#FROM openjdk:8
FROM ubuntu:latest
RUN apt-get update && apt-get install -y \
    openjdk-8-jre \
    iputils-ping
COPY ./ /tmp
WORKDIR /tmp
CMD ["java", "-jar", "target/Scheduler-1.0.jar"]
