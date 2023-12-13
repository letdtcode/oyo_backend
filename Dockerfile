FROM maven:3.8-openjdk-17
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-ea-oraclelinux8
COPY --from=build /target/OYO_Booking_backend-0.0.1-SNAPSHOT.jar OYO_Booking_backend.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","OYO_Booking_backend.jar"]
