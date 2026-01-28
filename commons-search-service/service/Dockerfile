FROM eclipse-temurin:8-jdk

# --- Create non-root user and set up work directory ---
RUN groupadd -r common-grp && useradd -r -g common-grp common-user
RUN mkdir -p /ignite/work && chown -R common-user:common-grp /ignite

# --- Copy jar and fix ownership ---
ARG JAR_FILE
COPY ${JAR_FILE} /app/commons-search-service.jar
RUN chown -R common-user:common-grp /app

WORKDIR /app
USER common-user:common-grp

# --- Run app ---
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "commons-search-service.jar"]
