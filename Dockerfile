# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-focal as builder
WORKDIR /app
COPY . .
RUN javac Main.java

# Stage 2: Create the final, smaller image
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=builder /app/Main.class .
COPY --from=builder /app/Main$MyHandler.class .
CMD ["java", "Main"]
