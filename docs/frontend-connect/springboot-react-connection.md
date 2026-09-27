# React + Spring Boot Backend Connection Guide

## Objective

Connect the NovaRetail React frontend to the existing Spring Boot backend.

### Project Structure

```text
novaretail-main/
│
├── src/
│   └── main/
│       └── java/
│           └── com/example/demo/
│               ├── controller/
│               ├── service/
│               ├── repository/
│               ├── security/
│               └── config/
│
├── pom.xml
│
└── novaretail-frontend/
    ├── src/
    │   ├── pages/
    │   │   ├── Home.jsx
    │   │   ├── Login.jsx
    │   │   └── Register.jsx
    │   │
    │   ├── services/
    │   │   └── api.js
    │   │
    │   ├── App.jsx
    │   └── main.jsx
    │
    ├── package.json
    └── vite.config.js
```

---

# 1. Backend and Frontend Ports

NovaRetail uses two development servers.

### Spring Boot Backend

```text
http://localhost:8080
```

### React Frontend

```text
http://localhost:5173
```

The React application sends HTTP requests to the Spring Boot API.

```text
React
  │
  │ HTTP Request
  ▼
Spring Boot
  │
  ▼
MySQL
```

---

# 2. Install Axios

Inside the React frontend:

```bash
npm install axios
```

Axios is used to send HTTP requests from React to Spring Boot.

Example:

```javascript
api.post("/auth/login", data);
```

---

# 3. Create API Service

File:

```text
novaretail-frontend/src/services/api.js
```

Code:

```javascript
import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080",
    headers: {
        "Content-Type": "application/json"
    }
});

api.interceptors.request.use(
    (config) => {

        const token =
            localStorage.getItem("token");

        if (token) {
            config.headers.Authorization =
                `Bearer ${token}`;
        }

        return config;
    },

    (error) =>
        Promise.reject(error)
);

export default api;
```

---

# 4. Why Use an API Service?

Instead of writing:

```javascript
axios.post(
    "http://localhost:8080/auth/login",
    data
);
```

in every component, we use:

```javascript
api.post(
    "/auth/login",
    data
);
```

The base URL is configured once:

```javascript
baseURL: "http://localhost:8080"
```

This makes the frontend code cleaner.

---

# 5. Login Connection

File:

```text
src/pages/Login.jsx
```

The React login form sends:

```http
POST /auth/login
```

to:

```text
http://localhost:8080/auth/login
```

Example:

```javascript
const response = await api.post("/auth/login", {
    email: email,
    password: password
});
```

The backend returns a JWT token.

```text
React
  │
  │ email + password
  ▼
POST /auth/login
  │
  ▼
Spring Boot
  │
  │ JWT
  ▼
React
```

---

# 6. Store JWT Token

After successful login:

```javascript
const token = response.data.token;

localStorage.setItem(
    "token",
    token
);
```

The token is stored in the browser.

Check it in:

```text
Browser
→ Developer Tools
→ Application
→ Local Storage
→ http://localhost:5173
```

You should see:

```text
token = eyJ...
```

---

# 7. Automatically Send JWT

The Axios interceptor reads the token:

```javascript
const token =
    localStorage.getItem("token");
```

Then adds:

```http
Authorization: Bearer <JWT>
```

to protected requests.

Flow:

```text
React
  │
  │ GET /products
  │
  │ Authorization:
  │ Bearer eyJ...
  ▼
Spring Security
  │
  ▼
JwtFilter
  │
  ▼
Controller
```

This means you do not need to manually add the token to every API call.

---

# 8. Backend JWT Filter

The Spring Boot backend contains:

```text
JwtFilter.java
```

The filter checks the request:

```java
String authHeader =
        request.getHeader("Authorization");
```

It expects:

```text
Bearer <JWT>
```

The token is then validated.

If valid:

```text
JWT
 ↓
Email
 ↓
UserDetails
 ↓
Authentication
 ↓
Spring Security
```

The authenticated request can continue to the controller.

---

# 9. Authentication Endpoints

The backend authentication endpoints are:

### Register

```http
POST /auth/register
```

### Login

```http
POST /auth/login
```

These endpoints are public.

React can call them without a JWT.

