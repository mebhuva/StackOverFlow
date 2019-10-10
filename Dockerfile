#FROM openjdk:8-jdk-alpine
FROM openjdk
COPY "./build/libs/StackOverflow-0.0.1-SNAPSHOT.jar" app/StackOverflow/
WORKDIR app/StackOverflow/
EXPOSE 8070
ENTRYPOINT ["java", "-jar","StackOverflow-0.0.1-SNAPSHOT.jar"]