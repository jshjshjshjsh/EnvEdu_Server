#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/EnvEdu_Server
cd $REPOSITORY

mvn package -DskipTests

JAR_NAME=demo-0.0.1-SNAPSHOT.jar
JAR_PATH=$REPOSITORY/target/$JAR_NAME

CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -9 $CURRENT_PID"
  sleep 5
  kill -15 $CURRENT_PID
fi

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &

#!/usr/bin/env bash

#REPOSITORY=/home/ubuntu/EnvEdu_Server
#cd $REPOSITORY
#
## Maven으로 프로젝트 빌드
#mvn package
#
#JAR_NAME=demo-0.0.1-SNAPSHOT.jar
#JAR_PATH=$REPOSITORY/target/$JAR_NAME
#
## 이전 실행중인 Java 프로세스 찾기
#CURRENT_PID=$(pgrep -f $JAR_NAME)
#
#if [ -z "$CURRENT_PID" ]; then
#  echo "> 종료할 것 없음."
#else
#  echo "> kill -15 $CURRENT_PID"
#  kill -15 $CURRENT_PID
#  sleep 5
#fi
#
#echo "> $JAR_PATH 배포"
#nohup java -jar $JAR_PATH > $REPOSITORY/app.log 2>&1 &
#
#echo "배포 완료"
