#FROM maven:3.8-openjdk-17
#COPY . .
#RUN mvn clean package -DskipTests
FROM eclipse-temurin:17-jre-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/OYO_Booking_backend-0.0.1-SNAPSHOT.jar OYO_Booking_backend.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","OYO_Booking_backend.jar"]
