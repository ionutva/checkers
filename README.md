cd checkers-backend
mvn clean package

Place your frontend files (index.html, app.js) under src/main/resources/static in the backend project.

java -jar target/checkers-0.0.1-SNAPSHOT.jar
Application will start on port 8080 by default.

Access API at http://localhost:8080/api/checkers

http://localhost:8080/
