## Purpose
To support ATM operations - Withdraw/add money, balance enquiry, block card, change pin, notification

## Technology
Spring Boot
Eureka Discovery Client
MongoDB
SendGrid


## Architecture style
Microservices

1. Card Service - issues card on an account
2. Transaction Service - perofrms actual transaction on an account
3. Notification Service - sends alerts through emails
