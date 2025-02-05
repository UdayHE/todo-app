FROM openjdk:21-jdk-slim

RUN mkdir -p /etc/todo-app

WORKDIR /etc/todo-app

ADD build/libs/*.jar ./

CMD java $JAVA_OPTS -jar *.jar

EXPOSE 8080
