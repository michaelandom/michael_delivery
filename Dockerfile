FROM openjdk:17-jdk-slim
WORKDIR /app

COPY target/michael_delivery-0.0.1-SNAPSHOT.jar /app/michael_delivery-0.0.1-SNAPSHOT.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "michael_delivery-0.0.1-SNAPSHOT.jar"]
