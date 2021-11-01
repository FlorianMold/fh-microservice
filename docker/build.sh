#!/bin/bash

echo "--------------"
echo "Build fh/microservice."
echo "Make sure to call this script from the project root directory."
echo "--------------"
echo ""

WKDIR=`dirname "$0"`
echo "Working directory:" $WKDIR

DOCKER_CONTAINER_NAME='microservice'
DOCKER_IMAGE_NAME='fh/microservice:'
DOCKER_MICROSERVICE_VERSION='0.0.1'

# Build the application
./gradlew bootJar

# Build docker container
docker build -t $DOCKER_IMAGE_NAME$DOCKER_MICROSERVICE_VERSION .

# Remove existing container
docker rm $DOCKER_CONTAINER_NAME
