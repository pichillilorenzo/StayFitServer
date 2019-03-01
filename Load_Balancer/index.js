const http = require("http");
const request = require("request");
const path = require("path");

const ABS_PATH = path.dirname(__dirname);

// available hosts
let serviceHosts = {
  "/user/services/user": [
    { pm2: "http://localhost:8071", java: "http://localhost:8082" },
    { pm2: "http://localhost:8072", java: "http://localhost:8092" }
  ],
  "/user_history/services/user_history": [
    { pm2: "http://localhost:8073", java: "http://localhost:8083" },
    { pm2: "http://localhost:8074", java: "http://localhost:8093" }
  ],
  "/user_diet/services/user_diet": [
    { pm2: "http://localhost:8075", java: "http://localhost:8084" },
    { pm2: "http://localhost:8076", java: "http://localhost:8094" }
  ]
};

let cachedResourceInfo = {
  "/user/services/user": [],
  "/user_history/services/user_history": [],
  "/user_diet/services/user_diet": []
};

// gets, every 1 seconds, information about CPU and Memory usage from web services
setTimeout(() => {
  let intervalId = setInterval(async () => {
    for (let key of Object.keys(serviceHosts)) {
      for (let i = 0; i < serviceHosts[key].length; i++) {
        request(serviceHosts[key][i].pm2, (error, response, body) => {
          if (error) console.log(error);
          else if (response.statusCode == 200) {
            try {
              cachedResourceInfo[key][i] = JSON.parse(body);
              cachedResourceInfo[key][i].numOfRequests = 0;
            } catch (err) {
              console.log(body, err);
            }
          }
        });
      }
    }
  }, 1000);
}, 2000);

// returns a value based on CPU usage, Memory usage and number of requests in 1 seconds
// to determine which web service is the best to serve the current request
function calcWeightedRes(cpu, memory, numOfRequests) {
  return cpu * 0.4 + memory * 0.2 + numOfRequests * 0.4;
}

// create a server to accept requests from the Prosumer and forward the requests
// to the best web service
http
  .createServer(function(req, res) {
    let hosts = serviceHosts[req.url];
    let resources = cachedResourceInfo[req.url];
    let weighetRes = [];

    for (let i = 0; i < resources.length; i++) {
      weighetRes.push(
        calcWeightedRes(
          resources[i].cpu,
          resources[i].memory,
          resources[i].numOfRequests
        )
      );
    }

    let index = weighetRes.indexOf(Math.min(...weighetRes));
    index = index < 0 ? 0 : index;
    if (cachedResourceInfo[req.url][index].numOfRequests != null)
      cachedResourceInfo[req.url][index].numOfRequests++;

    // forward request
    req.pipe(request(hosts[index].java + req.url)).pipe(res);
  })
  .listen(process.env.PORT, function() {
    console.log("Listening on port " + process.env.PORT);
  });

process.on("uncaughtException", e => {
  console.log("[uncaughtException]: ", e.stack);
});
