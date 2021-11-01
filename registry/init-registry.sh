REGISTRY_CONTAINER_NAME='registry'
REGISTRY_DOCKER_PORT=5000
REGISTRY_LOCAL_PORT=5000

# Stop running registry
docker stop $REGISTRY_CONTAINER_NAME

# Remove old container
docker rm $REGISTRY_CONTAINER_NAME

# Creates a local registry for docker images.
docker run -d -p $REGISTRY_LOCAL_PORT:$REGISTRY_DOCKER_PORT --name $REGISTRY_CONTAINER_NAME registry:2.7
