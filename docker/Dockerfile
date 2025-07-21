FROM eclipse-temurin:24-jdk

WORKDIR /app

# Kopiowanie plików projektu do obrazu
COPY room-booker /app

# Budowanie aplikacji za pomocą Gradle
RUN ./gradlew clean build -x test -x spotlessCheck

# Eksponowanie portu aplikacji
EXPOSE 8080
EXPOSE 5005

# Komenda startowa
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "build/libs/room-booker-0.0.1-SNAPSHOT.jar"]