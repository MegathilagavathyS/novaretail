# Reviews & Ratings Module

## Objective

Allow users to:

* Review purchased products
* Rate products
* Update reviews
* Delete reviews
* View reviews
* Calculate average product rating

---

# Database Design

## Review Table

| Column      | Type     |
| ----------- | -------- |
| id          | INT      |
| rating      | INT      |
| comment     | VARCHAR  |
| review_date | DATETIME |
| user_id     | FK       |
| product_id  | FK       |

---

# Entity Relationship

```text
User
  |
  | 1
  |
  | *
Review
  |
  | *
  |
  | 1
Product
```

One User → Many Reviews

One Product → Many Reviews

---

# API Endpoints

Base URL

```http
http://localhost:8080/reviews
```

---

# Add Review

Request

```http
POST /reviews
```

Body

```json
{
  "userId": 11,
  "productId": 1,
  "rating": 5,
  "comment": "Excellent product"
}
```

Response

```json
{
  "id": 1,
  "userId": 11,
  "productId": 1,
  "rating": 5,
  "comment": "Excellent product",
  "reviewDate": "2026-06-18T10:30:00"
}
```

---

# Get Product Reviews

Request

```http
GET /reviews/product/1
```

Response

```json
[
  {
    "id": 1,
    "rating": 5,
    "comment": "Excellent product"
  }
]
```

---

# Average Rating

Request

```http
GET /reviews/rating/1
```

Response

```json
4.6
```

---

# Update Review

Request

```http
PUT /reviews/1
```

Body

```json
{
  "rating": 4,
  "comment": "Good Product"
}
```

---

# Delete Review

Request

```http
DELETE /reviews/1
```

Response

```text
Review Deleted Successfully
```

---

# Validation Rules

Rating must be:

```text
1 - 5
```

Reject:

```json
{
  "rating": 0
}
```

Reject:

```json
{
  "rating": 10
}
```

---

# Business Rules

User cannot review non-existing product.

User cannot review non-existing user.

User should review only once per product.

Duplicate reviews should be rejected.

---

# Bugs Fixed During Development

## DTO Constructor Error

Error

```text
constructor ReviewResponseDTO cannot be applied
```

Cause:

DTO fields and constructor parameters mismatch.

Fix:

Updated constructor to include:

```java
id
userId
productId
rating
comment
reviewDate
```

---

## Missing Repository Method

Error

```text
cannot find symbol
findByUserIdAndProductId
```

Fix:

Added:

```java
Optional<Review>
findByUserIdAndProductId(
        Integer userId,
        Integer productId);
```

---

## LocalDateTime Error

Error

```text
String cannot be converted to LocalDateTime
```

Fix:

Review entity updated:

```java
private LocalDateTime reviewDate;
```

---

## Missing Setter Error

Error

```text
setReviewDate()
not found
```

Fix:

Added:

```java
public void setReviewDate(
        LocalDateTime reviewDate)
```

---

# Learning Outcomes

✔ Entity Relationships

✔ One-To-Many Mapping

✔ DTO Mapping

✔ Validation

✔ Aggregation Queries

✔ Average Rating Calculation

✔ CRUD Operations

✔ Production Review System
