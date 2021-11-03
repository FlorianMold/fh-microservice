#!/bin/bash

echo "--------------"
echo "Build fh/microservice."
echo "Make sure to call this script from the project root directory."
echo "--------------"
echo ""

WKDIR=`dirname "$0"`
echo "Working directory:" $WKDIR"/build.sh"


# Build the application
./gradlew bootJar

# Build docker container
docker build -t $DOCKER_MICROSERVICE_NAME:$MICROSERVICE_VERSION .

# Remove existing container
docker rm $DOCKER_CONTAINER_NAME
