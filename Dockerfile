FROM gradle:8.5-jdk21 AS builder
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle format --no-daemon
RUN gradle build --no-daemon --stacktrace

FROM openjdk:21-jdk-slim
COPY --from=builder /home/gradle/project/core/api/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]
