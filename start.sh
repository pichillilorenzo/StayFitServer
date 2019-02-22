#!/usr/bin/env bash

function split_string() { 
  echo $1 | cut -d$2 -f$3
}

trap ctrl_c INT

BASEDIR=$(pwd)

STAYFIT_SERVER="localhost:8080"
OAUTH2_SERVICE_ENDPOINT="localhost:8081"
USER_SERVICE_ENDPOINT="localhost:8082"
USER_HISTORY_SERVICE_ENDPOINT="localhost:8083"
USER_DIET_SERVICE_ENDPOINT="localhost:8084"

MYSQL_DATABASE_URL="jdbc:mysql://localhost:3306/stayfit?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
MONGODB_DATABASE_URL="localhost:27017"

RUN_NODE_AMAZON_SERVER=false

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
  elif [[ "$arg" = "--node" ]]
  then
    RUN_NODE_AMAZON_SERVER=true
  else
    STAYFIT_SERVER=$(split_string $arg "," "1")
    USER_SERVICE_ENDPOINT=$(split_string $arg "," "2")
    USER_HISTORY_SERVICE_ENDPOINT=$(split_string $arg "," "3")
    USER_DIET_SERVICE_ENDPOINT=$(split_string $arg "," "4")
    OAUTH2_SERVICE_ENDPOINT=$(split_string $arg "," "5")
  fi

done

if [ $(split_string $USER_SERVICE_ENDPOINT ":" "1") = "localhost" ]
then
  java -jar $BASEDIR/StayFit_UserService/target/StayFitUserService-0.0.1-SNAPSHOT.jar --server.port=$(split_string $USER_SERVICE_ENDPOINT ":" "2") --spring.datasource.url=$MYSQL_DATABASE_URL &
  pid1=$!
fi

if [ $(split_string $USER_HISTORY_SERVICE_ENDPOINT ":" "1") = "localhost" ]
then
  java -jar $BASEDIR/StayFit_UserHistoryService/target/StayFitUserHistoryService-0.0.1-SNAPSHOT.jar --server.port=$(split_string $USER_HISTORY_SERVICE_ENDPOINT ":" "2") --spring.data.mongodb.host=$(split_string $MONGODB_DATABASE_URL ":" "1") --spring.data.mongodb.port=$(split_string $MONGODB_DATABASE_URL ":" "2") &
  pid2=$!
fi

if [ $(split_string $USER_DIET_SERVICE_ENDPOINT ":" "1") = "localhost" ]
then
  java -jar $BASEDIR/StayFit_UserDietService/target/StayFitUserDietService-0.0.1-SNAPSHOT.jar --server.port=$(split_string $USER_DIET_SERVICE_ENDPOINT ":" "2") --spring.datasource.url=$MYSQL_DATABASE_URL --spring.data.mongodb.host=$(split_string $MONGODB_DATABASE_URL ":" "1") --spring.data.mongodb.port=$(split_string $MONGODB_DATABASE_URL ":" "2") &
  pid3=$!
fi

if [ $(split_string $OAUTH2_SERVICE_ENDPOINT ":" "1") = "localhost" ]
then
  java -jar $BASEDIR/StayFit_OAuth2Service/target/StayFit_OAuth2Service-0.0.1-SNAPSHOT.jar --server.port=$(split_string $OAUTH2_SERVICE_ENDPOINT ":" "2") --spring.datasource.url=$MYSQL_DATABASE_URL &
  pid4=$!
fi

java -jar $BASEDIR/StayFit_RESTProsumer/target/StayFit-0.0.1-SNAPSHOT.jar --server.port=$(split_string $STAYFIT_SERVER ":" "2") --spring.datasource.url=$MYSQL_DATABASE_URL $USER_SERVICE_ENDPOINT $USER_HISTORY_SERVICE_ENDPOINT $USER_DIET_SERVICE_ENDPOINT $OAUTH2_SERVICE_ENDPOINT &
pid5=$!

if [ $RUN_NODE_AMAZON_SERVER = true ]
then
  node $BASEDIR/Server_Amazon/server.js &
  pid6=$!
fi

function ctrl_c() {
  kill -9 $pid1
  kill -9 $pid2
  kill -9 $pid3
  kill -9 $pid4
  kill -9 $pid5
  if [ $RUN_NODE_AMAZON_SERVER = true ]
  then
    kill -9 $pid6
  fi
}

wait