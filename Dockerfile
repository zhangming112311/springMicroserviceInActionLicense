#layered JARs
FROM openjdk:11-slim as build
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract
FROM openjdk:11-slim
WORKDIR application
COPY --from=build application/dependencies/ ./
COPY --from=build application/spring-boot-loader/ ./
COPY --from=build application/snapshot-dependencies/ ./
COPY --from=build application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
##mvn clean package 
#docker build . --tag licensing-service
#docker run -it -p8080:8080 licensing-service:latest
#docker run -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -it -p8080:8080 jboss/keycloak