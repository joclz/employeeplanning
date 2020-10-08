FROM openjdk:8
RUN mkdir -p /usr/local/employeeplanning
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} /usr/local/employeeplanning/app.war
ENTRYPOINT ["java","-jar","/usr/local/employeeplanning/app.war"]