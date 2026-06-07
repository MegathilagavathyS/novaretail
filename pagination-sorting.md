# Spring Boot Pagination & Sorting

## Objective

Learn how to retrieve data in smaller chunks instead of returning all records at once.

Topics covered:

* Pagination
* Sorting
* Pageable
* Page
* PageRequest
* Sort
* Real-world API usage

---

# Problem Without Pagination

Current API:

```http
GET /users
```

Response:

```text
User1
User2
User3
...
User10000
```

Problem:

* Large response size
* Slow API
* High memory usage
* Poor performance

---

# Solution: Pagination

Instead of loading all users:

```http
GET /users/paged?page=0&size=5
```

Response:

```text
Only first 5 users
```

---

# Pagination Flow

```text
Client
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
MySQL
```

Repository returns only the requested page.

---

# Spring Components Used

## Page

Represents paginated results.

Example:

```java
Page<User> usersPage
```

Contains:

* Data
* Current page
* Total pages
* Total elements

---

## Pageable

Represents pagination information.

Example:

```java
Pageable pageable
```

Contains:

* page number
* page size
* sort details

---

## PageRequest

Used to create Pageable.

Example:

```java
PageRequest.of(page, size)
```

---

## Sort

Used for ordering results.

Example:

```java
Sort.by("name")
```

---

# Service Logic

Created:

```java
getUsersWithPagination(
    int page,
    int size,
    String sortBy
)
```

Flow:

```text
Page Number
      +
Page Size
      +
Sort Field
      ↓
PageRequest
      ↓
Repository
      ↓
Page<User>
      ↓
Page<UserResponseDTO>
```

---

# Controller Endpoint

Endpoint:

```http
GET /users/paged
```

Parameters:

```http
page
size
sortBy
```

Example:

```http
GET /users/paged?page=0&size=5&sortBy=name
```

---

# My Example

Database:

| ID | Name  |
| -- | ----- |
| 1  | Ravi  |
| 2  | Kumar |

Request:

```http
GET /users/paged?page=0&size=5&sortBy=name
```

Response:

```json
{
  "content": [
    {
      "id": 2,
      "name": "Kumar",
      "email": "kumar@gmail.com"
    },
    {
      "id": 1,
      "name": "Ravi",
      "email": "ravi@gmail.com"
    }
  ]
}
```

---

# My Doubt

Question:

Why does Kumar appear before Ravi?

Response:

```json
[
  {
    "name": "Kumar"
  },
  {
    "name": "Ravi"
  }
]
```

Reason:

Sorting was applied:

```java
Sort.by("name")
```

Alphabetical order:

```text
K
R
```

Therefore:

```text
Kumar
Ravi
```

is correct.

---

# Understanding Response Fields

Example:

```json
{
  "content": [...],
  "empty": false,
  "first": true,
  "last": true,
  "number": 0,
  "size": 5,
  "totalElements": 2,
  "totalPages": 1
}
```

---

## content

Actual data.

```json
[
  {
    "id": 1,
    "name": "Ravi"
  }
]
```

---

## totalElements

Total records in database.

Example:

```json
2
```

Means:

```text
Ravi
Kumar
```

---

## totalPages

Total pages available.

Example:

```json
1
```

Because:

```text
2 records
size = 5
```

Everything fits into one page.

---

## number

Current page number.

Example:

```json
0
```

Spring starts counting from:

```text
0
1
2
3
...
```

Not from 1.

---

## first

Indicates first page.

Example:

```json
true
```

---

## last

Indicates last page.

Example:

```json
true
```

Because only one page exists.

---

# Testing With More Users

Add:

```text
John
Arun
David
Priya
```

Total users:

```text
6
```

---

## Page 0

Request:

```http
GET /users/paged?page=0&size=2
```

Returns:

```text
Users 1-2
```

---

## Page 1

Request:

```http
GET /users/paged?page=1&size=2
```

Returns:

```text
Users 3-4
```

---

## Page 2

Request:

```http
GET /users/paged?page=2&size=2
```

Returns:

```text
Users 5-6
```

---

# Real-World Usage

Large applications rarely return all records.

Examples:

* Employees
* Orders
* Products
* Customers

Typical API:

```http
GET /users?page=0&size=20
```

---

# Current Project Features

Completed:

✅ CRUD

✅ MySQL Integration

✅ JPA Repository

✅ Validation

✅ DTO Pattern

✅ Global Exception Handling

✅ Custom Queries

✅ Pagination & Sorting

---

# Next Topic

Entity Relationships

Learn:

```java
@OneToMany
@ManyToOne
@OneToOne
@JoinColumn
```

Examples:

```text
Department → Employees

User → Addresses

Customer → Orders
```

This is the next major JPA concept before moving to Spring Security and JWT Authentication.

---

# Revision Checklist

* [ ] Understand Page
* [ ] Understand Pageable
* [ ] Understand PageRequest
* [ ] Understand Sort
* [ ] Create Pagination Endpoint
* [ ] Test page=0
* [ ] Test page=1
* [ ] Test sorting by name
* [ ] Understand content field
* [ ] Understand totalElements
* [ ] Understand totalPages
* [ ] Understand first and last flags

---

# Final Architecture

```text
Client
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Pageable
   ↓
Hibernate
   ↓
MySQL
```

Pagination and Sorting are foundational concepts used in almost every production Spring Boot application.
