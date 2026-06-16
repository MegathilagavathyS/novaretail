# 🛠️ Authentication & Authorization Debugging Notes

## 🚨 Problem 1: 403 Forbidden Error

### 📋 Symptoms
* **Successful Login:** `POST /auth/login` successfully generated a JWT token.
* **Failed Requests:** Both `GET /users` and `POST /users` returned a `403 Forbidden` error.

### 🔍 Root Cause
Spring Security assigned **`ROLE_ADMIN`** to authenticated users. However, the controller used `@PreAuthorize("hasAuthority('ADMIN')")`. Spring compared `ROLE_ADMIN` directly against `ADMIN`, causing a mismatch and failing authorization.

### ❌ Incorrect Code
```java
@PreAuthorize("hasAuthority('ADMIN')")
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
```

### 🔹 Correct Code
```java
@PreAuthorize("hasRole('ADMIN')")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
```
> **Note:** Spring automatically appends the `ROLE_` prefix when evaluating `hasRole()`, successfully matching `ROLE_ADMIN`.

---

## 🚨 Problem 2: Debug Endpoint Overlap

### 📋 Symptoms
* **Request:** `GET /users/debug`
* **Error:** `MethodArgumentTypeMismatchException: For input string: "debug"`

### 🔍 Root Cause
Spring MVC evaluated the dynamic path variable route `@GetMapping("/{id}")` before the explicit `@GetMapping("/debug")` route. It tried to parse the string `"debug"` into an Integer `id`.

### 🔹 Fix
Reorder the methods in the controller so explicit routes come first, or change the endpoint path completely:
```java
@GetMapping("/debug-role")
```

---

## 🛡️ Verification & Success Criteria
A final test of `GET /debug` returned the expected payload:

```json
[
  {
    "authority": "ROLE_ADMIN"
  }
]
```

### ✅ Validated Components
* JWT Filter pipeline functioning
* JWT Authentication processing
* `CustomUserDetailsService` loading context
* Role mapping working as intended
