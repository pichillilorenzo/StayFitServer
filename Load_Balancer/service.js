const path = require("path");
const pm2 = require("pm2");

const ABS_PATH = path.dirname(__dirname);

const STAYFIT_SERVER_1="localhost:8080"
const OAUTH2_SERVICE_ENDPOINT_1="localhost:8081"
const USER_SERVICE_ENDPOINT_1="localhost:8082"
const USER_HISTORY_SERVICE_ENDPOINT_1="localhost:8083"
const USER_DIET_SERVICE_ENDPOINT_1="localhost:8084"

const MYSQL_DATABASE_URL_1="localhost:3306"
const MONGODB_DATABASE_URL_1="localhost:27017"

const STAYFIT_SERVER_2="localhost:8090"
const OAUTH2_SERVICE_ENDPOINT_2="localhost:8091"
const USER_SERVICE_ENDPOINT_2="localhost:8092"
const USER_HISTORY_SERVICE_ENDPOINT_2="localhost:8093"
const USER_DIET_SERVICE_ENDPOINT_2="localhost:8094"

const MYSQL_DATABASE_URL_2="localhost:3306"
const MONGODB_DATABASE_URL_2="localhost:27017"

let servers = ["http://"+STAYFIT_SERVER_1, "http://"+STAYFIT_SERVER_2];
let stayfitServer1Process = null;
let SOAPServices1Process = null;
let stayfitServer2Process = null;
let SOAPServices2Process = null;

pm2.connect((err) => {
  if (err) {
    console.error(err);
    process.exit(2);
  }

  pm2.start(
    {
      name: "SOAPServices1",
      cwd: ABS_PATH,
      out_file: ABS_PATH+"/Load_Balancer/SOAPServices1.log",
      error_file: ABS_PATH+"/Load_Balancer/SOAPServices1.err",
      args: [
        "--node",
        "--mysql="+MYSQL_DATABASE_URL_1,
        "--mongodb="+MONGODB_DATABASE_URL_1,
        [STAYFIT_SERVER_1, USER_SERVICE_ENDPOINT_1, USER_HISTORY_SERVICE_ENDPOINT_1, USER_DIET_SERVICE_ENDPOINT_1, OAUTH2_SERVICE_ENDPOINT_1].join(",")
      ],
      script: ABS_PATH+"/start.sh",
      node_args: [],
      log_date_format: "YYYY-MM-DD HH:mm",
      exec_interpreter: "bash",
      exec_mode: "fork"
    },
    (err, apps) => {
      if (err) throw err;
      pm2.describe(apps[0].pm_id, (err, procDesc) => {
        if (err) throw err;
        SOAPServices1Process = procDesc[0];
      })
    }
  );

  pm2.start(
    {
      name: "RESTService1",
      cwd: ABS_PATH,
      out_file: ABS_PATH+"/Load_Balancer/RESTService1.log",
      error_file: ABS_PATH+"/Load_Balancer/RESTService1.err",
      args: [
        "-jar",
        ABS_PATH+"/StayFit_RESTProsumer/target/StayFit-0.0.1-SNAPSHOT.jar",
        "--server.port=8080",
        "--spring.datasource.url=jdbc:mysql://"+MYSQL_DATABASE_URL_1+"/stayfit?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
        USER_SERVICE_ENDPOINT_1,
        USER_HISTORY_SERVICE_ENDPOINT_1,
        USER_DIET_SERVICE_ENDPOINT_1,
        OAUTH2_SERVICE_ENDPOINT_1
      ],
      script: "java",
      node_args: [],
      log_date_format: "YYYY-MM-DD HH:mm",
      exec_mode: "fork"
    },
    (err, apps) => {
      if (err) throw err;
      pm2.describe(apps[0].pm_id, (err, procDesc) => {
        if (err) throw err;
        stayfitServer1Process = procDesc[0];
      })
    }
  );

  pm2.start(
    {
      name: "SOAPServices2",
      cwd: ABS_PATH,
      out_file: ABS_PATH+"/Load_Balancer/SOAPServices2.log",
      error_file: ABS_PATH+"/Load_Balancer/SOAPServices2.err",
      args: [
        "--node",
        "--mysql="+MYSQL_DATABASE_URL_2,
        "--mongodb="+MONGODB_DATABASE_URL_2,
        [STAYFIT_SERVER_2, USER_SERVICE_ENDPOINT_2, USER_HISTORY_SERVICE_ENDPOINT_2, USER_DIET_SERVICE_ENDPOINT_2, OAUTH2_SERVICE_ENDPOINT_2].join(",")
      ],
      script: ABS_PATH+"/start.sh",
      node_args: [],
      log_date_format: "YYYY-MM-DD HH:mm",
      exec_interpreter: "bash",
      exec_mode: "fork"
    },
    (err, apps) => {
      if (err) throw err;
      pm2.describe(apps[0].pm_id, (err, procDesc) => {
        if (err) throw err;
        SOAPServices2Process = procDesc[0];
      })
    }
  );

  pm2.start(
    {
      name: "RESTService2",
      cwd: ABS_PATH,
      out_file: ABS_PATH+"/Load_Balancer/RESTService2.log",
      error_file: ABS_PATH+"/Load_Balancer/RESTService2.err",
      args: [
        "-jar",
        ABS_PATH+"/StayFit_RESTProsumer/target/StayFit-0.0.1-SNAPSHOT.jar",
        "--server.port=8090",
        "--spring.datasource.url=jdbc:mysql://"+MYSQL_DATABASE_URL_2+"/stayfit?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
        USER_SERVICE_ENDPOINT_2,
        USER_HISTORY_SERVICE_ENDPOINT_2,
        USER_DIET_SERVICE_ENDPOINT_2,
        OAUTH2_SERVICE_ENDPOINT_2
      ],
      script: "java",
      node_args: [],
      log_date_format: "YYYY-MM-DD HH:mm",
      exec_mode: "fork"
    },
    (err, apps) => {
      if (err) throw err;
      pm2.describe(apps[0].pm_id, (err, procDesc) => {
        if (err) throw err;
        stayfitServer2Process = procDesc[0];
      })
    }
  );

  setInterval(() => {
    if (stayfitServer1Process != null) {
      pm2.describe(stayfitServer1Process.pm_id, (err, procDesc) => {
        if (err) throw err;
        stayfitServer1Process = procDesc[0];
      })
    }
    if (stayfitServer2Process != null) {
      pm2.describe(stayfitServer2Process.pm_id, (err, procDesc) => {
        if (err) throw err;
        stayfitServer2Process = procDesc[0];
      })
    }
  }, 1000);
});

function getPM2() {
  return pm2;
}

function getProcesses() {
  return {
    stayfitServer1Process,
    SOAPServices1Process,
    stayfitServer2Process,
    SOAPServices2Process
  };
}

module.exports = {
  getPM2,
  STAYFIT_SERVER_1,
  OAUTH2_SERVICE_ENDPOINT_1,
  USER_SERVICE_ENDPOINT_1,
  USER_HISTORY_SERVICE_ENDPOINT_1,
  USER_DIET_SERVICE_ENDPOINT_1,
  MYSQL_DATABASE_URL_1,
  MONGODB_DATABASE_URL_1,
  STAYFIT_SERVER_2,
  OAUTH2_SERVICE_ENDPOINT_2,
  USER_SERVICE_ENDPOINT_2,
  USER_HISTORY_SERVICE_ENDPOINT_2,
  USER_DIET_SERVICE_ENDPOINT_2,
  MYSQL_DATABASE_URL_2,
  MONGODB_DATABASE_URL_2,
  servers,
  getProcesses
}