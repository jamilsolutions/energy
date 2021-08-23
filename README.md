# Energy Consumption API

Energy Consumption API project consists a couple of the endpoints like GET /consumption, and POST /event and GET 
/microgeneration for a technical test using Java Technology with Spring Boot, RESTFUL, build in Gradle and persistence 
by MongoDB.


Requirements:

1. Have the MongoDB database installed on the local machine or server:
   - https://docs.mongodb.com/manual/administration/install-community/
   > Note: Keep MongoDB Compass installation enabled.
   
2. Access the database

3. Create energy database if necessary.

4. Having Java 8 or higher installed on your computer or laptop:
   - https://www.java.com/pt-BR/download/manual.jsp

5. Clone the project by running the command in a folder of your choice:
```bash
   git clone https://github.com/jamilsolutions/energy.git
```

6. Enter the directory where the project was cloned and open a command prompt with Git Bash:
   >Note: 1. If you do not have git installed use this link to install: 
   > - https://git-scm.com/downloads
   > - Git Tutorial at https://git-scm.com/book/pt-br/v2

7. Edit the <PROJECT PATH>/energy/src/main/resources/application.yml file with the database settings:
   >Example:
   > - spring.data.mongodb.host=localhost
   > - spring.data.mongodb.port=27017
   > - spring.data.mongodb.database:energy
 
8. Run the command in the project <PROJECT PATH>/energy with the command:
```bash   
./gradlew bootJar
```  
   
9. Run the project with the command
```bash
cd /build/libs/
java -jar energy-0.0.1-SNAPSHOT.jar
```
   
10. Open Postman and import the Secure collection Energy.postman_collection.json:
```bash
Postman -> File -> Import -> Select folder or Upload the file -> Confirm import.
```
    
11. Run the collection Energy:
```bash
Run Collection Runner -> Run Energy.
```

12. Completed. Great job!


