# ❤️ Wishlist Module Documentation

## 📌 Overview

The Wishlist module allows users to save products they are interested in purchasing later.

Instead of adding products directly to the cart, users can bookmark products inside their personal wishlist.

This feature is commonly found in:

* Amazon
* Flipkart
* Myntra
* eBay
* Alibaba

---

# 🎯 Learning Objectives

After implementing Wishlist, I learned:

* One-to-Many Relationships
* Many-to-One Relationships
* Foreign Keys
* Entity Mapping
* DTO Concepts
* JSON Serialization
* Circular Reference Problems
* Recursive JSON Issues
* REST API Design
* Spring Data JPA
* Hibernate Entity Relationships

---

# 🏗 Database Design

## wishlist

| Column     | Type |
| ---------- | ---- |
| id         | INT  |
| user_id    | INT  |
| product_id | INT  |

---

## ER Diagram

```text
User
 |
 | 1
 |
 *
Wishlist
 *
 |
 | 1
 |
Product
```

Meaning:

* One User can have many Wishlist items.
* One Product can exist in many users' Wishlists.

---

# 🔗 Entity Relationships

## Wishlist → User

```java
@ManyToOne
@JoinColumn(name = "user_id")
private User user;
```

Meaning:

Many wishlist records belong to one user.

---

## Wishlist → Product

```java
@ManyToOne
@JoinColumn(name = "product_id")
private Product product;
```

Meaning:

Many wishlist records can point to the same product.

---

# 📂 API Endpoints

---

## Add Product to Wishlist

### URL

```http
POST /wishlist/add
```

### Request

```json
{
  "userId": 1,
  "productId": 1
}
```

### Response

```json
{
  "id": 1,
  "product": {
    "id": 1,
    "name": "iPhone 16"
  }
}
```

---

## Get Wishlist By User

### URL

```http
GET /wishlist/1
```

### Response

```json
[
  {
    "id": 1,
    "product": {
      "id": 1,
      "name": "iPhone 16"
    }
  }
]
```

---

## Remove Wishlist Item

### URL

```http
DELETE /wishlist/1
```

### Response

```text
Wishlist item removed successfully
```

---

# 📊 Database Example

## User Table

| id | name |
| -- | ---- |
| 1  | Ravi |

---

## Product Table

| id | name      |
| -- | --------- |
| 1  | iPhone 16 |

---

## Wishlist Table

| id | user_id | product_id |
| -- | ------- | ---------- |
| 1  | 1       | 1          |

---

# 🚨 Major Bug Faced

## Infinite Recursion Error

### Error

```text
HttpMessageNotWritableException

Document nesting depth (501)
exceeds maximum allowed (500)
```

---

## Why It Happened

Hibernate relationships were creating a loop.

Example:

```text
Wishlist
  ↓
Product
  ↓
Wishlist
  ↓
Product
  ↓
Wishlist
  ↓
...
```

Jackson kept converting objects into JSON forever.

---

## Visual Explanation

```text
Wishlist
 |
 +---- Product
          |
          +---- Wishlist
                    |
                    +---- Product
                               |
                               +---- Wishlist
```

Infinite loop.

---

# ✅ Solution Applied

Inside Wishlist.java

```java
@ManyToOne
@JoinColumn(name = "user_id")
@JsonIgnore
private User user;
```

---

Inside Product.java

```java
@JsonIgnore
private List<Wishlist> wishlists;
```

---

Result:

```text
Infinite recursion fixed
```

---

# 🚨 Bug #2

## Stack Overflow Style Serialization

Response became:

```json
{
  "wishlist": {
    "product": {
      "wishlist": {
        "product": {
          "wishlist": {}
        }
      }
    }
  }
}
```

Repeated forever.

---

## Solution

Used:

```java
@JsonIgnore
```

to break the cycle.

---

# 🚨 Bug #3

## Returning Entities Directly

Controller:

```java
@GetMapping("/{userId}")
public List<Wishlist> getWishlist(...)
```

Problem:

Entire object graph returned.

Example:

```text
Wishlist
User
Addresses
Orders
Profile
Roles
Products
Categories
```

Huge JSON payload.

---

## Better Solution

Use DTOs.

Example:

```java
WishlistResponseDTO
```

```json
{
  "wishlistId": 1,
  "productId": 1,
  "productName": "iPhone 16",
  "price": 89999
}
```

Much cleaner.

---

# 🔥 Important Concepts Learned

## Cascade Types

```java
cascade = CascadeType.ALL
```

Operations cascade from parent to child.

Example:

```text
Save Wishlist
      ↓
Save Related Entities
```

---

## orphanRemoval

```java
orphanRemoval = true
```

When child is removed from parent collection:

```text
Wishlist Item Removed
      ↓
Database Row Deleted
```

---

## Fetch Types

### LAZY

```java
fetch = FetchType.LAZY
```

Loads data only when needed.

---

### EAGER

```java
fetch = FetchType.EAGER
```

Loads immediately.

---

# 📚 Interview Questions

## Q1

Why use Wishlist?

Answer:

To allow users to save products for future purchases.

---

## Q2

What relationship exists between User and Wishlist?

Answer:

```text
One User
      ↓
Many Wishlist Items
```

One-to-Many.

---

## Q3

Why did infinite recursion occur?

Answer:

Bidirectional JPA mappings caused Jackson serialization loops.

---

## Q4

How was recursion fixed?

Answer:

Using:

```java
@JsonIgnore
```

and DTO responses.

---

## Q5

Why DTOs are preferred?

Answer:

* Smaller responses
* Better security
* No recursive JSON
* Cleaner API contracts

---

# ✅ Module Completion Checklist

* [x] Wishlist Entity
* [x] Wishlist Repository
* [x] Wishlist Service
* [x] Wishlist Controller
* [x] Add Product To Wishlist
* [x] Get Wishlist
* [x] Remove Wishlist Item
* [x] Hibernate Mapping
* [x] Foreign Keys
* [x] Infinite Recursion Fix
* [x] JSON Serialization Handling
* [x] DTO Understanding

---

# 🚀 Next Module

Product Search + Filtering + Sorting

Topics:

* Search by Name
* Search by Keyword
* Search by Price Range
* Sort by Price
* Sort by Name
* Spring Data Query Methods
* JPQL
* Custom Queries
* Pageable APIs

This module moves the project much closer to a production-level e-commerce backend.
