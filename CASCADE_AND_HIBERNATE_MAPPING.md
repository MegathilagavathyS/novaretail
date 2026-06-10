# Cascade Types & Hibernate Relationship Mapping

## Overview

Hibernate/JPA provides relationship mapping annotations that allow Java objects to be connected and persisted automatically in a relational database.

In this project, we have learned:

* One-To-One Mapping
* One-To-Many Mapping
* Many-To-One Mapping
* Many-To-Many Mapping
* Cascade Types
* Lazy Loading
* Bidirectional Mapping
* Foreign Keys
* Join Tables

---

# 1. What is Hibernate Mapping?

Hibernate Mapping defines relationships between entities.

Example:

```text
User
 ├── Profile
 ├── Address
 └── Role
```

Database:

```text
user
profile
address
role
user_role
```

Hibernate automatically converts Java objects into database records.

---

# 2. One-To-One Mapping

## Definition

One record in Table A is associated with exactly one record in Table B.

Example:

```text
User ↔ Profile
```

### Database

```text
user
 └── id

profile
 ├── id
 └── user_id
```

### Entity Mapping

User.java

```java
@OneToOne(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
)
private Profile profile;
```

Profile.java

```java
@OneToOne
@JoinColumn(name = "user_id")
private User user;
```

### Use Cases

* User ↔ Profile
* Employee ↔ Passport
* Student ↔ Identity Card

---

# 3. One-To-Many Mapping

## Definition

One parent can have multiple children.

Example:

```text
User
 ├── Address 1
 ├── Address 2
 └── Address 3
```

### Database

```text
user
 └── id

address
 ├── id
 └── user_id
```

### Entity Mapping

User.java

```java
@OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
)
private List<Address> addresses;
```

Address.java

```java
@ManyToOne
@JoinColumn(name = "user_id")
private User user;
```

### Use Cases

* User → Addresses
* Department → Employees
* Customer → Orders

---

# 4. Many-To-One Mapping

## Definition

Many child records belong to one parent.

Example:

```text
Address
   ↓
 User
```

### Mapping

```java
@ManyToOne
@JoinColumn(name = "user_id")
private User user;
```

### Use Cases

* Order → Customer
* Employee → Department
* Address → User

---

# 5. Many-To-Many Mapping

## Definition

Many records in Table A can be related to many records in Table B.

Example:

```text
User ↔ Role
```

A User can have multiple Roles.

A Role can belong to multiple Users.

### Database

```text
user
role

user_role
```

### Join Table

```text
user_role

user_id
role_id
```

### Entity Mapping

User.java

```java
@ManyToMany(
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
)
@JoinTable(
    name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
)
private Set<Role> roles;
```

Role.java

```java
@ManyToMany(mappedBy = "roles")
private Set<User> users;
```

### Use Cases

* User ↔ Role
* Student ↔ Course
* Employee ↔ Project

---

# 6. Foreign Key

A foreign key creates a relationship between two tables.

Example:

```text
address.user_id
        ↓
user.id
```

### Example

```sql
CREATE TABLE address (
    id INT PRIMARY KEY,
    city VARCHAR(100),
    user_id INT,
    FOREIGN KEY(user_id)
        REFERENCES user(id)
);
```

---

# 7. Lazy Loading

## Definition

Related entities are loaded only when needed.

### Example

```java
@OneToMany(fetch = FetchType.LAZY)
private List<Address> addresses;
```

When User is fetched:

```text
SELECT * FROM user;
```

Addresses are NOT loaded.

Only when:

```java
user.getAddresses();
```

Hibernate executes:

```sql
SELECT * FROM address
WHERE user_id = ?;
```

### Benefits

* Better performance
* Reduced memory usage
* Faster API responses

---

# 8. Bidirectional Mapping

## Definition

Both entities know about each other.

Example:

```text
User ↔ Address
```

User.java

```java
@OneToMany(mappedBy = "user")
private List<Address> addresses;
```

Address.java

```java
@ManyToOne
private User user;
```

### Benefits

Navigation is possible from both sides.

```java
user.getAddresses();

address.getUser();
```

---

# 9. JSON Infinite Recursion Problem

Without handling:

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

Infinite loop occurs during JSON serialization.

### Solution

Parent Side

```java
@JsonManagedReference
```

Child Side

```java
@JsonBackReference
```

Example:

User.java

```java
@JsonManagedReference
private List<Address> addresses;
```

Address.java

```java
@JsonBackReference
private User user;
```

---

# 10. Cascade Types

## What is Cascade?

Cascade tells Hibernate what operations should automatically propagate from parent to child entities.

Example:

```text
User
 ├── Address 1
 └── Address 2
```

If User is saved:

```text
Should Address records also be saved?
```

Cascade decides this behavior.

---

# 11. CascadeType.PERSIST

### Purpose

Save child entities automatically.

```java
cascade = CascadeType.PERSIST
```

### Result

```text
save(User)
      ↓
save(Address)
```

---

# 12. CascadeType.MERGE

### Purpose

Update child entities automatically.

```java
cascade = CascadeType.MERGE
```

### Result

```text
update(User)
       ↓
update(Address)
```

---

# 13. CascadeType.REMOVE

### Purpose

Delete child entities automatically.

```java
cascade = CascadeType.REMOVE
```

### Result

```text
delete(User)
       ↓
delete(Address)
```

---

# 14. CascadeType.REFRESH

### Purpose

Reload entity state from database.

```java
cascade = CascadeType.REFRESH
```

---

# 15. CascadeType.DETACH

### Purpose

Detach entities from persistence context.

```java
cascade = CascadeType.DETACH
```

Advanced use case.

---

# 16. CascadeType.ALL

Most commonly used.

```java
cascade = CascadeType.ALL
```

Equivalent to:

```text
PERSIST
MERGE
REMOVE
REFRESH
DETACH
```

### Example

```java
@OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL
)
private List<Address> addresses;
```

When:

```java
userRepository.save(user);
```

Hibernate automatically saves:

```text
User
Address 1
Address 2
Address 3
```

---

# 17. orphanRemoval

### Purpose

Automatically delete child records when removed from parent.

Example:

```java
@OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
)
```

### Result

```text
User
 ├── Address 1
 └── Address 2
```

Remove Address 2:

```java
user.getAddresses().remove(address2);
```

Hibernate automatically executes:

```sql
DELETE FROM address
WHERE id = ?;
```

---

# Project Relationships Learned

```text
User
 │
 ├── Profile
 │      (One-To-One)
 │
 ├── Address
 │      (One-To-Many)
 │
 └── Role
        (Many-To-Many)
```

---

# Key Interview Questions

1. Difference between One-To-One and One-To-Many?
2. What is a Foreign Key?
3. What is Lazy Loading?
4. Difference between LAZY and EAGER Fetch?
5. What is CascadeType.ALL?
6. What is orphanRemoval?
7. What is Bidirectional Mapping?
8. Why use @JsonManagedReference and @JsonBackReference?
9. What is a Join Table?
10. How does Many-To-Many mapping work?

---

# Summary

Concepts Completed:

✅ One-To-One Mapping

✅ One-To-Many Mapping

✅ Many-To-One Mapping

✅ Many-To-Many Mapping

✅ Foreign Keys

✅ Lazy Loading

✅ Bidirectional Mapping

✅ Cascade Types

✅ orphanRemoval

✅ Join Tables

✅ Hibernate/JPA Relationship Mapping

These concepts form the core of Spring Boot + Hibernate database development and are heavily used in production applications.
