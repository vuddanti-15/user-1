Command to build the jar file in QA instance
----------------------------------------------------------
mvn clean install -Dspring.profiles.active=qa

Command To run the jar file
---------------------------------------------
java -jar -Dspring.profiles.active=qa user-management.jar

https://frugalisminds.com/best-ways-run-spring-boot-app-via-command-line/
