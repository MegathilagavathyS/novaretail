# NovaRetail 🛒

### E-Commerce Backend REST API

NovaRetail is a **Spring Boot-based E-Commerce Backend REST API** designed to simulate the core functionality of a real-world online shopping platform.

The project follows a layered architecture and includes authentication, product management, search and filtering, cart and wishlist functionality, inventory management, coupons, order processing, Razorpay test-mode payments, reviews and ratings, shipping addresses, order tracking, and dashboard analytics.

---

## 📌 Project Overview

NovaRetail was developed to understand and implement the backend architecture of a real-world e-commerce application.

The application provides REST APIs for:

* User registration and login
* JWT authentication
* Role-based authorization
* Category management
* Product management
* Product search
* Product filtering
* Product sorting
* Wishlist management
* Shopping cart
* Inventory and stock management
* Inventory history
* Coupon management
* Order placement
* Order status management
* Razorpay payment integration
* Payment verification
* Product reviews and ratings
* Shipping addresses
* Order tracking
* Dashboard analytics

---

# 🚀 Features

## 1. Authentication & Authorization

NovaRetail uses **Spring Security + JWT** for authentication.

### Features

* User registration
* User login
* BCrypt password encryption
* JWT token generation
* JWT token validation
* Protected APIs
* Role-based authorization
* ADMIN and USER roles

### Authentication Flow

```text
User Login
    ↓
Email + Password
    ↓
Spring Security
    ↓
Password Verification
    ↓
JWT Token Generated
    ↓
Client Stores Token
    ↓
Authorization: Bearer <token>
    ↓
JwtFilter
    ↓
Protected API
```

---

# 2. User Management

Users can register and authenticate themselves.

User information includes:

```text
id
name
email
password
role
```

Passwords are stored using **BCrypt hashing** rather than plain text.

---

# 3. Product Management

Products contain information such as:

```text
Product ID
Name
Description
Price
Stock
Category
Average Rating
Review Count
```

Admin functionality includes product management.

---

# 4. Product Search

Users can search products by name.

Example:

```http
GET /products/search?name=iphone
```

Search is implemented using Spring Data JPA repository methods.

---

# 5. Product Filtering

NovaRetail supports product filtering based on price.

Example:

```http
GET /products/filter?minPrice=10000&maxPrice=50000
```

---

# 6. Product Sorting

Products can be sorted according to their price.

Examples:

```http
GET /products/sort?order=asc
```

```http
GET /products/sort?order=desc
```

---

# 7. Wishlist

Users can save products to their wishlist.

### Operations

```text
Add product
View wishlist
Remove product
```

The project also handles bidirectional JPA relationships carefully to prevent infinite JSON recursion.

---

# 8. Shopping Cart

Users can add products to their cart before placing an order.

Typical flow:

```text
Product
   ↓
Add to Cart
   ↓
CartItem
   ↓
Cart
   ↓
Checkout
```

Cart operations include:

* Add product
* Update quantity
* Remove product
* View cart

---

# 9. Inventory & Stock Management

NovaRetail maintains product stock and inventory history.

When an order is placed:

```text
Available Stock
       ↓
Stock Validation
       ↓
Order Quantity
       ↓
Stock Reduced
       ↓
Inventory History Created
```

Example:

```text
Stock = 20

Customer orders = 3

New Stock = 17
```

Inventory history records actions such as:

```text
RESTOCK
ORDER_PLACED
```

---

# 10. Coupon Management

NovaRetail supports discount coupons.

Example coupon:

```text
Code: WELCOME10
Discount: 10%
Active: true
Expiry: 2026-12-31
```

Coupon validation checks:

* Coupon exists
* Coupon is active
* Coupon has not expired

Example:

```http
POST /orders/place/{userId}/{addressId}?couponCode=WELCOME10
```

---

# 11. Order Management

Orders are created from the user's cart.

### Order Flow

```text
Cart
 ↓
Stock Check
 ↓
Inventory Update
 ↓
Coupon Validation
 ↓
Calculate Total
 ↓
Create Order
 ↓
Clear Cart
```

Order contains:

```text
Order ID
User
Order Date
Total Amount
Order Status
Order Items
Shipping Address
```

