#!/bin/bash
# Start the Spring Boot application
echo "Starting Spring Boot application..."
nohup java -jar /home/ubuntu/EnvEdu_Server/*.jar > /home/ubuntu/EnvEdu_Server/app.log 2>&1 &
