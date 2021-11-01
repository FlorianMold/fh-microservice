# Getting Started

> Execute every script from the base directory

## Run the application

```shell
./gradlew bootRun
```

## Build the docker image

Either run `./docker/build.sh` or `./docker/buildpack.sh` to create a docker image of the microservice.
- `./docker/build.sh`: Uses the **Dockerfile** in the base directory to build the docker image.
- `./docker/buildpack.sh`: Uses **buildpacks** for creating the docker-images, which is already supported by spring boot. Takes the configuration from the project configuration and does not need any more configuration.

```shell
./docker/build.sh

# OR

./docker/buildpack.sh
```

### Run the built docker-image

After the image was built the container can be executed by executing the following command:

```shell
# docker run --name $DOCKER_CONTAINER_NAME --network $DOCKER_NETWORK_NAME -p 8080:8080 $DOCKER_IMAGE_NAME

docker run --name microservice --network fh -p 8080:8080 fh/microservice:0.0.1
```

## Using docker-compose

After building the image the containers can be started by using a `docker-compose.yml` file. Therefore, move into to the 
`./tools`-folder, where the files are located.

```shell

# Run microservice + integrated database
docker-compose up

# Run microservice + postgres database
docker-compose -f docker-compose.db.yml -f docker-compose.yml up
```

## Deploying

To simplify this process the `bundle.sh` script was created. The script builds the microservice and saves a 
tar file of the image inside a newly created `dist/` folder. The `docker-compose.yml` file is also transferred to this directory.
You have to specify a `.env` file inside the `tools/`-directory, which contains the environment variables for the application. This file is transferred to the `dist/`-folder
Variables that can be defined are found inside the `.env.example`-file.

```shell
./bundle.sh

# OR

# Do not save an image to the filesystem
./bundle.sh -r
```

`./bundle.sh` uses the `./docker/buildpack.sh` by default, but can be configured to use `./docker/build.sh` by running 
`./bundle.sh -d`.

## Running

To simplify the initialization just run `run.sh` inside the `dist/` folder. This will initially start the microservice with an integrated database.
Or run `run.sh -p` inside the `dist/` folder. This will start the microservice with a postgres database.

```shell
dist/run.sh

# OR

# With postgres
dist/run.sh -p
```

## Pull from a registry

By default, the docker-image of the microservice is transferred to the `dist/`-folder. This option can be turned off
by adding the `-r` flag to the `./bundle.sh -r`. Therefore, the image-file of the microservice is not transferred to the `dist/`-folder.
Then the image can be pulled from a registry when trying to run the images. The `./run.sh` also provides such an option to 
prevent the script from trying to load the image-file from the file-system.

```shell
./bundle.sh -r
dist/run.sh -r

# OR

# With postgres
./bundle.sh -r
dist/run.sh -pr
````

## Push to a registry

The microservice can be pushed to a local registry by running.

```shell
./release.sh
```

If you have no local registry run:

```shell
./registry/init-registry.sh
```

This will initialize a local docker-registry, where docker-images can be pushed/pulled.
