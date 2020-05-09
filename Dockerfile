FROM java:8
ADD /target/trending-repos-app.jar trending-repos-app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "trending-repos-app.jar"]