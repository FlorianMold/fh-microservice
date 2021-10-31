# Running the microservice

The configuration for the services is stored inside the .env file.

## Run the microservice with an integrated database

When using the Postgres DB enable the `H2 Config` Section and remove the `Postgres Config` section.

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
