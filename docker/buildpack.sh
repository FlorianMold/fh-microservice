#!/bin/bash

echo "--------------"
echo "Build fh/microservice."
echo "Make sure to call this script from the project root directory."
echo "--------------"
echo ""

WKDIR=`dirname "$0"`
echo "Working directory:" $WKDIR"/buildpack.sh"

# Build docker container
./gradlew bootBuildImage --imageName=$DOCKER_MICROSERVICE_NAME:$MICROSERVICE_VERSION

# Remove existing container
docker rm $DOCKER_CONTAINER_NAME