Example registration:

```javascript
await api.post("/auth/register", {
    name: name,
    email: email,
    password: password
});
```

Example login:

```javascript
const response = await api.post("/auth/login", {
    email: email,
    password: password
});
```

---

# 10. CORS Configuration

Because React and Spring Boot run on different ports:

```text
React     → localhost:5173
Backend   → localhost:8080
```

the backend must allow the React origin.

Spring Security CORS configuration:

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration =
            new CorsConfiguration();

    configuration.setAllowedOrigins(
            List.of("http://localhost:5173")
    );

    configuration.setAllowedMethods(
            List.of(
                    "GET",
                    "POST",
                    "PUT",
                    "DELETE",
                    "PATCH",
                    "OPTIONS"
            )
    );

    configuration.setAllowedHeaders(
            List.of("*")
    );

    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration(
            "/**",
            configuration
    );

    return source;
}
```

Security configuration must enable CORS:

```java
.cors(cors ->
    cors.configurationSource(
        corsConfigurationSource()
    )
)
```

---

# 11. Allow OPTIONS Requests

Browsers can send an `OPTIONS` request before certain API requests.

Spring Security allows it with:

```java
.requestMatchers(
    HttpMethod.OPTIONS,
    "/**"
)
.permitAll()
```

This is important for browser-based React → Spring Boot communication.

---

# 12. Authentication Authorization Rules

The backend allows authentication endpoints:

```java
.requestMatchers(
    "/auth/**"
)
.permitAll()
```

Other endpoints can require authentication:

```java
.anyRequest()
.authenticated()
```

Therefore:

```text
/auth/register  → Public
/auth/login     → Public

Other APIs      → JWT required
```

---

# 13. Register Flow

When a new customer signs up:

```text
React Register Page
        │
        │ POST /auth/register
        ▼
Spring Boot AuthController
        │
        ▼
PasswordEncoder
        │
        ▼
UserRepository
        │
        ▼
MySQL
```

The public registration endpoint creates the user with:

```text
role = USER
```

A public registration form should not allow the user to choose:

```text
ADMIN
```

or:

```text
USER
```

The backend decides the role.

---

# 14. Login Flow

```text
React Login Page
        │
        │ email + password
        ▼
POST /auth/login
        │
        ▼
AuthController
        │
        ▼
UserRepository
        │
        ▼
PasswordEncoder
        │
        ▼
JWT generated
        │
        ▼
React
        │
        ▼
localStorage
```

---

# 15. Protected API Flow

Example:

```http
GET /products
```

React sends:

```http
Authorization: Bearer eyJ...
```

Backend:

```text
Request
  ↓
JwtFilter
  ↓
Extract JWT
  ↓
Extract email
  ↓
Load user
  ↓
Validate JWT
  ↓
Create Authentication
  ↓
