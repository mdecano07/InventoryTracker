# Library Inventory Tracker
A Spring Boot application to be able to track a library's inventory, along with other services such as borrowing and 
returning items and checking a current user's loan. 

## Technologies
* Java 11
* Spring Boot
* Maven

## How to run the application
To run this project
1. Clone repository
2. Run **_mvn clean install_**, to pull all the dependencies from the maven repository.
3. Press run on the main class.
  

## System Design
The functionalities of the application is split into 3 different services
* Data Service
    * Input: A list of historical data with double values.
    * Output: Returns the var in double value.
* Loan Service
    * Input: A list of all historical data for each trade with double values.
    * Output: Returns the var in double value.
* Inventory Service

## System Assumptions
1. Clone repository








