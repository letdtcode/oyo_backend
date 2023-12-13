FROM openjdk:17
EXPOSE 8081
ADD target/OYO_Booking_backend-0.0.1-SNAPSHOT.jar OYO_Booking_backend.jar
ENTRYPOINT ["java","-jar","OYO_Booking_backend.jar"]