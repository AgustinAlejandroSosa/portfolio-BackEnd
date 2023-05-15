FROM openjdk:20-ea-17-oracle

MAINTAINER AgustinAlejandroSosa

COPY target/portfolio_backend.jar portfolio_backend.jar

ENTRYPOINT ["java","-jar","/portfolio_backend.jar"]