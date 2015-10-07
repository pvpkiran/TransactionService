#Transaction Service

Look into CodeChallenge.pdf file for details about the project.

1. This is a maven project. To build this, run
		mvn clean package 
   from the command line.
3. This will create a jar file in target folder. To run the application 
		java -jar target\transactionservice-1.0.jar
    Alternatively, It can be run by opening the project in any IDE and running TransactionApplication file as a java application
4. By default the server starts in port 8080. If you want to change it, then you would have to run the application with 
	-Dserver.port=<portnumber> option
5. To run integration test cases run
	mvn test -Dtest=TransactionControllerIT
  make sure server is running before Integration test cases are executed.