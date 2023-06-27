FROM openjdk:11
EXPOSE 8084
COPY target/userapplication.jar userapplication.jar

ENTRYPOINT ["java","-jar","/userapplication.jar"]