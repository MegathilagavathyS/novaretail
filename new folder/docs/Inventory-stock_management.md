# 📦 Inventory & Stock Management

## Objective

Inventory Management ensures that products maintain correct stock quantities when:

* New products are added
* Products are restocked
* Orders are placed
* Stock becomes unavailable

---

# Database Design

## Product Table

| Column      | Type    |
| ----------- | ------- |
| id          | INT     |
| name        | VARCHAR |
| description | VARCHAR |
| price       | DOUBLE  |
| stock       | INT     |
| category_id | INT     |

Example:

| id | name        | stock |
| -- | ----------- | ----- |
| 1  | iPhone 16   | 50    |
| 2  | Samsung S25 | 40    |
| 3  | OnePlus 13  | 25    |

---

## Inventory History Table

Tracks every stock movement.

| Column           | Type     |
| ---------------- | -------- |
| id               | INT      |
| product_id       | INT      |
| quantity_changed | INT      |
| action           | VARCHAR  |
| created_at       | DATETIME |

Example:

| id | product_id | quantity_changed | action       |
| -- | ---------- | ---------------- | ------------ |
| 1  | 1          | 50               | RESTOCK      |
| 2  | 1          | -2               | ORDER_PLACED |
| 3  | 1          | 20               | RESTOCK      |

---

# Features Implemented

## Restock Product

Endpoint:

POST /inventory/restock

Example:

productId=1
quantity=50

Result:

Old Stock = 20

New Stock = 70

Inventory History Created:

RESTOCK

---

## Order Placement

When user places order:

Cart:

iPhone x 2

Stock Before:

50

Stock After:

48

Inventory History:

ORDER_PLACED

---

# Business Rules

## Rule 1

Stock can never become negative.

Invalid:

Stock = 5

Order Quantity = 10

Result:

Exception

"Insufficient stock"

---

## Rule 2

Restock quantity must be positive.

Invalid:

quantity=-10

Result:

Exception

"Quantity must be greater than 0"

---

## Rule 3

Product must exist.

Invalid:

productId=999

Result:

Exception

"Product not found"

---

# API Testing

## Restock Product

POST

/inventory/restock?productId=1&quantity=20

Expected:

200 OK

Stock increases.

---

## View Inventory History

GET

/inventory/history/1

Expected:

Inventory records returned.

---

# Bugs Fixed

## Bug 1

Infinite JSON Recursion

Error:

Document nesting depth (501)

Cause:

Product ↔ InventoryHistory

Fix:

@JsonIgnore

on Product relationship.

---

## Bug 2

Infinite Recursion Through Cart

Cause:

Product ↔ CartItem

Fix:

@JsonIgnore

on Product.cartItems

---

## Bug 3

Infinite Recursion Through Orders

Cause:

Product ↔ OrderItem

Fix:

@JsonIgnore

on Product.orderItems

---

# Concepts Learned

* OneToMany Mapping
* ManyToOne Mapping
* Cascade Operations
* Inventory Tracking
* Business Validation
* Order Processing
* Stock Management
* Preventing Negative Stock
* Inventory Audit Trail
* JSON Serialization Issues
* @JsonIgnore
* Bidirectional Relationships

---

# Real World Usage

Amazon

Flipkart

Myntra

All major e-commerce systems use inventory history tables to:

* Track stock changes
* Detect fraud
* Analyze sales
* Generate reports
* Forecast inventory requirements

---

# Project Progress

Completed Modules:

✅ Authentication

✅ JWT

✅ RBAC

✅ User Management

✅ Address Management

✅ Profile Management

✅ Category Management

✅ Product Management

✅ Product Search

✅ Product Filtering

✅ Product Sorting

✅ Cart Management

✅ Wishlist Management

✅ Order Management

✅ Inventory & Stock Management

Project Completion:

~80%
