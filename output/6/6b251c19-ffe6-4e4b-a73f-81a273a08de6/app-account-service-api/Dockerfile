FROM openjdk:11.0-jdk-slim-stretch
ARG DEPENDENCY=target
COPY ${DEPENDENCY}/app-account-service-api-1.0.0-SNAPSHOT.jar /home/app-account-service-api-1.0.0-SNAPSHOT.jar
RUN echo $ENVIROMENTS
ENTRYPOINT   [ "java","-jar","-Dspring.profiles.active=release","/home/app-account-service-api-1.0.0-SNAPSHOT.jar" ] 
