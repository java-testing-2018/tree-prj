FROM frolvlad/alpine-oraclejdk8:slim

VOLUME /tmp

ADD tree-service.jar app.jar

RUN sh -c 'touch /app.jar'

RUN apk add --no-cache bash

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/app.jar"]
