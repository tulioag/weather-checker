FROM maven:3-openjdk-11 AS builder
WORKDIR /app
ADD . .
RUN  mvn clean compile assembly:single
RUN zipinfo target/weather-checker-1.0-SNAPSHOT-jar-with-dependencies.jar

FROM gcr.io/distroless/java:11
WORKDIR /app
COPY --from=builder /app/target/weather-checker-1.0-SNAPSHOT-jar-with-dependencies.jar .
CMD ["weather-checker-1.0-SNAPSHOT-jar-with-dependencies.jar"]