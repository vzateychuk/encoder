FROM amazoncorretto:17

COPY ./build/libs/signer-srv-*.jar /app/signer-service.jar
WORKDIR /app

CMD ["java", "-jar", "./signer-service.jar"]
