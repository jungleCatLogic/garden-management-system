<div align="center">

# Garden Management System

A Spring Boot REST API for managing garden beds, plants, growing seasons, and maintenance activities

</div>

<br />

<!-- Table of Contents -->
# Table of Contents

- [About the Project](#about-the-project)
  * [Tech Stack](#tech-stack)
  * [Features](#features)
  * [Environment Variables](#environment-variables)
- [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
  * [Database Setup](#database-setup)
  * [Run Locally](#run-locally)
- [Usage](#usage)
  * [API Endpoints](#api-endpoints)
- [Contact](#contact)

<!-- About the Project -->
## About the Project

This Garden Management System provides a RESTful API for managing garden beds, plants, growing seasons, and maintenance activities. It allows users to track planting information, create garden bed layouts, record maintenance tasks, and associate plants with appropriate growing seasons.

<!-- TechStack -->
## Tech Stack

<details>
  <summary>Server</summary>
  <ul>
    <li><a href="https://spring.io/projects/spring-boot">Spring Boot</a></li>
    <li><a href="https://spring.io/projects/spring-data-jpa">Spring Data JPA</a></li>
    <li><a href="https://spring.io/guides/gs/rest-service/">Spring REST</a></li>
  </ul>
</details>

<details>
<summary>Database</summary>
  <ul>
    <li><a href="https://www.mysql.com/">MySQL</a></li>
  </ul>
</details>

<!-- Features -->
## Features

- Garden bed management (create, read, update, delete)
- Plant tracking with detailed information
- Maintenance activity logging
- Growing season management
- Plant-season relationship tracking
- RESTful API with JSON responses

<!-- Environment Variables -->
## Environment Variables

To run this project, you will need to modify the database connection properties in the `application.yaml` file:

```yaml
spring:
  datasource:
    username: your_username
    password: your_password
    url: jdbc:mysql://localhost:3306/garden_management
```

<!-- Getting Started -->
## Getting Started

<!-- Prerequisites -->
### Prerequisites

To run this project, you need to have the following installed:

- Java Development Kit (JDK) 17 or higher
- MySQL 
- Maven 
- An IDE such as Eclipse
- REST Web API Server such as AdvancedRestClient (ARC)




### Installation

1. Clone the repository
2. Navigate to the project directory

### Database Setup

Create the database and user in MySQL:

```sql
CREATE SCHEMA garden_management;
CREATE USER 'garden_user'@'localhost' IDENTIFIED BY 'user_garden_password';
GRANT ALL PRIVILEGES ON garden_management.* TO 'garden_user'@'localhost';
FLUSH PRIVILEGES;
```

Update the `application.yaml` file with your database credentials:

```yaml
spring:
  datasource:
    username: garden_user
    password: user_garden_password
    url: jdbc:mysql://localhost:3306/garden_management
```

### Run Locally

Using an IDE:
1. Import the project as a Maven project
2. Run `GardenManagementApplication.java` as a Java application

The application will run at `http://localhost:8080`

## Usage

### API Endpoints

Here are the main API endpoints available:

#### Garden Beds
- `GET /garden/bed` - Get all garden beds
- `GET /garden/bed/{id}` - Get garden bed by ID
- `POST /garden/bed` - Create a new garden bed
- `PUT /garden/bed/{id}` - Update a garden bed
- `DELETE /garden/bed/{id}` - Delete a garden bed

#### Plants
- `POST /garden/bed/{gardenBedId}/plant` - Add a plant to a garden bed
- `DELETE /garden/plant/{id}` - Delete a plant

#### Maintenance Activities
- `GET /garden/plant/{plantId}/activity` - Get all maintenance activities for a plant
- `POST /garden/plant/{plantId}/activity` - Add a maintenance activity to a plant

#### Growing Seasons
- `GET /garden/season` - Get all growing seasons
- `POST /garden/season` - Create a new growing season
- `POST /garden/plant/{plantId}/season/{seasonId}` - Add a plant to a growing season

#### Example Request (Create Garden Bed)

```json
POST /garden/bed
Content-Type: application/json

{
  "name": "Vegetable Garden",
  "location": "Backyard",
  "soilType": "Potting Soil",
  "sunExposure": "Partial Sun",
  "dimensions": "5ft x 5ft",
  "notes": "Raised bed with potting soil mixture"
}
```

<!-- Contact -->
## Contact

Project Creator - JungleCatLogic

Project Link: [https://github.com/JungleCatLogic/garden-management-system](https://github.com/JungleCatLogic/garden-management-system)
