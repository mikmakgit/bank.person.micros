FROM openjdk:8
ADD target/docker-bank-person-boot.jar docker-bank-person-boot.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "docker-bank-person-boot.jar"]