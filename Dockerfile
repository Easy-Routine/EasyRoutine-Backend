FROM gradle:8.5-jdk21 AS builder
ENV GOOGLE_CLIENT_ID=dummy \
    GOOGLE_CLIENT_SECRET=dummy \
    NAVER_CLIENT_ID=dummy \
    NAVER_CLIENT_SECRET=dummy \
    KAKAO_CLIENT_ID=dummy \
    KAKAO_CLIENT_SECRET=dummy \
    JWT_SECRET_KEY=dummy
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle format --no-daemon
RUN gradle build --no-daemon --stacktrace

FROM openjdk:21-jdk-slim
COPY --from=builder /home/gradle/project/core/api/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]



