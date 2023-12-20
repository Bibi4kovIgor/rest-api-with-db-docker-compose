FROM amazoncorretto:17-alpine3.12
EXPOSE 8082
RUN addgroup -S spring-group \
    && adduser -S spring -G spring-group
USER spring:spring-group
ARG JAR_FILE=demo.jar
ARG WORKDIR_PATH=/app
WORKDIR ${WORKDIR_PATH}
COPY ${JAR_FILE} ${JAR_FILE}
RUN pwd \
    && ls
ENTRYPOINT ["java", "-jar", "/app/demo.jar"]