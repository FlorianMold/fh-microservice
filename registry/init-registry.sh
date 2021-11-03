#!/bin/bash

echo "------------------------------"
echo "Starting local docker registry"
echo "------------------------------"
echo ""

echo "Docker-registry name:" $DOCKER_REGISTRY_CONTAINER_NAME
echo "Docker-container port:" $DOCKER_REGISTRY_DOCKER_PORT:$DOCKER_REGISTRY_LOCAL_PORT
echo ""

# Stop running registry
docker stop $DOCKER_REGISTRY_CONTAINER_NAME

# Remove old container
docker rm $DOCKER_REGISTRY_CONTAINER_NAME

# Creates a local registry for docker images.
docker run -d -p $DOCKER_REGISTRY_LOCAL_PORT:$DOCKER_REGISTRY_DOCKER_PORT --name $DOCKER_REGISTRY_CONTAINER_NAME registry:2.7
