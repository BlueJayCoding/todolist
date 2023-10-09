FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ./target/*.jar /todolist.jar
ENTRYPOINT ["java","-jar","/todolist.jar"]