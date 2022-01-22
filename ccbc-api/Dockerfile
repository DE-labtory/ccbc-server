FROM markhobson/maven-chrome:jdk-11
ADD build/libs/ccbc-api-0.0.1.jar ccbc-api.jar
RUN apt-get update && apt-get install wget \
    && wget -q https://github.com/pinpoint-apm/pinpoint/releases/download/v2.3.0/pinpoint-agent-2.3.0.tar.gz \
    && tar xvzf pinpoint-agent-2.3.0.tar.gz

EXPOSE 8080
ENTRYPOINT ["java","-jar","-javaagent:pinpoint-agent-2.3.0/pinpoint-bootstrap.jar", "-Dprofiler.transport.grpc.collector.ip=34.64.246.37","-Dpinpoint.applicationName=ccbc-api","/ccbc-api.jar"]