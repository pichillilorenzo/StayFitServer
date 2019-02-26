const http = require('http');
const os = require('os');
const path = require("path");
const pm2 = require("pm2");
const pidusage = require('pidusage')

const ABS_PATH = path.dirname(__dirname);

let pm2Process = {
  pid: null,
  pm_id: null,
  monit: {
    cpu: 0,
    memory: 0
  }
};

let appId = process.argv[2];
let appName = process.argv[4].split("/").slice(-1)[0];

function saveResourceInfo() {
  if (pm2Process.pid != null) {
    pidusage(pm2Process.pid, function (err, stats) {
      pm2Process.monit.cpu = stats.cpu;
      pm2Process.monit.memory = stats.memory;
      // => {
      //   cpu: 10.0,            // percentage (from 0 to 100*vcore)
      //   memory: 357306368,    // bytes
      //   ppid: 312,            // PPID
      //   pid: 727,             // PID
      //   ctime: 867000,        // ms user + system time
      //   elapsed: 6650000,     // ms since the start of the process
      //   timestamp: 864000000  // ms since epoch
      // }
    })
    // pm2.describe(pm2Process.pm_id, (err, procDesc) => {
    //   if (err) throw err;
    //   pm2Process = procDesc[0];
    // })
  }
}

pm2.connect((err) => {
  if (err) {
    console.error(err);
    process.exit(2);
  }
  
  // start the java web service using pm2
  pm2.start(
    {
      name: appName+"-"+appId,
      cwd: ABS_PATH,
      out_file: ABS_PATH+"/Load_Balancer/"+appName+"-"+appId+".log",
      error_file: ABS_PATH+"/Load_Balancer/"+appName+"-"+appId+".err",
      args: process.argv.slice(3, process.argv.length),
      script: "java",
      node_args: [],
      log_date_format: "YYYY-MM-DD HH:mm",
      exec_mode: "fork"
    },
    (err, apps) => {
      if (err) throw err;
      // save java process information
      pm2.describe(apps[0].pm_id, (err, procDesc) => {
        if (err) throw err;
        pm2Process.pid = procDesc[0].pid;
        pm2Process.pm_id = procDesc[0].pm_id;
      })
    }
  );
  
  // every 1 second get information about the java process
  setInterval(() => {
    saveResourceInfo();
  }, 1000);
});

// server that responds to the load balancer with CPU and Memory % usage
http.createServer(function (req, res) {
  if (pm2Process.pid != null) {
    res.writeHead(200, {"Content-Type": "application/json"});
    res.write(JSON.stringify({
      cpu: pm2Process.monit.cpu,
      memory: (pm2Process.monit.memory/os.totalmem()*100)
    }));
  }
  res.end();
}).listen(process.env.PORT, function(){
  console.log("Listening on port " + process.env.PORT)
});

function exitHandler(options) {
  if (pm2Process.pid != null) pm2.stop(pm2Process.pm_id);
  
  setTimeout(() => {
    if (options.exit) 
      process.exit();
  }, 2500);
}

process.stdin.resume();

//do something when app is closing
process.on('exit', exitHandler.bind(null, {}));

//catches ctrl+c event
process.on('SIGINT', exitHandler.bind(null, {exit:true}));

process.on('uncaughtException', (e) => {
  console.log('[uncaughtException] app will be terminated: ', e.stack);
  exitHandler({exit:true});
});