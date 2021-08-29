# Sandbox project for Spring Boot, Spring Data, Swagger Codegen & Apache Commons CSV

In this small sandbox data from Swagger Petstore are loaded, written into a database and exported to CSV.

# Requirements
- Java 11, e.g. [OpenJDK](https://openjdk.java.net/projects/jdk/11/)
- [Apache Maven](http://maven.apache.org)
- database, e.g. Postgres or H2

# DB configuration
change values in [application.properties](src/main/resources/application.properties)

# Build
```
mvn clean install
```

# Usage
in `target` folder:
```bash
usage: java -jar spring-data-swagger-codegen.jar
 -export <file>   path and file name of the export file
 -load            load all pets into database

e.g.
java -jar spring-data-swagger-codegen.jar -load -export /tmp/export.csv
```
