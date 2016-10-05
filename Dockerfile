FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/pigallery.jar /pigallery/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/pigallery/app.jar"]
