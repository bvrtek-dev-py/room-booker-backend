FROM eclipse-temurin:24-jdk

WORKDIR /app

# Kopiowanie plików projektu do obrazu
COPY room-booker /app

# Budowanie aplikacji za pomocą Gradle
RUN ./gradlew clean build -x test

# Eksponowanie portu aplikacji
EXPOSE 8080

# Komenda startowa
CMD ["java", "-jar", "build/libs/room-booker-0.0.1-SNAPSHOT.jar"]