FROM openjdk:11.0.13
# Copy project dependencies from the build stage
ADD build/libs/premier-league-web-scraper-*.jar web-scraper.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","web-scraper.jar"]
