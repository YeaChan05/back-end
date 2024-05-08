FROM openjdk:17-jdk
ADD build/libs/*SNAPSHOT.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-jar", "-Dspring.config.location=file:/config/", "-Dspring.profiles.active=prod", "/app.jar"]

