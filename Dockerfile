FROM openjdk:21-ea-17-jdk

MAINTAINER tunombre_o_alias

COPY target/portfolio_backend.jar portfolio_backend.jar

ENTRYPOINT ["java","-jar","/portfolio_backend.jar"]