---

# 12. Order Status System

Orders use an enum-based status system.

```java
PENDING
PAID
CONFIRMED
SHIPPED
DELIVERED
CANCELLED
REFUNDED
```

Example workflow:

```text
PENDING
   ↓
PAID
   ↓
CONFIRMED
   ↓
SHIPPED
   ↓
DELIVERED
```

Cancellation and refund states are also supported.

---

# 13. Payment Integration

NovaRetail integrates **Razorpay Test Mode** for payment processing.

### Payment Flow

```text
Order Created
      ↓
Create Razorpay Order
      ↓
Payment Record Created
      ↓
PENDING
      ↓
Customer Completes Payment
      ↓
Payment Verification
      ↓
SUCCESS
      ↓
Order = PAID
```

Payment stores information such as:

```text
Payment ID
Razorpay Order ID
Razorpay Payment ID
Razorpay Signature
Amount
Payment Status
Payment Date
Order
```

> Razorpay credentials must never be committed to GitHub.

---

# 14. Payment Verification

Payment verification receives:

```json
{
  "razorpayOrderId": "order_xxxxx",
  "razorpayPaymentId": "pay_xxxxx",
  "razorpaySignature": "signature_xxxxx"
}
```

The backend identifies the payment using the Razorpay order ID and updates the payment and order status.

---

# 15. Reviews & Ratings

Users can submit reviews for products.

Review information includes:

```text
User
Product
Rating
Comment
Review Date
```

Supported functionality:

* Add review
* Get product reviews
* Get reviews by user
* Update review
* Delete review
* Calculate average rating

Product information can also maintain:

```text
Average Rating
Review Count
```

---

# 16. Shipping Address

Users can store multiple shipping addresses.

Address contains:

```text
Address ID
Address Line
City
State
Pincode
User
```

Example:

```json
{
  "addressLine": "No 18, Anna Nagar",
  "city": "Chennai",
  "state": "Tamil Nadu",
  "pincode": "600040"
}
```

Supported operations:

```text
Add Address
Get User Addresses
Update Address
Delete Address
```

---

# 17. Shipping Address Integration with Orders

An order stores the shipping address selected during checkout.

```text
User
 ↓
Saved Addresses
 ↓
Select Address
 ↓
Place Order
 ↓
Order
 ↓
Shipping Address
```

This means the order retains the address associated with the shipment.

---

# 18. Order Tracking

Order tracking is based on the order status.

Example:

```text
PENDING
   ↓
PAID
   ↓
CONFIRMED
   ↓
SHIPPED
   ↓
DELIVERED
```

The current order status can be retrieved using the order API.

---

# 19. Dashboard Analytics

NovaRetail includes dashboard APIs for basic business analytics.

### Dashboard Metrics

```text
Total Users
Total Products
Total Orders
Total Revenue
```

Example:

```http
GET /dashboard
```

Response:

```json
{
  "totalUsers": 12,
  "totalProducts": 10,
  "totalOrders": 8,
  "totalRevenue": 1961978.2
}
```

---

## Orders By Status

```http
GET /dashboard/orders/status
```

Example:

```json
[
  ["DELIVERED", 5],
  ["SHIPPED", 2],
  ["PENDING", 3]
]
```

---

## Low Stock Products

```http
GET /dashboard/low-stock
```

Example:

```json
[
  {
    "id": 1,
    "name": "iPhone 16",
    "stock": 5
  }
]
```

---

## Top Rated Products

```http
GET /dashboard/top-rated
```

Example:

```json
[
  [1, 4.9],
  [3, 4.8],
  [2, 4.7]
]
```

---

## Monthly Revenue

```http
GET /dashboard/monthly-revenue
```

Example:

```json
[
  [1, 50000],
  [2, 70000],
  [3, 120000]
]
```

---

# 🏗️ Architecture

NovaRetail follows a layered architecture.

```text
                    Client
                 Postman / React
                       |
                       ↓
                REST Controller
                       |
                       ↓
                   Service
                       |
                       ↓
                 Repository
                       |
                       ↓
                Spring Data JPA
                       |
                       ↓
                   Hibernate
                       |
                       ↓
                    MySQL
```

