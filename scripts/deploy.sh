##!/usr/bin/env bash
#
#REPOSITORY=/home/ubuntu/EnvEdu_Server
#cd $REPOSITORY
#
#mvn package -DskipTests
#
#JAR_NAME=demo-0.0.1-SNAPSHOT.jar
#JAR_PATH=$REPOSITORY/target/$JAR_NAME
#
#CURRENT_PID=$(pgrep -f $JAR_NAME)
#
#if [ -z $CURRENT_PID ]
#then
#  echo "> 종료할것 없음."
#else
#  echo "> kill -9 $CURRENT_PID"
#  sleep 5
#  kill -15 $CURRENT_PID
#fi
#
#echo "> $JAR_PATH 배포"
#nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &

#!/bin/bash

# 환경 변수 설정
REPOSITORY=/home/ubuntu/EnvEdu_Server
JAR_NAME=demo-0.0.1-SNAPSHOT.jar
JAR_PATH="$REPOSITORY/target/$JAR_NAME"
LOG_FILE="$REPOSITORY/app.log"

# 스크립트 실행 권한 부여
chmod +x $0

# 디렉토리로 이동
cd $REPOSITORY

# Maven으로 프로젝트 빌드
mvn package

# 이전 실행중인 Java 프로세스 찾기
CURRENT_PID=$(pgrep -f $JAR_NAME)

# 기존 Java 프로세스가 존재하면 종료
if [ -z "$CURRENT_PID" ]; then
  echo "> 종료할 것 없음."
else
  echo "> 종료 중: $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

# 로그 파일 생성 및 권한 설정
touch $LOG_FILE
chmod 664 $LOG_FILE

ls -l $JAR_NAME  # 파일 권한 확인
chmod 644 $JAR_NAME  # 읽기와 실행 권한 부여

# JAR 파일 배포
echo "> 배포 시작: $JAR_PATH"
nohup java -jar $JAR_PATH > $LOG_FILE 2>&1 &

echo "배포 완료"

