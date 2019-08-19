FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD zoran-core/target/core-0.0.1-SNAPSHOT.jar core.jar
RUN sh -c 'touch /core.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /core.jar","-Dspring.profiles.active=prod" ]