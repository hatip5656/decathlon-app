# Decathlon Points Calculator

This project is a Decathlon points calculator with a Spring Boot backend and React frontend. It's dockerized for convenience, but you can also run the applications separately.

## Prerequisites
Java 17
Node.js
Maven

## Frontend (React)

Navigate to the frontend directory:

```bash
cd decathlonpoints-fe
```
Install dependencies:

```bash
npm install
```
Run the React app:

```bash
npm start
```
The app will be accessible at http://localhost:3000.

## Backend (Spring Boot)

Navigate to the backend directory:

```bash
cd decathlon-point-be
```

Build and run the backend:

```bash
mvn spring-boot:run
```

The backend will be accessible at http://localhost:8080.
Explore the Swagger UI at http://localhost:8080/swagger-ui/index.html#/.

## Dockerized Deployment

Clone the repository:

```bash
git clone https://github.com/hatip5656/decathlon-app.git
```
Navigate to the project root and Build the backend:

```bash
mvn clean install
```
Run the Docker containers:

```bash
docker-compose up -d
```
This will start the backend and frontend services.

## Usage

Open your browser and go to http://localhost:3000 to access the React frontend.
Define the sports in the UI and provide the necessary information.
Test data is available in [decathlon-point-be/src/main/resources/test-data.json](decathlon-point-be/src/main/resources/test-data.json).

The JSON object in the `test-data.json` file contains the following variables:

- `eventName`: The name of the sport.
- `basePoints`: The base points for the sport represent the starting point value assigned to the event. It serves as a reference point for calculating the points based on the athlete's performance. 
- `resultMultiplier`: The result multiplier for the sport is a factor that determines how much the athlete's result will contribute to the final points. It is multiplied by the athlete's result to calculate the points. 
- `resultExponent`: The result exponent for the sport is the power to which the athlete's result is raised during the points calculation. It allows for non-linear scaling of points based on the result. A higher exponent value will amplify the impact of the result on the points.