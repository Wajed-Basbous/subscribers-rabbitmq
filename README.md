# User Event Management System with RabbitMQ

## Overview
This project demonstrates a simple user event management system using RabbitMQ as a message broker. It includes two main components:
- **EmitUserCreatedTopic**: A publisher that emits user-created events.
- **ReceiveEmailTopic**: A subscriber that listens for user-created events and handles them either by adding the user to an accounting system or sending a welcome email.

## Running the Application
Run EmitUserCreatedTopic by including for exemple "user.created.accounting" "User created: Wajed Basbous, Email: wajed.basbous@net.usj.edu.lb"

Run ReceiveEmailTopic by using for example "user.created.accounting" "user.created.email"