

FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY server/eq_inventory/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/target/eq-inventory-0.0.1-SNAPSHOT.jar .
EXPOSE 3000
CMD ["java", "-jar", "eq-inventory-0.0.1-SNAPSHOT.jar"]