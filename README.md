# StayFit Servers

## Dependencies

- java
- node.js + npm (https://nodejs.org/en/)
- mvn (https://maven.apache.org/)
- pm2 (https://www.npmjs.com/package/pm2): `npm install pm2 -g`

## Installation

### Node.js dependencies installation

To install Node.js dependencies for `Load_Balancer` and `Server_Amazon`, go in each of these directories and execute `npm install`.

### Database MySQL

To initialize the Database MySQL, you can import the `./sql/stayfit.sql` file. 
This file contains all the MySQL tables needed to execute the application properly.

Default values are:
- Database name: `stayfit`
- Database username: `stayfit`
- Database password: `stayfit`

### Database MongoDB

Create a database named: `stayfit`.

## Getting Started

To build .jar files and execute the Java and Node.js servers together, you can use `./start.sh -b`.
Instead, if you don't want to build, you can simply use `./start.sh`.

The `./start.sh` script contains the default host value of each server:
```bash
...

STAYFIT_SERVER_HOST="localhost:8080"
OAUTH2_SERVICE_HOST="localhost:8081"
LOAD_BALANCER_HOST="localhost:8070"

USER_SERVICE_HOST_1="localhost:8082"
USER_HISTORY_SERVICE_HOST_1="localhost:8083"
USER_DIET_SERVICE_HOST_1="localhost:8084"

USER_SERVICE_HOST_2="localhost:8092"
USER_HISTORY_SERVICE_HOST_2="localhost:8093"
USER_DIET_SERVICE_HOST_2="localhost:8094"

MYSQL_DATABASE_URL="jdbc:mysql://localhost:3306/stayfit?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
MONGODB_DATABASE_URL="localhost:27017"

...
```

Also, the `./start.sh` script contains the default PORT value of each **Monitor** and for the `Server_Amazon` server:
```bash
# USER SERVICE 1
export PORT=8071
# USER SERVICE 2
export PORT=8072

# USER HISTORY SERVICE 1
export PORT=8073
# USER HISTORY SERVICE 2
export PORT=8074

# USER DIET SERVICE 1
export PORT=8075
# USER DIET SERVICE 2
export PORT=8076

# OAUTH2 SERVICE
export PORT=8077

# AMAZON API
export PORT=3000

# LOAD BALANCER
export PORT=8070

# PROSUMER
export PORT=8078
```

The `./Load_Balancer/index.js` script contains also the host value of each **Monitor** and **Java Server**, that are:
```javascript
...

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

...
```