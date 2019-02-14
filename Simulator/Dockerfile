#FROM openjdk:8
FROM ubuntu:latest
RUN apt-get update && apt-get install -y \
    openjdk-8-jdk \
    iputils-ping
RUN apt-get install -y \
    maven
COPY ./ /tmp
WORKDIR /tmp
CMD ["mvn", "clean", "package", "-DskipTests"]
CMD ["java", "-jar", "target/Simulator-1.0.jar", "/testEvents.txt"]
