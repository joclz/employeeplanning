FROM openjdk:8
RUN mkdir -p /usr/local/employeeplanning
COPY db/h2-db-employeeplanning.mv.db /usr/local/employeeplanning
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} /usr/local/employeeplanning/app.war
ENTRYPOINT ["java","-jar","/usr/local/employeeplanning/app.war"]