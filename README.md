# Loan Management Application - Setup Guide

The Loan Management Application is a Java-based application for managing loan applications, loans, repayments, and payment terms. This README provides an overview of the application and instructions for setting it up and using it.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java Development Kit (JDK)**: Make sure you have Java SE 8 or higher installed on your system.

- **Database**: Set up a  MySQL database and create a database schema. Update the application configuration with the database connection details (see [Configuration](https://github.com/vedullakiran/loans/blob/master/src/main/resources/application.properties)).

- **Build Tool**: This project uses a build tool Gradle. Ensure you have the build tool installed.

## Setup And Run

Follow these steps to download and set up the Loan Management Application:

1. Clone the repository:
   ```
   git clone https://github.com/vedullakiran/loans.git)https://github.com/vedullakiran/loans.git
   ```
2. Install Mysql
3. create database in Mysql: `create DATABASE loan_system;`
4. build the applciaiton `./gradlew build`
5. run the application `./gradlew bootRun`
6. postman collection 
  ```
   https://api.postman.com/collections/8502250-acd0416b-b0bc-422a-9e5c-53341351cbc3?access_key=PMAT-01HAKV37KCYDR5BDYBV6DTZ0T3
```
## User flow
testing 
Steps for basic flow of from Loan request to Loan repayment.

1. User creates loan application
2. Admin approves loan application. Which creates a loan associated with loan application and loan repayment schedules associated with a loan.
3. User repays the loan which can close one or more than one repayment schedules.
4. When all repayments schedules are closed the loan is marked as REPAID


