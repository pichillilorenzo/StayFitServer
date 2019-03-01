# StayFit Servers

## Dependencies

- java
- node.js + npm (https://nodejs.org/en/)
- mvn (https://maven.apache.org/)
- pm2 (https://www.npmjs.com/package/pm2): `npm install pm2 -g`

## Installation

### Node.js dependencies installation

To install Node.js dependencies for `Load_Balancer` and `Server_Amazon`, go in each of these directories and execute: 
```bash
npm install
```

### MySQL Database

- **Database name**: `stayfit`
- **Database username**: `stayfit`
- **Database password**: `stayfit`

To initialize the MySQL Database, you can import the `./sql/stayfit.sql` file. 
This file contains all the MySQL tables needed to execute the application properly.

### MongoDB Database

Create a database named `stayfit` and then create two collections named `user_history` and `user_diet` in it.

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

## Usage Example

The MySQL Database contains already an User called `admin` with password `admin1234`.

To get an access token, we need to send a **POST** request to the `Authorization Service (OAuth2)` using this endpoint: `http://localhost:8081/oauth/token`:
- Headers:
  - `Authorization: Basic c3ByaW5nLXNlY3VyaXR5LW9hdXRoMi1yZWFkLXdyaXRlLWNsaWVudDpzcHJpbmctc2VjdXJpdHktb2F1dGgyLXJlYWQtd3JpdGUtY2xpZW50LXBhc3N3b3JkMTIzNA`
  - `Content-Type: application/x-www-form-urlencoded`
- Body (x-www-form-urlencoded):
  - `grant_type: password`
  - `username: admin`
  - `passowrd: admin1234`
  - `cliend_id: spring-security-oauth2-read-write-client`

Where `Authorization: Basic` uses these credentials base64 encoded:
- Username: `spring-security-oauth2-read-write-client`
- Password: `spring-security-oauth2-read-write-client-password1234`

The response will be something like:
```json
{
    "access_token": "a2722351-8b9a-40ef-bd39-3fa6331a8cf1",
    "token_type": "bearer",
    "refresh_token": "404672eb-a9da-48a1-9964-9f5eb755ec2a",
    "expires_in": 10799,
    "scope": "read write"
}
```

After that, we can test our **REST API** using the `access_token`. For example we want to get information about User with id 1. 
In this case, we need to send a **GET** request to the `Coordinator (Prosumer)` using this endpoint: `http://localhost:8080/api/v1/users/1`:
- Headers:
  - `Authorization: Bearer Token a2722351-8b9a-40ef-bd39-3fa6331a8cf1`

The response will be something like:
```json
{
    "id": 1,
    "username": "admin",
    "email": "admin@gmail.com",
    "enabled": true,
    "gender": true,
    "height": 175,
    "weight": 68,
    "birthDate": "1994-11-13T23:00:00.000+0000",
    "roles": [
        {
            "id": 1,
            "name": "ROLE_ADMIN"
        }
    ]
}
```

### Load Testing

To execute a **load testing**, we use **Artillery** ([https://artillery.io/](https://artillery.io/)), that is a modern, powerful & easy-to-use load testing and functional testing toolkit.
To install `artillery`, you need to run `npm install -g artillery`.

Before you run the load test, you need to set the `Authorization: Bearer Token` in the `config` section of `./Load_Balancer/test.yml`.
After that, we can use this tool to run the load test `./Load_Balancer/test.yml`. Simply, run the command `artillery run ./Load_Balancer/test.yml`.

As Artillery runs the test, an **intermediate report** will be printed to the terminal every 10 seconds, followed by an **aggregate report** at the end of the test.

An aggregate report will look similar to this:
```
All virtual users finished
Summary report @ 14:28:15(+0100) 2019-03-01
  Scenarios launched:  2947
  Scenarios completed: 2947
  Requests completed:  2947
  RPS sent: 26.15
  Request latency:
    min: 25.4
    max: 7116.2
    median: 728.6
    p95: 2664.8
    p99: 3397.7
  Scenario counts:
    0: 729 (24.737%)
    1: 705 (23.923%)
    2: 748 (25.382%)
    3: 765 (25.959%)
  Codes:
    200: 2947
```

See [https://artillery.io/docs/getting-started/](https://artillery.io/docs/getting-started/) for more details.