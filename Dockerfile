FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM tomcat:11.0.24-jdk17-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

# Desactiva el puerto interno de apagado 8005
RUN sed -i 's/<Server port="8005"/<Server port="-1"/' \
    /usr/local/tomcat/conf/server.xml

# Cambia el puerto HTTP de Tomcat de 8080 a 10000
RUN sed -i 's/port="8080"/port="10000"/' \
    /usr/local/tomcat/conf/server.xml

COPY --from=build /app/target/smartdevices.war \
    /usr/local/tomcat/webapps/ROOT.war

EXPOSE 10000

CMD ["catalina.sh", "run"]
