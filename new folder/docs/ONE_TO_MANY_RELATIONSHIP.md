# Spring Boot JPA Relationships - Learning Notes

## Objective

Learn how to establish relationships between entities using Spring Boot, Hibernate, JPA, and MySQL.

In this project:

```text
User
  ↓
Address
```

A User can have multiple Addresses.

---

# Concepts Covered

## 1. One-To-Many Relationship

### Definition

One parent record can have multiple child records.

Example:

```text
User
 ├── Address 1
 ├── Address 2
 └── Address 3
```

### JPA Annotation

```java
@OneToMany(mappedBy = "user")
private List<Address> addresses;
```

### Meaning

One User can own multiple Address records.

---

## 2. Many-To-One Relationship

### Definition

Many child records can belong to one parent record.

Example:

```text
Address 1
Address 2
Address 3
      ↓
     User
```

### JPA Annotation

```java
@ManyToOne
@JoinColumn(name = "user_id")
private User user;
```

### Meaning

Each Address belongs to a single User.

---

## 3. Foreign Key (user_id)

### What is a Foreign Key?

A foreign key creates a connection between two tables.

### Database Tables

#### user

```sql
+----+-----------------+-------+
| id | email           | name  |
+----+-----------------+-------+
| 1  | ravi@gmail.com  | Ravi  |
+----+-----------------+-------+
```

#### address

```sql
+----+---------+---------+------------+---------+
| id | city    | pincode | state      | user_id |
+----+---------+---------+------------+---------+
| 1  | Chennai | 600001  | Tamil Nadu | 1       |
+----+---------+---------+------------+---------+
```

### Relationship

```text
address.user_id
        ↓
     user.id
```

This links Address to User.

---

## 4. LAZY Loading

### Definition

Data is loaded only when required.

### Configuration

```java
@OneToMany(
    mappedBy = "user",
    fetch = FetchType.LAZY
)
```

### Behavior

When fetching User:

```java
userRepository.findById(1);
```

Only User data is loaded.

Addresses are fetched only when:

```java
user.getAddresses();
```

is called.

### Benefit

Improves application performance.

Avoids unnecessary database queries.

---

## 5. Bidirectional Mapping

### Definition

Both entities know about each other.

### User.java

```java
private List<Address> addresses;
```

### Address.java

```java
private User user;
```

### Visualization

```text
User
 ↓
Address

Address
 ↑
User
```

This allows navigation from:

```java
user.getAddresses();
```

and

```java
address.getUser();
```

---

## 6. JSON Infinite Recursion Problem

### Problem

When converting entities to JSON:

```text
User
 ↓
Address
 ↓
User
 ↓
Address
 ↓
User
 ...
```

Serialization never stops.

### Example

```json
{
  "id":1,
  "addresses":[
    {
      "user":{
        "addresses":[
          {
            "user":{
              ...
```

Application may fail or return endless JSON.

---

## Fix Using Jackson

### User.java

```java
@JsonManagedReference
private List<Address> addresses;
```

### Address.java

```java
@JsonBackReference
private User user;
```

### Result

Serialization becomes:

```json
{
  "id":1,
  "name":"Ravi",
  "addresses":[
    {
      "id":1,
      "city":"Chennai"
    }
  ]
}
```

No recursion.

---

# Database Structure

```text
spring_demo
│
├── user
│     ├── id
│     ├── name
│     └── email
│
└── address
      ├── id
      ├── city
      ├── state
      ├── pincode
      └── user_id
```

---

# APIs Tested

## Add User

```http
POST /users
```

## Get All Users

```http
GET /users
```

## Add Address

```http
POST /users/{userId}/address
```

Example:

```json
{
  "city":"Chennai",
  "state":"Tamil Nadu",
  "pincode":"600001"
}
```

## Get User Addresses

```http
GET /users/{userId}/addresses
```

Example:

```json
[
  {
    "id":1,
    "city":"Chennai",
    "state":"Tamil Nadu",
    "pincode":"600001"
  }
]
```

---

# Key Interview Questions

### What is @OneToMany?

One parent entity can have multiple child entities.

### What is @ManyToOne?

Many child entities belong to one parent entity.

### What is a Foreign Key?

A column that links one table to another.

### Difference between LAZY and EAGER?

LAZY:

* Loads data only when needed.

EAGER:

* Loads data immediately.

### What causes infinite recursion?

Bidirectional entity relationships during JSON serialization.

### How to fix infinite recursion?

Use:

```java
@JsonManagedReference
@JsonBackReference
```

or

```java
@JsonIgnore
```

---

# Learning Progress

Completed:

✅ REST API

✅ Controller Layer

✅ Service Layer

✅ Repository Layer

✅ MySQL Integration

✅ DTO Pattern

✅ Validation

✅ Global Exception Handling

✅ Custom Queries

✅ Pagination & Sorting

✅ One-To-Many Relationship

✅ Many-To-One Relationship

✅ Foreign Key Mapping

✅ LAZY Loading

✅ Bidirectional Mapping

✅ JSON Infinite Recursion Fix

---

# Next Topics

1. Cascade Types
2. One-To-One Mapping
3. Many-To-Many Mapping
4. Spring Security
5. JWT Authentication
6. Swagger/OpenAPI
7. Unit Testing
8. Integration Testing
9. Docker
10. Deployment