Controller
```

---

# 16. Testing the Connection

## Step 1: Start MySQL

Make sure MySQL is running.

Database:

```text
spring_demo
```

---

## Step 2: Start Spring Boot

From:

```text
C:\New folder\novaretail-main
```

Run:

```bash
mvn spring-boot:run
```

Backend:

```text
http://localhost:8080
```

---

## Step 3: Start React

Open another terminal.

Go to:

```text
C:\New folder\novaretail-main\novaretail-frontend
```

Run:

```bash
npm run dev
```

Frontend:

```text
http://localhost:5173
```

---

# 17. Test Registration

Open:

```text
http://localhost:5173/register
```

Enter:

```text
Name: Ravi
Email: ravi@gmail.com
Password: password123
```

Click:

```text
Sign Up
```

React sends:

```http
POST http://localhost:8080/auth/register
```

Expected result:

```text
User Registered Successfully
```

---

# 18. Test Login

Open:

```text
http://localhost:5173/login
```

Enter the registered credentials.

React sends:

```http
POST http://localhost:8080/auth/login
```

Expected response contains:

```json
{
    "token": "eyJ..."
}
```

React stores it:

```javascript
localStorage.setItem("token", token);
```

---

# 19. Verify JWT

Open browser developer tools.

Go to:

```text
Application
→ Local Storage
→ http://localhost:5173
```

Verify:

```text
token
```

exists.

---

# 20. Verify API Request

Open:

```text
Developer Tools
→ Network
```

Make a protected API request.

Check request headers.

You should see:

```http
Authorization: Bearer eyJ...
```

This confirms that the React Axios interceptor is working.

---

# 21. Common Problems

## CORS Error

Example:

```text
blocked by CORS policy
```

Check that backend allows:

```text
http://localhost:5173
```

and that Spring Security contains:

```java
.cors(cors ->
    cors.configurationSource(
        corsConfigurationSource()
    )
)
```

---

## 401 Unauthorized

Usually means:

```text
JWT missing
```

or:

```text
JWT invalid
```

Check browser Local Storage for:

```text
token
```

and Network request headers for:

```http
Authorization: Bearer <token>
```

---

## 403 Forbidden

Usually means the request reached Spring Security but access was denied.

Check:

- User role
- Endpoint authorization
- `@PreAuthorize`
- JWT authentication
- CORS/preflight configuration

For admin endpoints, the backend can use:

```java
@PreAuthorize("hasRole('ADMIN')")
```

---

## Cannot Connect to Backend

Check that Spring Boot is running:

```text
http://localhost:8080
```

Check that React is running:

```text
http://localhost:5173
```

Also check the browser Network tab.

---

# 22. Logout

Logout can remove the JWT:

```javascript
localStorage.removeItem("token");
```

Then redirect to login:

```javascript
navigate("/login");
```

Example:

```javascript
const handleLogout = () => {

    localStorage.removeItem("token");

    navigate("/login");
};
```

---

# 23. Frontend Authentication Structure

Current authentication structure:

```text
React
│
├── Login.jsx
│       │
│       └── POST /auth/login
│
├── Register.jsx
│       │
│       └── POST /auth/register
│
└── services/
        │
        └── api.js
                │
                ├── baseURL
                └── JWT interceptor
```

Backend:

```text
Spring Boot
│
├── AuthController
│       │
│       ├── /auth/register
│       └── /auth/login
│
├── JwtFilter
│
├── JwtUtil
│
├── SecurityConfig
│
└── UserRepository
```

---

# 24. Complete Connection Flow

```text
                    NOVARETAIL

┌───────────────────────────────┐
│       React Frontend          │
│                               │
│  Login.jsx                    │
│  Register.jsx                 │
│  Home.jsx                     │
│                               │
│  Axios api.js                 │
└───────────────┬───────────────┘
                │
                │ HTTP / JSON
                │
                ▼
┌───────────────────────────────┐
│      Spring Boot Backend      │
│                               │
│  Controller                   │
│       ↓                       │
│  Service                      │
│       ↓                       │
│  Repository                   │
│       ↓                       │
│  MySQL                        │
│                               │
│  Spring Security              │
│       ↓                       │
│  JwtFilter                    │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│          MySQL                │
│                               │
│       spring_demo             │
└───────────────────────────────┘
```

---

# 25. Current NovaRetail Frontend-to-Backend Features

Implemented:

- React frontend
- Vite development server
- Axios
- React Router
- Register page
- Login page
- JWT storage
- Axios JWT interceptor
- Spring Security JWT authentication
- CORS configuration
- React → Spring Boot communication

---

# 26. Next Development Steps

After confirming the React/backend connection, continue in this order:

```text
1. Navbar
      ↓
2. Products Page
      ↓
3. Product Details
      ↓
4. Cart
      ↓
5. Address
      ↓
6. Checkout
      ↓
7. Razorpay Payment
      ↓
8. Orders
      ↓
9. Admin Dashboard
```

---

# Final Outcome

The NovaRetail React frontend is successfully connected to the Spring Boot backend.

Development architecture:

```text
React
localhost:5173
       │
       │ Axios
       ▼
Spring Boot
localhost:8080
       │
       │ JPA / Hibernate
       ▼
MySQL
spring_demo
```

Authentication architecture:

```text
Register
   ↓
Spring Boot
   ↓
USER created
   ↓
Login
   ↓
JWT generated
   ↓
localStorage
   ↓
Axios interceptor
   ↓
Authorization: Bearer <JWT>
   ↓
Spring Security
   ↓
Protected API
```
