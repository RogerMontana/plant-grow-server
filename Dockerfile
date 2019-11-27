FROM amazoncorretto:11
COPY ./build/libs/plant-grow-server-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
USER 1000
CMD ["java", "-jar", "plant-grow-server-0.0.1-SNAPSHOT.jar", "com.rasp.server.ServerApplication"]