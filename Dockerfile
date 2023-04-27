FROM openjdk:11
EXPOSE 8080
ADD target/crypto-recommendation.jar crypto-recommendation.jar
ENTRYPOINT ["java", "-jar","/crypto-recommendation.jar"]