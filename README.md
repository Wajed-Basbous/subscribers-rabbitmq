# RabbitMQ User Creation Event System

## Project Overview
This project is a Java-based messaging system utilizing RabbitMQ for handling user creation events. The system includes components for publishing events and subscribing to those events to perform specific tasks, such as accounting updates and sending welcome emails.

## Components
- **EmitLogUserCreated**: A publisher that sends user creation events in JSON format to a RabbitMQ exchange.
- **AccountingUserSubscriber**: A subscriber that listens for user creation events and processes the user's email for accounting purposes.
- **WelcomeEmailSubscriber**: A subscriber that listens for user creation events and sends a welcome email to the new user.

## Technologies Used
- **Java** (JDK 8+)
- **RabbitMQ** (message broker)
- **JSON Library** (`org.json`)
- **Maven** (build tool)

## Installation

### Prerequisites
- **Java JDK 8+** installed on your system
- **Apache Maven** installed
- **RabbitMQ** server running locally or accessible remotely

### Project Setup
1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/rabbitmq-user-event-system.git
    cd rabbitmq-user-event-system
    ```

2. **Add JSON Library Dependency** (if using Maven):
    Ensure your `pom.xml` includes the following:
    ```xml
    <dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20240303</version>
    </dependency>
    ```

3. **Build the project**:
    ```bash
    mvn clean install
    ```

## Usage

### Starting the RabbitMQ Server
Ensure that your RabbitMQ server is running:
```bash
rabbitmq-server
```
## Compile the code
```bash
javac -cp $CP EmitLogUserCreated.java AccountingUserSubscriber.java WelcomEmailSUbscriber.java
```

## Example of how to run EmitLogUserCreated, use the following command:

```bash
java -cp $CP EmitLogUserCreated routing.key '{"username":"Wajed_basbous","email":"wajed.basbous@net.usj.edu.lb"}'
```

## Example of how to run AccountingUserSubscriber, use the following command:

```bash
java -cp $CP AccountingUserSubscriber routing.key
```

## Example of how to run WelcomeEmailSubscriber, use the following command:

```bash
java -cp $CP WelcomeEmailSubscriber routing.key 
```