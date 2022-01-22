FROM markhobson/maven-chrome:jdk-11
ADD build/libs/ccbc-worker-0.0.1.jar ccbc-worker.jar

ENTRYPOINT ["java", "-jar","/ccbc-worker.jar"]