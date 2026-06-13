# Role-Based Access Control (RBAC)

## What is RBAC?

RBAC (Role-Based Access Control) is a security mechanism that restricts access to APIs based on user roles.

Example:

* ADMIN
* USER

Admins can create, update, and delete products.

Users can only view products and place orders.

---

# Flow

## Register

POST /auth/register

```json
{
  "name": "Admin",
  "email": "admin@gmail.com",
  "password": "admin123",
  "role": "ADMIN"
}
```

Password is encrypted using BCrypt before saving.

---

## Login

POST /auth/login

```json
{
  "email": "admin@gmail.com",
  "password": "admin123"
}
```

Response:

```json
{
  "token":"jwt-token"
}
```

---

## JWT Authentication

User sends token:

```http
Authorization: Bearer jwt-token
```

JwtFilter validates the token and loads user details.

---

## UserDetailsService

CustomUserDetailsService loads user information from database.

```java
return User.builder()
        .username(user.getEmail())
        .password(user.getPassword())
        .authorities("ROLE_" + user.getRole())
        .build();
```

---

## Role Mapping

Database:

```text
ADMIN
USER
```

Spring Security automatically converts:

```text
ROLE_ADMIN
ROLE_USER
```

---

## Controller Security

### ADMIN Only

```java
@PreAuthorize("hasRole('ADMIN')")
@PostMapping
```

```java
@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/{id}")
```

### USER + ADMIN

```java
@GetMapping
```

Both authenticated users can access.

---

## SecurityConfig

```java
@EnableMethodSecurity
```

Enables:

```java
@PreAuthorize
```

annotations.

---

## Testing

### Login as USER

Create Product

Expected:

```http
403 Forbidden
```

---

### Login as ADMIN

Create Product

Expected:

```http
200 OK
```

---

### Without Token

Expected:

```http
401 Unauthorized
```

---

## Common Errors

### Invalid Credentials

Cause:

Wrong password or password not encrypted.

---

### 403 Forbidden

Cause:

Authenticated user lacks required role.

---

### 401 Unauthorized

Cause:

Missing or invalid JWT token.

---

## Concepts Learned

* Spring Security
* BCrypt Password Encoding
* JWT Authentication
* JWT Filter
* UserDetailsService
* AuthenticationManager
* Role-Based Access Control
* @PreAuthorize
* Stateless Authentication
* Authorization vs Authentication
