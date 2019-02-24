const http = require('http');
const request = require('request');
const path = require("path");

const ABS_PATH = path.dirname(__dirname);

let userServiceHosts = [{pm2: "http://localhost:8071", java: "http://localhost:8082"}, {pm2: "http://localhost:8072", java: "http://localhost:8092"}];
let userHistoryServiceHosts = [{pm2: "http://localhost:8073", java: "http://localhost:8083"}, {pm2: "http://localhost:8074", java: "http://localhost:8093"}];
let userDietServiceHosts = [{pm2: "http://localhost:8075", java: "http://localhost:8084"}, {pm2: "http://localhost:8076", java: "http://localhost:8094"}];

// wrap a request in an promise
function requestUrl(url) {
  return new Promise((resolve, reject) => {
      request(url, (error, response, body) => {
          if (error) {
            reject(error);
            return;
          }
          if (response.statusCode != 200) {
            reject('Invalid status code <' + response.statusCode + '>');
            return;
          }
          resolve(body);
      });
  });
}

function calcWeightedRes(cpu, memory) {
  return (cpu * 0.20) + (memory * 0.80);
}

http.createServer(async function (req, res) {
  let resources = [];

  try {
    switch(req.url) {
      case "/userservice":
        for(let i = 0; i < userServiceHosts.length; i++)
          resources[i] = JSON.parse(await requestUrl(userServiceHosts[i].pm2));
        break;
      case "/userhistoryservice":
        for(let i = 0; i < userHistoryServiceHosts.length; i++)
          resources[i] = JSON.parse(await requestUrl(userHistoryServiceHosts[i].pm2));
        break;
      case "/userdietservice":
        for(let i = 0; i < userDietServiceHosts.length; i++)
          resources[i] = JSON.parse(await requestUrl(userDietServiceHosts[i].pm2));
        break;
    }
  } catch (error) {
    console.log(error)
    res.writeHead(500, {"Content-Type": "text/plain"});
    res.write(error.toString());
    res.end();
    return;
  }
  
  let weighetRes = [];

  for(let i = 0; i < resources.length; i++) {
    weighetRes.push(calcWeightedRes(resources[i].cpu, resources[i].memory));
  }
  
  let index = weighetRes.indexOf(Math.min(...weighetRes));

  res.writeHead(200, {"Content-Type": "text/plain"});

  if (index >= 0) {
    switch(req.url) {
      case "/userservice":
        res.write(userServiceHosts[index].java.replace("http://", ""));
        break;
      case "/userhistoryservice":
        res.write(userHistoryServiceHosts[index].java.replace("http://", ""));
        break;
      case "/userdietservice":
        res.write(userDietServiceHosts[index].java.replace("http://", ""));
        break;
    }
  }

  res.end()

}).listen(process.env.PORT, function(){
  console.log("Listening on port " + process.env.PORT)
});
