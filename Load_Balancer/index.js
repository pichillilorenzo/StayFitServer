const http = require('http');
const request = require('request');
const os = require('os');
const path = require("path");
const service = require("./service");

const ABS_PATH = path.dirname(__dirname);

function calcWeightedRes(cpu, memory) {
  return cpu * 0.80 + (memory/os.totalmem()*100) * 0.20;
}

http.createServer(function (req, res) {
  if (service.getProcesses().stayfitServer1Process != null && service.getProcesses().stayfitServer2Process != null) {
    
    let server = service.servers[0];
    let res1 = calcWeightedRes(service.getProcesses().stayfitServer1Process.monit.cpu, service.getProcesses().stayfitServer1Process.monit.memory);
    let res2 = calcWeightedRes(service.getProcesses().stayfitServer2Process.monit.cpu, service.getProcesses().stayfitServer2Process.monit.memory);
    
    console.log(res1, res2)

    if (res1 > res2) {
      server = service.servers[1];
      console.log("SECOND SERVER")
    }
    else {
      console.log("FIRST SERVER")
    }
    
    let forwardRequest = request({ url: server + req.url });
    forwardRequest.on('error', (e) => {
      console.error(`problem with request: ${e.message}`);
    });

    req.pipe(forwardRequest).pipe(res);
  }
  else {
    res.writeHead(200, { 'Content-Type': 'text/plain' });
    res.write('pm2 is not working!');
    res.end();
  }
}).listen(8070);

function exitHandler(options) {
  if (service.getProcesses().stayfitServer1Process != null) service.getPM2().stop(service.getProcesses().stayfitServer1Process.pm_id);
  if (service.getProcesses().SOAPServices1Process != null) service.getPM2().stop(service.getProcesses().SOAPServices1Process.pm_id);
  if (service.getProcesses().stayfitServer2Process != null) service.getPM2().stop(service.getProcesses().stayfitServer2Process.pm_id);
  if (service.getProcesses().SOAPServices2Process != null) service.getPM2().stop(service.getProcesses().SOAPServices2Process.pm_id);
  
  setTimeout(() => {
    if (options.exit) 
      process.exit();
  }, 2000);
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