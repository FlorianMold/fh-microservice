FROM openjdk:11

MAINTAINER Florian Mold

RUN groupadd --gid 1000 microservice
RUN useradd --uid 1000 --gid microservice --shell /bin/bash --create-home microservice

USER microservice:microservice

ARG JAR_FILE=build/libs/\*.jar
COPY ${JAR_FILE} microservice.jar

ENTRYPOINT ["java","-jar","/microservice.jar"]
