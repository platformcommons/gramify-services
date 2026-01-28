FROM openjdk:8-alpine
RUN apk --no-cache add curl
RUN addgroup -S common-grp && adduser -S common-user -G common-grp

RUN mkdir -p /ignite/work
RUN chown -R common-user:common-grp /ignite

ARG JAR_FILE
ADD ${JAR_FILE} commons-report-service.jar
RUN chown common-user:common-grp commons-report-service.jar

USER common-user:common-grp
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom",  "-jar", "commons-report-service.jar"]
