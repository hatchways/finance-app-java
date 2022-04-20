# Finance App Work Simulation

Welcome to the Java Finance App work simulation by [Hatchways](http://hatchways.io/). In this project, you will be
writing a couple of back-end API routes on a Java Spring Boot back-end API.

We will use [this rubric](https://drive.google.com/file/d/17p8-OdTxKF8bhCn_YwpQ4UPBIh13bDOo/view?usp=sharing) to
evaluate your submission. Please note that if your submission does not attempt to complete all of the requirements, or
does not pass our plagiarism screening, we will be unable to provide feedback on it. Please contact hello@hatchways.io
if you have any questions or concerns.

Below is some high level detail about the project. Good luck!

## Server

- Install [Java](https://www.oracle.com/java/technologies/downloads/)
- Install [Gradle](https://gradle.org/install/)

## Tips

### Provided functions

The project contains helper functions you can use to complete your tasks without writing any SQL or ORM logic:

* `AccountService.getByUserId` in the [service folder](./src/main/java/com/hatchways/finance/service) can be used to
  fetch accounts for a user
* `TransactionService.getForDateRange` in the [service folder](./src/main/java/com/hatchways/finance/service)
  can be used to fetch transactions by date

### Seed data

We've included sample data that the application has been configured to use. For more information on how the database is
set up, please reference [Database](#Database).

# REST API Server

## Language & Tools

- [Java 17](https://www.oracle.com/java/technologies/downloads/)
- [Spring Boot](https://spring.io/projects/spring-boot) - framework
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) and [SQLite](https://www.sqlite.org/) for the database (
  you will not be required to write any database queries in order to complete this project)

## Quickstart

This section contains all the information required for getting the server up and running. Note: Every command discussed
below should be run from the root directory.

### Install Gradle

Gradle is required to run this project. The installation instructions can be found [here](https://gradle.org/install/).

### Run the server

`gradle bootRun` - launches the server on port 8080.

**Note: it is typical for this command to stop at `80% EXECUTING`. The application has been started when you see a log
similar to the one below:**

```
Started FinanceApplication in 5.16 seconds (JVM running for 5.579)
```

### Testing Routes

Once the server is running, you can use cURL, Postman, or another tool of your choice to make requests to the API.

#### Example cURL Commands

You can log in as the seeded account with the following command:

```bash
curl --request POST 'localhost:8080/api/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "test@test.com",
    "password": "sample"
}'
```

You can then use the token returned from the `/api/login` request to make an authenticated request to get the user:

```bash
curl 'localhost:8080/api/user' \
--header 'Authorization: Bearer YOUR-TOKEN-HERE'
```

### Unit tests

`gradle test` - runs all the tests. The tests use [JUnit 5](https://junit.org/junit5/docs/current/user-guide/). We've
provided you with a few example tests to get you started.

#### Adding your own tests

If a ticket requires it, you can add a new test file to the `src/test/java/com/hatchways/finance` folder.

### Formatting

This project is formatted using [google-java-format](https://github.com/google/google-java-format).

## Database

**Note: No database setup should be required to get started with running the project.**

This project uses SQLite, which stores your tables inside a file.
The [`application.properties`](src/main/resources/application.properties) file configures the project to
use `database.db` for development. `database.db` is committed into the repository and already has seed data

#### Resetting the Database

If you would like to reset your development database, you have a few options:

* Run the application with `gradle bootRun -Pargs=seedData`: this will run your application as normal, but will
  immediately delete all the database entities and seed the starting data using
  the [`SeedDataUtil` class](src/main/java/com/hatchways/finance/util/SeedDataUtil.java) on startup.
* Deleting `database.db`: if you delete this file, the next time you run `gradle bootRun`, a new, empty database will be
  created for you.

`SeedDataUtil` uses sample data located in the [`resources`](./src/main/resources) directory to populate the database.
 
