FROM openjdk:21
EXPOSE 8080
ADD target/springbootAppBackend.jar springbootAppBackend.jar
ENTRYPOINT ["java","-jar","springbootAppBackend.jar"]