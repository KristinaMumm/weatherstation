FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/weatherstation.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
