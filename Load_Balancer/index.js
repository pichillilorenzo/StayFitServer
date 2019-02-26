const http = require('http');
const request = require('request');
const path = require("path");

const ABS_PATH = path.dirname(__dirname);

// available hosts
let serviceHosts = {
  userservice: [{pm2: "http://localhost:8071", java: "http://localhost:8082"}, {pm2: "http://localhost:8072", java: "http://localhost:8092"}],
  userhistoryservice: [{pm2: "http://localhost:8073", java: "http://localhost:8083"}, {pm2: "http://localhost:8074", java: "http://localhost:8093"}],
  userdietservice: [{pm2: "http://localhost:8075", java: "http://localhost:8084"}, {pm2: "http://localhost:8076", java: "http://localhost:8094"}]
}

let cachedServiceResources = {
  userservice: [],
  userhistoryservice: [],
  userdietservice: []
};

// gets, every 2 seconds, information about CPU and Memory usage from web services
setTimeout(() => {
  let intervalId = setInterval(async () => {
    for (let key of Object.keys(serviceHosts)) {
      for(let i = 0; i < serviceHosts[key].length; i++) {
        request(serviceHosts[key][i].pm2, (error, response, body) => {
          if (error) 
            console.log(error);
          else if (response.statusCode == 200) {
            try {
              cachedServiceResources[key][i] = JSON.parse(body);
            } catch (err) {
              console.log(body, err);
            }
          }
        });
      }
    }
  }, 2000);
}, 2000);

// returns a value based on CPU and Memory usage 
// to determine which web service is the best to serve the current user request
function calcWeightedRes(cpu, memory) {
  return (cpu * 0.80) + (memory * 0.20);
}

// create a server to accept requests from the Prosumer and return 
// the best web service url based on its CPU and Memory usage 
http.createServer(function (req, res) {
  let hosts = serviceHosts[req.url.substr(1)];
  let resources = cachedServiceResources[req.url.substr(1)];
  let weighetRes = [];

  for(let i = 0; i < resources.length; i++) {
    weighetRes.push(calcWeightedRes(resources[i].cpu, resources[i].memory));
  }
  
  let index = weighetRes.indexOf(Math.min(...weighetRes));
  index = (index < 0) ? 0 : index;

  res.writeHead(200, {"Content-Type": "text/plain"});
  res.write(hosts[index].java);
  res.end()

}).listen(process.env.PORT, function(){
  console.log("Listening on port " + process.env.PORT)
});

process.on('uncaughtException', (e) => {
  console.log('[uncaughtException]: ', e.stack);
});