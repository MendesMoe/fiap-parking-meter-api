FROM openjdk:17-oracle
COPY target/spring-boot-application.jar /app/spring-boot-application.jar
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "spring-boot-application.jar"]

#$ docker build -t my-java-app .
#$ docker run -it --rm --name my-running-app my-java-app
# /Users/mohena/Desktop/java (meu projeto java, no meu macbook esta neste caminho)
# EXPOSE Preciso de um expose para openjdk:11?
