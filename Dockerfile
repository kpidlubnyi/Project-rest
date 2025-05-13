FROM openjdk:25-ea-21-slim-bullseye
WORKDIR /app
COPY target/filmcritic-0.0.1-SNAPSHOT.jar filmcritic.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar" ,"filmcritic.jar"]