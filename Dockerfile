FROM openjdk:8
COPY h2-db-employeeplanning.mv.db /usr/local
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]