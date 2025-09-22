E-Commerce Microservice Project
This project is an e-commerce system built on a microservice architecture.It includes core services such as:

Identity Service  
Catalog Service  
Order Service  
Inventory Service  
Payment Service

All services are orchestrated through an API Gateway and use Docker for easy deployment.

Getting Started
Below is a complete guide for setup, running, exploring the system, and making API calls.

1. Prerequisites
Ensure your machine has the following installed:

Docker Desktop👉 Download here  

⚠️ After installation, start Docker Desktop and wait until it runs stably.


Git👉 Download here

(Optional) IntelliJ IDEA & JDK 21 (if you want to explore the source code)👉 Download IntelliJ IDEA Community👉 Download OpenJDK 21



2. Clone the Project
Open a terminal and run:
git clone https://gitlab.com/mscnptpm/e-commerce.git


3. Build and Run with Docker
Run the following command to build and start the entire system:
docker-compose up --build -d


--build: Forces Docker to rebuild images (the first run may take a while ⏳).  

-d: Runs in background (detached mode).  

On the first run, Docker will download Java, Maven, RabbitMQ, and compile the source code. Subsequent runs will be much faster.



4. Exploring the System
After successful startup, you can access the following tools:
4.1. Eureka Dashboard (Service Management)
👉 http://localhost:8761  

Here, you can view all registered and running microservices.

4.2. RabbitMQ Dashboard (Message Queue System)
👉 http://localhost:15672  

Login credentials:  
Username: guest  
Password: guest



4.3. Swagger UI (Main API Gateway)
👉 http://localhost:8080/swagger-ui.html  

This is where you can view all APIs and test them.


5. Making API Calls
5.1. Login to Obtain a Token

To call protected APIs, you need an access token.

Endpoint:  

POST http://localhost:8080/identity-service/auth/login


Body (JSON):
{
  "username": "testuser",
  "password": "password123"
}


The response will include an accessToken.  

👉 Copy this token.  

If you don’t have an account, use the /register endpoint to create one.


5.2. Calling Protected APIs

Example: GET /catalog-service/api/products  

On Swagger UI or Postman:  

Click Authorize or open the Authorization tab.  
Type: Bearer Token  
Token: Paste the token you received.



🎉 You can now call the API and receive successful responses.

6. Shutting Down the System
To stop the entire system:
docker-compose down


This command stops and removes all containers, freeing up the environment.
