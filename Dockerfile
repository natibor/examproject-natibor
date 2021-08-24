FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/vizsgaremek-nagytibor-1.0-SNAPSHOT.jar /opt/app/vizsgaremek-nagytibor.jar
CMD ["java", "-jar", "/opt/app/vizsgaremek-nagytibor.jar"]