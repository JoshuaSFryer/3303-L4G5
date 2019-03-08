FROM openjdk:8-jre-slim
COPY target/ /home/app/target/
ENTRYPOINT ["java", "-jar", "/home/app/target/Scheduler-1.0.jar"]
