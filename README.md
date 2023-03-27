## Purpose
To support ATM operations - Withdraw/add money, balance enquiry, block card, change pin, notification

## Technology
1. Spring Boot
2. Eureka Discovery Client
3. MongoDB
4. SendGrid


## Architecture style
Microservices

1. Card Service - issues card on an account
2. Transaction Service - perofrms actual transaction on an account
3. Notification Service - sends alerts through emails
