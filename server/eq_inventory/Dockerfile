

FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app/server/eq_inventory
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21
WORKDIR /app/server/eq_inventory
COPY --from=build /app/server/eq_inventory/target/eq-inventory-0.0.1-SNAPSHOT.jar .
EXPOSE 3000
CMD ["java", "-jar", "eq-inventory-0.0.1-SNAPSHOT.jar"]