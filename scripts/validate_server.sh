#!/bin/bash
# Validate that the Spring Boot application is running
echo "Validating the Spring Boot application..."
if curl -s http://localhost:8080/; then
    echo "Spring Boot application is running."
else
    echo "Failed to start Spring Boot application."
    exit 1
fi
