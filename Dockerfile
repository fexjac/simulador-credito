FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre
ENV LANG=C.UTF-8 LANGUAGE=C.UTF-8 LC_ALL=C.UTF-8 TZ=America/Sao_Paulo
WORKDIR /app
COPY --from=build /workspace/target/quarkus-app/ /app/
EXPOSE 8080

RUN useradd -r -u 1001 quarkus && chown -R quarkus:quarkus /app
USER quarkus

ENTRYPOINT ["java","-XX:MaxRAMPercentage=75","-Dquarkus.http.host=0.0.0.0","-jar","/app/quarkus-app/quarkus-run.jar"]
