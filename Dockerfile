FROM openjdk:19-jdk-alpine3.16

COPY "./target/Prueba-Tecnica-INTCOMEX-0.0.1-SNAPSHOT.jar" "app.jar"

EXPOSE 3000

ENTRYPOINT ["java", "-jar", "app.jar"]