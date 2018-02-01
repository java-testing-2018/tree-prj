FROM frolvlad/alpine-oraclejdk8:slim

ADD gateway-server.jar app.jar

RUN apk add --no-cache bash

EXPOSE 9100:9100

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/app.jar"]