---

# 📁 Project Structure

```text
src
└── main
    ├── java
    │   └── com.example.demo
    │
    │       ├── config
    │       │   └── SecurityConfig.java
    │       │
    │       ├── controller
    │       │   ├── AuthController.java
    │       │   ├── ProductController.java
    │       │   ├── CategoryController.java
    │       │   ├── CartController.java
    │       │   ├── WishlistController.java
    │       │   ├── OrderController.java
    │       │   ├── PaymentController.java
    │       │   ├── ReviewController.java
    │       │   ├── AddressController.java
    │       │   └── DashboardController.java
    │       │
    │       ├── dto
    │       │   ├── ProductResponseDTO.java
    │       │   ├── OrderResponseDTO.java
    │       │   ├── PaymentVerificationRequest.java
    │       │   ├── ReviewRequestDTO.java
    │       │   ├── ReviewResponseDTO.java
    │       │   └── AddressDTO.java
    │       │
    │       ├── exception
    │       │   └── UserNotFoundException.java
    │       │
    │       ├── model
    │       │   ├── User.java
    │       │   ├── Role.java
    │       │   ├── Profile.java
    │       │   ├── Address.java
    │       │   ├── Category.java
    │       │   ├── Product.java
    │       │   ├── Wishlist.java
    │       │   ├── Cart.java
    │       │   ├── CartItem.java
    │       │   ├── Order.java
    │       │   ├── OrderItem.java
    │       │   ├── OrderStatus.java
    │       │   ├── Payment.java
    │       │   ├── Review.java
    │       │   ├── Coupon.java
    │       │   └── InventoryHistory.java
    │       │
    │       ├── repository
    │       │   ├── UserRepository.java
    │       │   ├── ProductRepository.java
    │       │   ├── CategoryRepository.java
    │       │   ├── WishlistRepository.java
    │       │   ├── CartRepository.java
    │       │   ├── OrderRepository.java
    │       │   ├── PaymentRepository.java
    │       │   ├── ReviewRepository.java
    │       │   ├── CouponRepository.java
    │       │   ├── AddressRepository.java
    │       │   └── InventoryHistoryRepository.java
    │       │
    │       ├── security
    │       │   ├── JwtUtil.java
    │       │   ├── JwtFilter.java
    │       │   └── CustomUserDetailsService.java
    │       │
    │       ├── service
    │       │   ├── AuthService.java
    │       │   ├── ProductService.java
    │       │   ├── CategoryService.java
    │       │   ├── WishlistService.java
    │       │   ├── CartService.java
    │       │   ├── OrderService.java
    │       │   ├── PaymentService.java
    │       │   ├── ReviewService.java
    │       │   ├── CouponService.java
    │       │   ├── AddressService.java
    │       │   └── DashboardService.java
    │       │
    │       └── DemoApplication.java
    │
    └── resources
        └── application.properties
```

---

# 🛠️ Technologies Used

| Technology      | Purpose                        |
| --------------- | ------------------------------ |
| Java            | Programming language           |
| Spring Boot     | Backend framework              |
| Spring MVC      | REST API development           |
| Spring Security | Authentication & authorization |
| JWT             | Stateless authentication       |
| Spring Data JPA | Database access                |
| Hibernate       | ORM                            |
| MySQL           | Relational database            |
| BCrypt          | Password hashing               |
| Razorpay        | Payment gateway                |
| Maven           | Dependency management          |
| Postman         | API testing                    |
| Git/GitHub      | Version control                |

---

# 🔐 Security

Security features implemented include:

* JWT authentication
* BCrypt password hashing
* Stateless Spring Security sessions
* Protected REST endpoints
* Role-based access
* Authorization header validation

Example:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# 🗄️ Database

The project uses MySQL.

Main tables include:

```text
user
role
user_role
profile
address
category
product
wishlist
cart
cart_item
orders
order_item
payment
review
coupon
inventory_history
```

---

# 🔄 Complete E-Commerce Flow

