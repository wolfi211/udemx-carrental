FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY . .

RUN chmod 777 gradlew

RUN ./gradlew clean build -x test

EXPOSE 8080

CMD ["java", "-jar", "./build/libs/udemx-carrental-0.0.1-SNAPSHOT.jar"]