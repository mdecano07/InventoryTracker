# Library Inventory Tracker

A Spring Boot application to be able to track a library's inventory, along with other services such as borrowing and
returning items and checking a current user's loan.

## Technologies

* Java 11
* Spring Boot
* Maven

## How to run the application

To run this project

1. Git clone repository
2. Run **_mvn clean install_**, to pull all the dependencies from the maven repository.
3. Press run on the main class. Make sure you enable annotation processing as I am using Lombok.

## System Design

* The models used are Item, Loan, User which represents the core entities in the system.

1. Item represents library items, including their unique IDs, types (e.g., book, DVD), and titles which is based on the
   csv input file.
2. Loan represents loan records, indicating the user, the item they borrowed, and the loan start date.
    * This object was introduce to represent the loan service of the application. This way, when a loan was made,
      the existing Item did not need to be updated.
    * This object is used to link between item data and user data.
3. User represents information about library users.

* The functionalities of the application is split into 3 different services:

1. Data Service
    * This is the data layer of the application. It's primary responsibility is to manage the data, from initialising
      the models and provide interaction to update the data.

2. Loan Service
    * This service handles the borrowing and returning of library items.
    * It communicates with the DataService to add and remove loan records.
    * Users can borrow items by providing the unique item IDs and user ID.
    * Users can return items by providing the unique item IDs and user ID, which are used to look up and remove loan
      records.
3. Inventory Service
    * This service interacts with the DataService to retrieve and manage the inventory data.
    * It provides methods to query the current inventory, check item availability, find overdue items,
      and get items associated with a specific user.

## System Assumptions

1. Loading and managing the library's inventory data from CSV files is only done one time during application start-up.
2. The system assumes the CSV file is accurate and up-to-date with limited data validation performed.
3. No security layer is implemented to restrict access to the application to specific users.
4. As user and loan data are in memory - there is no data back-up and recovery plan.
5. Item uniqueness is assumed - no data handling on duplicate items.
6. User is not able to interact with the application - in the future, APIs can be exposed for access to the services.
7. Application assumes that there is only one data source. Other data sources such as a database will have to be
   implemented.