```text
                    REGISTER
                       ↓
                     LOGIN
                       ↓
                  JWT TOKEN
                       ↓
              ┌────────┴────────┐
              ↓                 ↓
          Browse Products     Profile
              ↓
      Search / Filter / Sort
              ↓
       ┌──────┴──────┐
       ↓             ↓
    Wishlist       Cart
                     ↓
              Select Address
                     ↓
               Apply Coupon
                     ↓
              Place Order
                     ↓
              Check Stock
                     ↓
            Reduce Inventory
                     ↓
          Create Inventory Log
                     ↓
               Create Order
                     ↓
             Create Payment
                     ↓
            Razorpay Payment
                     ↓
             Verify Payment
                     ↓
                  PAID
                     ↓
                CONFIRMED
                     ↓
                 SHIPPED
                     ↓
                DELIVERED
                     ↓
              Review Product
```

---

# 🧪 API Testing

APIs were tested using **Postman**.

Testing included:

* Successful requests
* Invalid requests
* Missing authentication
* Expired JWT
* Invalid JWT
* Invalid product IDs
* Invalid user IDs
* Empty cart
* Insufficient stock
* Invalid coupons
* Expired coupons
* Duplicate wishlist items
* Payment verification failures
* Invalid order status
* Invalid addresses
* Review validation

---

# 🐛 Important Bugs & Lessons Learned

During development, several real-world backend issues were encountered and resolved.

## 1. Infinite JSON Recursion

Bidirectional JPA relationships caused responses such as:

```text
User
 ↓
Order
 ↓
User
 ↓
Order
 ↓
User
...
```

This resulted in:

```text
Document nesting depth exceeds maximum allowed
```

### Solution

Used Jackson annotations such as:

```java
@JsonIgnore
@JsonManagedReference
@JsonBackReference
```

DTOs were also used where appropriate.

---

## 2. JWT Token Expired

The application initially returned:

```text
JWT Token Expired
401 Unauthorized
```

### Solution

The JWT filter and token validation logic were corrected so that valid tokens are accepted and expired tokens are rejected properly.

---

## 3. Order Enum Mapping Error

An error occurred because the database contained:

```text
PAID
```

while the Java enum did not contain the corresponding value.

### Solution

The `OrderStatus` enum was synchronized with the values stored in the database.

```java
PENDING
PAID
CONFIRMED
SHIPPED
DELIVERED
CANCELLED
REFUNDED
```

---

## 4. Payment Not Found

Payment verification initially failed when the Razorpay order ID sent by the client did not match the ID stored in the database.

### Lesson

Payment verification must use the correct Razorpay order ID and payment identifiers.

---

## 5. Stock Validation

Orders cannot be placed when requested quantity exceeds available stock.

Example:

```text
Stock = 3
Requested = 5

Result:
Insufficient stock
```

---

## 6. Address Ownership

A user should not be able to place an order using another user's shipping address.

The backend validates:

```text
Address.user.id == Order.user.id
```

---

# 📊 Core Business Rules

NovaRetail implements several important business rules.

### Order

```text
Cart cannot be empty
Stock must be sufficient
Product stock is reduced
Inventory history is recorded
```

### Coupon

```text
Coupon must exist
Coupon must be active
Coupon must not be expired
```

### Payment

```text
Payment belongs to an order
Payment starts as PENDING
Successful verification changes payment to SUCCESS
Successful payment changes order to PAID
```

### Review

```text
Review belongs to a user
Review belongs to a product
Rating contributes to average rating
```

### Address

```text
Address belongs to a user
Order uses a selected shipping address
```

---

# ⚙️ Setup & Installation

## Prerequisites

Install:

* Java 21
* Maven
* MySQL
* Postman
* Git

---

## 1. Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/novaretail.git
```

```bash
cd novaretail
```

---

## 2. Create MySQL Database

Open MySQL:

```sql
CREATE DATABASE novaretail;
```

---

## 3. Configure Database

Update:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/novaretail
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.jpa.open-in-view=false
```

---

# 💳 Razorpay Configuration

Configure Razorpay Test Mode credentials.

```properties
razorpay.key.id=YOUR_RAZORPAY_KEY
razorpay.key.secret=YOUR_RAZORPAY_SECRET
```

**Never commit real Razorpay credentials to GitHub.**

Use environment variables or a local configuration file that is excluded from Git.

---

