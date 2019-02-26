#!/usr/bin/env bash

function split_string() { 
  echo $1 | cut -d$2 -f$3
}

trap ctrl_c INT

BASEDIR="$(cd "$(dirname "$0")" && pwd)"

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

for arg in "$@"
do
  if [ "$arg" = "-b" ]
  then
    cd $BASEDIR/StayFit_UserService
    mvn clean package &

    cd $BASEDIR/StayFit_UserHistoryService
    mvn clean package &

    cd $BASEDIR/StayFit_UserDietService
    mvn clean package &

    cd $BASEDIR/StayFit_OAuth2Service
    mvn clean package &

    cd $BASEDIR/StayFit_RESTProsumer
    mvn clean package &

    cd $BASEDIR

    wait
  elif [[ "$arg" = "--mysql="* ]]
  then
    MYSQL_DATABASE_URL=$(echo $arg | cut -c9-)
    MYSQL_DATABASE_URL="jdbc:mysql://$MYSQL_DATABASE_URL/stayfit?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
  elif [[ "$arg" = "--mongodb="* ]]
  then
    MONGODB_DATABASE_URL=$(echo $arg | cut -c11-)
  fi

done

# USER SERVICE
export PORT=8071
node $BASEDIR/Load_Balancer/service.js 1 -jar $BASEDIR/StayFit_UserService/target/StayFitUserService-0.0.1-SNAPSHOT.jar --server.port=$(split_string $USER_SERVICE_HOST_1 ":" "2") --spring.datasource.url=$MYSQL_DATABASE_URL &
pid1=$!

export PORT=8072
node $BASEDIR/Load_Balancer/service.js 2 -jar $BASEDIR/StayFit_UserService/target/StayFitUserService-0.0.1-SNAPSHOT.jar --server.port=$(split_string $USER_SERVICE_HOST_2 ":" "2") --spring.datasource.url=$MYSQL_DATABASE_URL &
pid2=$!

# USER HISTORY SERVICE
export PORT=8073
node $BASEDIR/Load_Balancer/service.js 1 -jar $BASEDIR/StayFit_UserHistoryService/target/StayFitUserHistoryService-0.0.1-SNAPSHOT.jar --server.port=$(split_string $USER_HISTORY_SERVICE_HOST_1 ":" "2") --spring.data.mongodb.host=$(split_string $MONGODB_DATABASE_URL ":" "1") --spring.data.mongodb.port=$(split_string $MONGODB_DATABASE_URL ":" "2") &
pid3=$!

export PORT=8074
node $BASEDIR/Load_Balancer/service.js 2 -jar $BASEDIR/StayFit_UserHistoryService/target/StayFitUserHistoryService-0.0.1-SNAPSHOT.jar --server.port=$(split_string $USER_HISTORY_SERVICE_HOST_2 ":" "2") --spring.data.mongodb.host=$(split_string $MONGODB_DATABASE_URL ":" "1") --spring.data.mongodb.port=$(split_string $MONGODB_DATABASE_URL ":" "2") &
pid4=$!

# USER DIET SERVICE
export PORT=8075
node $BASEDIR/Load_Balancer/service.js 1 -jar $BASEDIR/StayFit_UserDietService/target/StayFitUserDietService-0.0.1-SNAPSHOT.jar --server.port=$(split_string $USER_DIET_SERVICE_HOST_1 ":" "2") --spring.datasource.url=$MYSQL_DATABASE_URL --spring.data.mongodb.host=$(split_string $MONGODB_DATABASE_URL ":" "1") --spring.data.mongodb.port=$(split_string $MONGODB_DATABASE_URL ":" "2") &
pid5=$!

export PORT=8076
node $BASEDIR/Load_Balancer/service.js 2 -jar $BASEDIR/StayFit_UserDietService/target/StayFitUserDietService-0.0.1-SNAPSHOT.jar --server.port=$(split_string $USER_DIET_SERVICE_HOST_2 ":" "2") --spring.datasource.url=$MYSQL_DATABASE_URL --spring.data.mongodb.host=$(split_string $MONGODB_DATABASE_URL ":" "1") --spring.data.mongodb.port=$(split_string $MONGODB_DATABASE_URL ":" "2") &
pid6=$!

# OAUTH2 SERVICE
export PORT=8077
node $BASEDIR/Load_Balancer/service.js 1 -jar $BASEDIR/StayFit_OAuth2Service/target/StayFit_OAuth2Service-0.0.1-SNAPSHOT.jar --server.port=$(split_string $OAUTH2_SERVICE_HOST ":" "2") --spring.datasource.url=$MYSQL_DATABASE_URL &
pid7=$!

# AMAZON API
export PORT=3000
node $BASEDIR/Server_Amazon/server.js &
pid8=$!

# LOAD BALANCER
export PORT=$(split_string $LOAD_BALANCER_HOST ":" "2") 
node $BASEDIR/Load_Balancer/index.js &
pid9=$!

# PROSUMER
export PORT=8078
node $BASEDIR/Load_Balancer/service.js 1 -jar $BASEDIR/StayFit_RESTProsumer/target/StayFit-0.0.1-SNAPSHOT.jar --server.port=$(split_string $STAYFIT_SERVER_HOST ":" "2") --spring.datasource.url=$MYSQL_DATABASE_URL --loadbalancer.url=http://$LOAD_BALANCER_HOST --oauth2service.url=http://$OAUTH2_SERVICE_HOST &
pid10=$!

function ctrl_c() {
  kill -9 $pid1
  kill -9 $pid2
  kill -9 $pid3
  kill -9 $pid4
  kill -9 $pid5
  kill -9 $pid6
  kill -9 $pid7
  kill -9 $pid8
  kill -9 $pid9
  kill -9 $pid10
  pm2 stop all
}

wait