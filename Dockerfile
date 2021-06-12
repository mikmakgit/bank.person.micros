FROM openjdk:8
ADD target/docker-bank-person-boot.jar docker-bank-person-boot.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "docker-bank-person-boot.jar"]