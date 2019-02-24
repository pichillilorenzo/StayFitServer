const http = require('http');
const os = require('os');
const path = require("path");
const pm2 = require("pm2");

const ABS_PATH = path.dirname(__dirname);

let pm2Process = null;

let appId = process.argv[2];
let appName = process.argv[4].split("/").slice(-1)[0];

pm2.connect((err) => {
  if (err) {
    console.error(err);
    process.exit(2);
  }
  
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
      pm2.describe(apps[0].pm_id, (err, procDesc) => {
        if (err) throw err;
        pm2Process = procDesc[0];
      })
    }
  );

  setInterval(() => {
    if (pm2Process != null) {
      pm2.describe(pm2Process.pm_id, (err, procDesc) => {
        if (err) throw err;
        pm2Process = procDesc[0];
      })
    }
  }, 500);
});

http.createServer(function (req, res) {
  if (pm2Process != null) {
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
  if (pm2Process != null) pm2.stop(pm2Process.pm_id);
  
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