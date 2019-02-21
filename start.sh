#!/usr/bin/env bash

trap ctrl_c INT

BASEDIR=$(pwd)

if [ "$1" = "-b" ]
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

fi

java -jar $BASEDIR/StayFit_UserService/target/StayFitUserService-0.0.1-SNAPSHOT.jar &
pid1=$!
java -jar $BASEDIR/StayFit_UserHistoryService/target/StayFitUserHistoryService-0.0.1-SNAPSHOT.jar &
pid2=$!
java -jar $BASEDIR/StayFit_UserDietService/target/StayFitUserDietService-0.0.1-SNAPSHOT.jar &
pid3=$!
java -jar $BASEDIR/StayFit_OAuth2Service/target/StayFit_OAuth2Service-0.0.1-SNAPSHOT.jar &
pid4=$!
java -jar $BASEDIR/StayFit_RESTProsumer/target/StayFit-0.0.1-SNAPSHOT.jar &
pid5=$!

node $BASEDIR/Server_Amazon/server.js &
pid6=$!

function ctrl_c() {
  kill -9 $pid1
  kill -9 $pid2
  kill -9 $pid3
  kill -9 $pid4
  kill -9 $pid5
  kill -9 $pid6
}

wait