FROM openjdk:8-jre-alpine

# Create app directory
WORKDIR /usr/src/app

COPY target/*.jar ./EmployeeJooq.jar

EXPOSE 8000

CMD ["java", "-Dspring.profiles.active=dev", "-jar", "/usr/src/app/EmployeeJooq.jar"]
