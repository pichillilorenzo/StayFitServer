#!/usr/bin/env bash

trap ctrl_c INT

BASEDIR=$(pwd)

if [ "$1" = "-b" ]
then

  cd $BASEDIR/StayFit_UserService
  mvn clean package &

  cd $BASEDIR/StayFit_RESTProsumer
  mvn clean package &

  cd $BASEDIR/StayFit_OAuth2Service
  mvn clean package &

  cd $BASEDIR/StayFit_FatSecretService
  mvn clean package &

  cd $BASEDIR/StayFit_BarcodeService
  mvn clean package &

  cd $BASEDIR/StayFit_AmazonService
  mvn clean package &
  
  cd $BASEDIR

  wait

fi

java -jar $BASEDIR/StayFit_UserService/target/StayFitUserService-0.0.1-SNAPSHOT.jar &
pid1=$!
java -jar $BASEDIR/StayFit_OAuth2Service/target/StayFit_OAuth2Service-0.0.1-SNAPSHOT.jar &
pid2=$!
java -jar $BASEDIR/StayFit_FatSecretService/target/StayFitFatSecretService-0.0.1-SNAPSHOT.jar &
pid3=$!
java -jar $BASEDIR/StayFit_BarcodeService/target/StayFitBarcodeService-0.0.1-SNAPSHOT.jar &
pid4=$!
java -jar $BASEDIR/StayFit_AmazonService/target/StayFitAmazonService-0.0.1-SNAPSHOT.jar &
pid5=$!
java -jar $BASEDIR/StayFit_RESTProsumer/target/StayFit-0.0.1-SNAPSHOT.jar &
pid6=$!

node $BASEDIR/Server_Amazon/server.js &
pid7=$!

function ctrl_c() {
  kill -9 $pid1
  kill -9 $pid2
  kill -9 $pid3
  kill -9 $pid4
  kill -9 $pid5
  kill -9 $pid6
  kill -9 $pid7
}

wait