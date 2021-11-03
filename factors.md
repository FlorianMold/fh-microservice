# 12 Factors

## Implemented Factors

### 1. Codebase

> One codebase tracked in revision control, many deploys.

The first best practice of twelve-factor apps is to track it in a version control system. The principle states that an app should be tracked in a single code repository and must not share that repository
with any other apps. This principle advocates having a single codebase that can be built and deployed to multiple environments.

Following this principle, we have a single Git repository containing the source code of our Spring Boot application. 
This code is compiled and packaged and then deployed to one or more environments.

**We’re breaking this rule if we have to change the source code to configure it for a specific environment**

### 2. Dependencies

> Explicitly declare and isolate dependencies.

Next, the twelve-factor app should always explicitly declare all its dependencies. We should do this using a dependency declaration manifest.

In the context of using Spring Boot, when using a dependency tool like Maven/Gradle we get:

- Versioning by declaring specific versions of the dependencies with which our application works
- Isolation by bundling dependencies with the application

**I followed this principle by adding all dependencies inside `build.gradle.kts`.**

### 3. Configurations

> Store config in the environment.

A twelve-factor app should externalize all such configurations that vary between deployments. 
The recommendation here is to use environment variables for such configurations. This leads to a clean separation of config and code.

**In the microservice we have a database. We'll need the address and credentials of the database to connect to. This is most likely to change between deployments. The environment variables are saved inside the `.env` file and loaded by `docker-compose` and used by the docker-containers.**

### 4 Backing Services

> Treat backing services as attached resources.

Backing services are services that the application depends on for operation. For instance a database or a message broker. A twelve-factor app should treat all such backing services as attached resources. What this effectively means is that it shouldn't require any code change to swap a compatible backing service. The only change should be in configurations.

**In the microservice the database can be easily switched without a code change. We just have to change the datasource of spring to change between an in-memory h2-database and an external postgres-database. Just specify the correct environment variables. Run the correct docker-compose file and the application will automatically use the specified database.**

```shell
# -----------------------
# --- Postgres Config ---
# -----------------------

SPRING_DATASOURCE_URL=jdbc:postgresql://microservice-database:5432/compose-postgres
SPRING_DATASOURCE_USERNAME=compose-postgres
SPRING_DATASOURCE_PASSWORD=compose-postgres
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

### 5. Build, Release and Run

We should keep the stages for build, release, and run as separate. This separation is important to maintain application fidelity and integrity.

These stages occur in a sequence. Each stage has a different objective and produces output that is propagated to the subsequent stage.

Any code changes including emergency fixes should happen in the build stage and follow an established release cycle before being promoted to production.

Code that runs on the server should not be updated to fix a bug. A configuration setting should not be tweaked at runtime to overcome a Java performance problem. All of the code that goes into deployment comes from the build, which is based on code that is versioned in a bare Git repo.

For Spring Boot applications, this is easy to achieve with the development workflow for containers:

- Build: we compile the source code and build a Docker image.
- Release: we tag the image and push it to a registry.
- Run: we pull the image from the registry and run it as a container instance.

The microservice uses this strategy.

- Build Stage: 

```shell

# Compile the source code into a docker-image
./bundle.sh -r
```

- Release Stage:

For simplicity reasons, the docker-image is saved as a `.tar`-file inside the dist-folder. This is to simplify the testing process for this example. Normally the image would be pushed to a local docker registry and pulled by the `docker-compose` file inside the `dist`-folder.

```shell

# Push the image to a registry.
./release.sh
```

- Run Stage: 

```shell

# Start the application with a postgres-database.
./dist/run.sh -r
```

### 6. Processes

> Execute the app as one or more stateless processes.

A twelve-factor app is expected to run in an execution environment as stateless processes. In other words, they can not store persistent state locally between requests. They may generate persistent data which is required to be stored in one or more stateful backing services.

Sticky sessions are a violation of twelve-factor. Session state data should be stored outside the application in a datastore that offers time-expiration, such as Memcached or Redis.

**In the case of our example, we've got multiple endpoints exposed. A request on any of these endpoints is entirely independent of any request made before it.**

### 7. Port Binding

> Export services via port binding

Port Binding refers to an application binding itself to a particular port and listening to all the requests from interested consumers on that port. The port is declared as an environment variable and provided during execution.

Port binding is one of the fundamental requirements for microservices to be autonomous and self-contained.

Spring Boot embeds Tomcat in applications and exports HTTP as a service by binding to a port and listening to incoming requests to that port.

Spring does provide the ability to change the port used internally by the packaged Spring Boot application, but it is Kubernetes or Docker that will take care of the external port binding that makes the cloud-native application publicly accessible.

**In the microservice the port of the applications can be set by environment variables. Docker will take care of the port binding.**

### 8. Concurrency

> Scale out via the process model.

Traditionally, whenever an application reached the limit of its capacity, the solution was to increase its capacity by adding RAM, CPU, and other resources - a process called vertical scaling.

**Instead of making a single process even larger, we create multiple processes and then distribute the load of our application among those processes.**

**So long as a Spring Boot application is stateless, Kubernetes replica sets will take care of the creation of new pods with Docker containers that can handle increasing workloads concurrently.**

The twelve-factor methodology suggests apps to rely on processes for scaling. What this effectively means is that applications should be designed to distribute workload across multiple processes. Individual processes are, however, free to leverage a concurrency model like Thread internally.

Spring Boot does not help us much with this factor. We have to make sure that our application is stateless, and thus can be scaled out to many concurrent workers to support the increased load. All kinds of state should be managed outside the application.

**The microservice is stateless and thus can be scaled out to many concurrent workers to support the increased load. All kinds of state should be managed outside the application.**

### 9. Disposability

> Maximize robustness with fast startup and graceful shutdown.

The 9th principle of the Twelve-Factor App methodology is disposability, which insists that microservices should start up quickly and shut down gracefully.

The application cannot scale, deploy, or recover rapidly if it takes a long time to get into a steady state and shut down gracefully. If our application is under increasing load, and we need to bring up more instances to handle that load, any delay to startup could mean denial of requests during the time the application is starting up.

Spring Boot applications should be run inside containers to make them disposable. Containers are ephemeral and can be started or stopped at any moment.

### 10. Dev/Prod Parity

> Keep development, staging, and production as similar as possible.

It's typical for applications to be developed on local machines, tested on some other environments and finally deployed to production. It's often the case where these environments are different. For instance, the development team works on Windows machines whereas production deployment happens on Linux machines.

The twelve-factor methodology suggests keeping the gap between development and production environment as minimal as possible. These gaps can result from long development cycles, different teams involved, or different technology stack in use.

Containers made it possible to build once and ship to multiple target environments. They also allow to package all the dependencies including the OS.

Spring Boot applications are packaged in Docker containers and pushed to a Docker registry. Apart from using a Docker file to create a Docker image, Spring Boot provides plugins for building OCI image from source with Cloud-Native buildpacks.

**The microservice is deployed with docker-containers. Therefore the environment is the same everywhere.**

### 11. Logs

> Treat Logs as Event Streams.

Implemented

### 12. Admin Processes

