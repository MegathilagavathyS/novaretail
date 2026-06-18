# Spring Boot E-Commerce Project Progress Tracker

## Phase 1: Spring Boot Fundamentals

### Completed

* Spring Boot Project Setup
* Maven Configuration
* Layered Architecture

  * Controller
  * Service
  * Repository
* REST APIs
* DTO Pattern
* Exception Handling
* Validation
* MySQL Integration
* JPA + Hibernate

### Learned

* Dependency Injection
* @RestController
* @Service
* @Repository
* @Entity
* @Autowired / Constructor Injection
* application.properties
* CRUD APIs

---

# Phase 2: User Management

## Completed

### User CRUD

* Create User
* Get All Users
* Get User By ID
* Update User
* Delete User

### Search Features

* Search By Email
* Search By Name

### Pagination

* Page Number
* Page Size
* Sorting

### DTO Mapping

* UserRequestDTO
* UserResponseDTO

### Learned

* Pageable
* Page<T>
* Sort
* Stream API
* DTO Pattern

---

# Phase 3: Address Module

## Completed

### User → Address

* Add Address
* Get Addresses

### Relationship

```text
User
 |
 | One-To-Many
 |
Address
```

### Learned

* OneToMany
* ManyToOne
* Cascade Types
* Foreign Keys

---

# Phase 4: Profile Module

## Completed

### User Profile

* Add Profile
* Get Profile

### Relationship

```text
User
 |
 | One-To-One
 |
Profile
```

### Learned

* OneToOne Mapping
* JoinColumn

---

# Phase 5: Authentication

## Completed

### Register

```http
POST /auth/register
```

### Login

```http
POST /auth/login
```

### Password Encryption

* BCrypt

### JWT Authentication

* Generate Token
* Validate Token
* Extract Email

### Learned

* PasswordEncoder
* JWT
* Security Filter
* Authentication

---

# Phase 6: Role Based Access Control

## Completed

### Roles

* ADMIN
* USER

### Authorization

```java
@PreAuthorize(...)
```

### Secured APIs

* Admin APIs
* User APIs

### Learned

* Spring Security
* Authorities
* Roles
* JWT + RBAC

---

# Phase 7: Category Management

## Completed

### CRUD

* Create Category
* Get Categories
* Update Category
* Delete Category

### Learned

* Parent Entity Design
* Product Categorization

---

# Phase 8: Product Management

## Completed

### CRUD

* Create Product
* Get Product
* Delete Product

### Product Fields

* Name
* Description
* Price
* Stock
* Category

### Learned

* Entity Relationships
* DTO Mapping

---

# Phase 9: Product Search & Filtering

## Completed

### Category Filtering

```http
GET /products/category/{id}
```

### Learned

* Derived Queries
* Repository Methods

---

# Phase 10: Shopping Cart

## Completed

### Add To Cart

```http
POST /cart/add
```

### View Cart

```http
GET /cart/{userId}
```

### Learned

* Cart Design
* CartItem Design
* OneToOne
* OneToMany

---

# Phase 11: Wishlist

## Completed

### Add Wishlist Item

### Get Wishlist

### Learned

* Wishlist Design
* User Preferences

---

# Phase 12: Order Management

## Completed

### Place Order

```http
POST /orders/place/{userId}
```

### Get Orders

### Get Order By Id

### Features

* Cart → Order Conversion
* OrderItem Creation
* Total Calculation

### Learned

* Business Logic Layer
* Transactions

---

# Phase 13: Inventory & Stock Management

## Completed

### Restock Product

```http
POST /inventory/restock
```

### Stock History

```http
GET /inventory/history/{productId}
```

### Learned

* Inventory Tracking
* Stock Movement
* Audit Records

---

# Phase 14: Payment Module

## Completed

### Razorpay Integration

### Create Payment

```http
POST /payments/create/{orderId}
```

### Payment Verification

```http
POST /payments/verify
```

### Payment Status

* PENDING
* SUCCESS

### Learned

* External APIs
* Payment Flow
* Order Payment Lifecycle

---

# Phase 15: Reviews & Ratings

## In Progress

### Completed

* Review Entity
* Review Repository
* Review Service
* Product Reviews
* Average Rating

### Learned

* Product Feedback System
* Aggregate Functions
* Duplicate Prevention

---

# Bugs & Issues Successfully Solved

## Infinite Recursion

Occurred In:

* Cart
* Wishlist
* Order
* Review

Solution:

```java
@JsonIgnore
@JsonManagedReference
@JsonBackReference
```

---

## 403 Forbidden

Solved By:

* JWT Authentication
* ROLE_ADMIN
* ROLE_USER
* Security Configuration

---

## JWT Token Expired

Solved By:

* Fixing JwtFilter
* Ignoring Auth Endpoints
* Correct Token Handling

---

## Foreign Key Errors

Solved By:

* Creating Parent Records First
* Understanding Relationships

---

## Payment Not Found

Solved By:

* Correct Repository Query
* Razorpay Order Mapping

---

# Current ER Diagram

```text
User
 ├── Address
 ├── Profile
 ├── Cart
 │     └── CartItem
 ├── Wishlist
 │     └── WishlistItem
 ├── Order
 │     └── OrderItem
 └── Review

Category
    └── Product
            ├── CartItem
            ├── WishlistItem
            ├── OrderItem
            ├── Review
            └── InventoryHistory

Order
   └── Payment
```

---

# Backend Completion Status

Core Backend Progress:

≈ 80% Complete

Completed:

✔ Authentication

✔ Authorization

✔ Users

✔ Products

✔ Categories

✔ Cart

✔ Wishlist

✔ Orders

✔ Inventory

✔ Payments

✔ Reviews

Remaining:

* Coupons & Discounts
* Order Status Workflow
* Shipment Tracking
* Email Notifications
* Dashboard Analytics
* Product Image Upload
* React Frontend
* Admin Dashboard
* Deployment
* CI/CD

---

# Recommended Next Order

1. Finish Reviews & Ratings
2. Coupons & Discount System
3. Order Status Workflow
4. Shipment Tracking
5. Product Image Upload
6. Email Notifications
7. Dashboard Analytics
8. React Frontend
9. Admin Dashboard
10. Deployment
