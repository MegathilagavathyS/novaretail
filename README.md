# 🚀 Spring Boot REST API Project

A simple REST API built using Spring Boot demonstrating CRUD operations with in-memory storage.

---

# 📁 Folder Structure

src/main/java/com/example/demo

- DemoApplication.java

- controller/
  - UserController.java

- model/
  - User.java

- service/ (optional - for business logic)

- repository/ (optional - for database layer)

src/main/resources

- application.properties

---

# 🧠 Project Overview

This project demonstrates how a REST API works using Spring Boot with a simple architecture.

It includes:

- REST Controller for handling HTTP requests
- Model class for representing data
- In-memory list to store data (no database)
- CRUD operations (Create, Read, Update, Delete)

---

# 🏗️ Architecture (Concept)

Client → Controller → Service (optional) → Repository (future DB) → Model

---

# 🔌 API Endpoints

| Operation | Method | Endpoint |
|----------|--------|----------|
| Create User | POST | /users |
| Get Users | GET | /users |
| Update User | PUT | /users/{id} |
| Delete User | DELETE | /users/{id} |

---

# 🧩 Key Concepts

## 1. Controller
Handles all incoming HTTP requests and sends responses.

## 2. Model
Represents the data structure (User object).

## 3. REST API
Uses HTTP methods to perform operations on resources.

## 4. In-Memory Storage
Data is stored temporarily using a list (no database used).

---

# 🛠️ Technologies Used

- Java
- Spring Boot
- Spring Web
- Maven

---

# ▶️ How to Run

## Step 1: Build Project
Use Maven to build the project.

## Step 2: Run Application
Start the Spring Boot application using Maven or your IDE.

## Step 3: Access Application
The server will start at:
http://localhost:8080

---

# 🧪 How to Test APIs

You can test APIs using:

- Postman
- Browser (for GET requests)
- cURL
- IntelliJ HTTP client

---

# 📌 Important Annotations

- @RestController → Defines REST API controller
- @RequestMapping → Base URL mapping
- @GetMapping → Read data
- @PostMapping → Create data
- @PutMapping → Update data
- @DeleteMapping → Delete data
- @RequestBody → Reads JSON request body
- @PathVariable → Reads URL parameters

---

# 🚀 Future Improvements

- Add Spring Data JPA
- Connect MySQL/PostgreSQL database
- Add Service layer
- Add Exception handling
- Add Validation
- Add Swagger API documentation
- Add JWT Authentication

---

# 🎯 Quick Revision Summary

- Controller → Handles API requests
- Model → Data representation
- Service → Business logic (optional)
- Repository → Database layer (future)
- CRUD → Core operations of REST API
<img width="619" height="322" alt="image" src="https://github.com/user-attachments/assets/486b19db-4e74-4c0a-a628-a947def9daf2" />
