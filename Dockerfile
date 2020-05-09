FROM java:8
ADD /target/trending-repos-app.jar /opt/app/trending-repos-app.jar
RUN ls -l ./opt/app/
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/opt/app/trending-repos-app.jar"]