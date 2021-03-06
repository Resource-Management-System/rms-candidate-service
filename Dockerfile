FROM openjdk:15
EXPOSE 5004

COPY target/rms-candidate-service*.jar /rms-candidate-service.jar

ENTRYPOINT ["java", "-jar", "/rms-candidate-service.jar"]