# ▶️ Running the Application

Using Maven:

```bash
mvn spring-boot:run
```

Or run:

```text
DemoApplication.java
```

The application runs by default on:

```text
http://localhost:8080
```

---

# 🔑 Authentication Example

### Login

```http
POST /auth/login
```

Example:

```json
{
  "email": "user@gmail.com",
  "password": "password"
}
```

The response provides a JWT token.

For protected APIs:

```http
Authorization: Bearer YOUR_TOKEN
```

---

# 📌 Important API Examples

## Authentication

```text
POST /auth/register
POST /auth/login
```

## Products

```text
GET /products
GET /products/{id}
POST /products
PUT /products/{id}
DELETE /products/{id}
```

## Search / Filtering

```text
GET /products/search
GET /products/filter
GET /products/sort
```

## Wishlist

```text
POST /wishlist
GET /wishlist/{userId}
DELETE /wishlist/{id}
```

## Cart

```text
POST /cart/add
GET /cart/{userId}
PUT /cart/update
DELETE /cart/remove
```

## Orders

```text
POST /orders/place/{userId}/{addressId}
GET /orders/user/{userId}
GET /orders/{orderId}
PUT /orders/{orderId}/status
```

## Payments

```text
POST /payments/create/{orderId}
GET /payments/{orderId}
POST /payments/verify
```

## Reviews

```text
POST /reviews
GET /reviews/product/{productId}
GET /reviews/rating/{productId}
PUT /reviews/{reviewId}
DELETE /reviews/{reviewId}
```

## Addresses

```text
POST /address/{userId}
GET /address/{userId}
PUT /address/{addressId}
DELETE /address/{addressId}
```

## Dashboard

```text
GET /dashboard
GET /dashboard/orders/status
GET /dashboard/low-stock
GET /dashboard/top-rated
GET /dashboard/monthly-revenue
```

---

# 📈 Future Improvements

The following features can be added in future versions:

* React frontend
* Admin dashboard UI
* Refresh token mechanism
* Email notifications
* Order confirmation emails
* Invoice generation
* PDF invoices
* Redis caching
* Advanced product recommendations
* Pagination for product APIs
* Pagination for reviews
* Advanced reporting
* Automated unit tests
* Integration tests
* Docker
* CI/CD
* Cloud deployment
* AWS deployment
* Image/file upload
* Product image management
* Delivery partner integration

---

# 🎯 Learning Outcomes

Through NovaRetail, the following concepts were practiced:

* REST API development
* Spring Boot
* Spring MVC
* Spring Security
* JWT authentication
* BCrypt password hashing
* Role-based authorization
* Spring Data JPA
* Hibernate ORM
* Entity relationships
* DTO design
* Repository queries
* Business logic
* Database design
* Inventory management
* Payment gateway integration
* Exception handling
* JSON serialization
* API testing
* Debugging
* Git/GitHub workflow

---

# 🧠 Project Highlights

The most important part of NovaRetail is that it goes beyond basic CRUD.

The project implements real business workflows:

```text
Authentication
      +
Authorization
      +
Product Management
      +
Cart
      +
Inventory
      +
Coupon
      +
Order
      +
Payment
      +
Shipping
      +
Reviews
      +
Analytics
```

This makes NovaRetail a practical backend project for demonstrating **Spring Boot and backend development skills**.

---

# 👨‍💻 Author

**Megathilagavathy S**

GitHub:

```text
https://github.com/MegathilagavathyS
```

---

# 📄 License

This project is intended for learning and portfolio purposes.

You may modify and extend it for educational and personal projects.

---

## ⭐ If You Like This Project

If NovaRetail helped demonstrate your learning journey, consider giving the repository a ⭐ on GitHub.

---

# 📌 Project Status

```text
Backend API       ✅
Authentication    ✅
JWT Security      ✅
Products          ✅
Search            ✅
Filtering         ✅
Sorting            ✅
Wishlist          ✅
Cart              ✅
Inventory         ✅
Coupons           ✅
Orders            ✅
Payments          ✅
Reviews           ✅
Ratings           ✅
Addresses         ✅
Order Tracking    ✅
Analytics         ✅
React Frontend    🔜
```
