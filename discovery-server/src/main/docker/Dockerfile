FROM frolvlad/alpine-oraclejdk8:slim

ADD discovery-server.jar app.jar

RUN apk add --no-cache bash

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/app.jar"]
