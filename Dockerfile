FROM java:8-jre
MAINTAINER Dmitriy Shevchenko <d.t.shevchenko@gmail.com>

ADD ./target/sh-server.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/sh-server.jar"]


EXPOSE 8081