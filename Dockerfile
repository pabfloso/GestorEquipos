FROM openjdk:8-alpine

RUN mkdir /usr/local/gestorEquipos
COPY target/*.jar /usr/local/gestorEquipos/app.jar
COPY ./src/main/resources/logback-spring.xml /usr/local/gestorEquipos/logback-spring.xml
COPY ./src/main/resources/application-docker.properties /usr/local/gestorEquipos/application-docker.properties

EXPOSE 80

CMD ["java","-jar","-Dspring.config.location=/usr/local/gestorEquipos/application-docker.properties", "-Dlogging.config=/usr/local/gestorEquipos/logback-spring.xml", "/usr/local/gestorEquipos/app.jar"]