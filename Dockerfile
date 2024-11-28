# Imagen
FROM eclipse-temurin:21.0.5_11-jdk

EXPOSE 8080

WORKDIR /root

COPY ./pom.xml /root
COPY ./.mvn /root/.mvn
COPY ./mvnw /root

RUN ./mvnw dependency:go-offline

COPY ./src  /root/src

RUN ./mvnw clean install -DskipTests

ENTRYPOINT ["java", "-jar", "/root/target/security-0.0.1-SNAPSHOT.jar"]
