FROM maven:3.5-jdk-11 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean install -DskipTests -DtrimStackTrace=false -Dversion=1.0.0

FROM gcr.io/distroless/java:11
COPY --from=build /usr/src/app/target/car-servicing-appointment-scheduler-1.0.0.jar /usr/app/car-servicing-appointment-scheduler-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/car-servicing-appointment-scheduler-1.0.0.jar"]