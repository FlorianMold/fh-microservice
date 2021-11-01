# Running the microservice

The configuration for the services is stored inside the .env file.

## Run the microservice with an integrated database

When using the H2 DB enable the `H2 Config` Section and remove the `Postgres Config` section.

```shell
./run.sh
````

This will load the docker-image and runs `docker-compose up` to start the microservice.

## Run the microservice with a postgres database

When using the Postgres DB enable the `Postgres Config` and remove the `H2 Config` section.

```shell
./run.sh -p
````

This will load the docker-images for the `microservice` and the `postgres`-database to start the
application.

## Pull from a registry

By default, the docker-image of the microservice is loaded from the file-system. This option can be turned off
by adding the `-r` flag to the `./run.sh -r`. This does not try to load the microservice-image-file from the file-system.
Therefore, the image can be pulled from a registry.

```shell
./run.sh -r

#OR

./run.sh -pr
````
