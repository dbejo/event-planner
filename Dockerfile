FROM openjdk:21-ea-17-jdk
COPY target/event-planner-0.0.1-SNAPSHOT.jar event-planner-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/event-planner-0.0.1-SNAPSHOT.jar"]