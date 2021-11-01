#!/bin/bash

echo "--------------"
echo "Build fh/microservice."
echo "Make sure to call this script from the project root directory."
echo "--------------"
echo ""

WKDIR=`dirname "$0"`
echo "Working directory:" $WKDIR

DOCKER_IMAGE_NAME='fh/microservice:'
DOCKER_MICROSERVICE_VERSION='0.0.1'
DOCKER_CONTAINER_NAME='microservice'

# Build docker container
./gradlew bootBuildImage --imageName=$DOCKER_IMAGE_NAME$DOCKER_MICROSERVICE_VERSION

# Remove existing container
docker rm $DOCKER_CONTAINER_NAME
