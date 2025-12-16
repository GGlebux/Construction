# ------------ Stage 1: Build ------------
FROM maven:3.9 AS builder
WORKDIR /app

# Копируем pom и кэшируем зависимости
COPY ../pom.xml .
RUN mvn dependency:go-offline -B


# Копируем исходники и собираем
COPY ../src ./src
RUN mvn -Pprod -DskipTests package

# ------------ Stage 2: Run ------------
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Копируем jar из предыдущей стадии
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
