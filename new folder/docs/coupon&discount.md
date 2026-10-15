# 🎟️ Coupon & Discount Module

## 📌 Overview
The Coupon module allows users to apply discount codes dynamically during checkout. It replicates real-world e-commerce promotions like flash sales, first-order offers, and holiday discounts, injecting robust financial validation logic right into your core order checkout sequence.

---

## 💾 Database Architecture

### `coupon` Table Schema

| 🔑 Column | 🏗️ Type | 📝 Description |
| :--- | :--- | :--- |
| `id` | **INT** (PK, AI) | Unique identifier for each coupon record. |
| `code` | **VARCHAR(50)** (Unique) | Unique alphanumeric coupon code string (e.g., `WELCOME10`). |
| `discount_percentage` | **DOUBLE** | The percent cut subtracted from the order total. |
| `expiry_date` | **DATETIME** | The date and time when the offer becomes invalid. |
| `active` | **BOOLEAN** | Master toggle used to manually activate or disable a code. |

### 🗄️ Initial Seed Data
```sql
INSERT INTO coupon (code, discount_percentage, expiry_date, active)
VALUES ('WELCOME10', 10.0, '2026-12-31 23:59:59', true);
```

---

## 🔄 Business Execution Flow

```text
       🛒 User Places Order
                 │
                 ▼
     🧮 Cart Items Aggregated
                 │
                 ▼
     💰 Base Total Cost Computed
                 │
                 ▼
     🎟️ Is a Coupon Code Provided?
                / \
               /   \
         YES  /     \  NO
             /       \
            ▼         ▼
  🔍 Run Rule Validation      📦 Proceed with Base Total
            │                         │
    Valid?  ├──[NO]──┐                │
            │        │                │
          [YES]      ▼                │
            │   🚫 Throw Error        │
            ▼                         │
  📉 Deduct Discount Value            │
            │                         │
            ▼                         ▼
      🧾 Generate Final Order Record & Save to DB
```

---

## 📡 API Specifications

### 🛠️ Create Coupon (Admin Only)
* **Endpoint:** `POST /coupons`
* **Access Control:** `hasRole('ADMIN')`
* **Payload Type:** `application/json`

#### Request Body
```json
{
  "code": "WELCOME10",
  "discountPercentage": 10.0,
  "active": true,
  "expiryDate": "2026-12-31T23:59:59"
}
```

#### Response Body (`201 Created`)
```json
{
  "id": 1,
  "code": "WELCOME10",
  "discountPercentage": 10.0,
  "active": true,
  "expiryDate": "2026-12-31T23:59:59"
}
```

### 📋 Get All Coupons
* **Endpoint:** `GET /coupons`
* **Access Control:** Public / Authorized User
* **Response Body (`200 OK`)**
```json
[
  {
    "id": 1,
    "code": "WELCOME10",
    "discountPercentage": 10.0,
    "active": true,
    "expiryDate": "2026-12-31T23:59:59"
  }
]
```

### 🛍️ Apply Coupon During Order Checkout
* **Endpoint:** `POST /orders/{userId}?couponCode=WELCOME10`
* **Access Control:** `hasRole('USER')`
* **Response Body (`201 Created`)**
```json
{
  "orderId": 502,
  "userId": 11,
  "baseAmount": 1500.00,
  "discountAmount": 150.00,
  "finalAmount": 1350.00,
  "status": "PENDING"
}
```

---

## ⚙️ Core Engineering Rules

### 🛡️ Validation Constraints
A coupon code is structurally executed only if it meets these strict database evaluations:
1. **Existence Verification**: The code must match an active string inside the `coupon` table.
2. **Status Check**: The record field status must evaluate to `active = true`.
3. **Temporal Bounds**: The runtime evaluation timestamp must be prior to the recorded `expiry_date`.

### 🧮 Mathematical Calculation Formula
To eliminate round-off inaccuracies, item pricing calculations are evaluated line-by-line prior to applying the percentage cut:

$$\text{Total Amount} = \sum (\text{Price} \times \text{Quantity})$$

$$\text{Discount Cut} = \text{Total Amount} \times \left( \frac{\text{Discount Percentage}}{100} \right)$$

$$\text{Final Checkout Payable} = \text{Total Amount} - \text{Discount Cut}$$

---

## 🎯 Exceptional Edge Cases Handled

| 🚨 Scenarios | ⚡ Exception Strategy | 💻 App Behavior |
| :--- | :--- | :--- |
| **Missing/Incorrect Code** | `ResourceNotFoundException` | Throws `"Coupon code not found."` |
| **Expired Promotion Date** | `IllegalArgumentException` | Throws `"Coupon code has expired."` |
| **Deactivated Promo Code** | `IllegalArgumentException` | Throws `"Coupon code is currently inactive."` |
| **Null Parameter Supplied** | *No Interception* | Bypasses discount logic and bills full total. |

---

## 🛠️ Resolved Regression Bugs

### ❌ Coupon Lookups Failing
* **Root Cause:** Missing abstract declaration mapping inside the JPA entity repository layer.
* **Resolution:** Implemented native structural schema interface query signature:
  ```java
  Optional<Coupon> findByCode(String code);
  ```

### ❌ Zero Discount Adjustment Faults
* **Root Cause:** Calculations processing sequentially inside isolated cart layers without hooking into the operational service loop.
* **Resolution:** Connected an explicit transaction validator checkpoint method call inside `OrderService` right before persisting the record:
  ```java
  order.applyCoupon(coupon);
  ```

### ❌ Incorrect Final Invoice Totals
* **Root Cause:** Applying percentage deductions to standalone single items instead of processing cumulative totals across multi-item quantity groupings.
* **Resolution:** Re-factored calculation array methods to safely aggregate total products by processing $(\text{price} \times \text{quantity})$ totals *before* evaluating fractional cuts.

---

## 🏆 Completed Milestones
* **Enterprise Validation Architecture**: Live validation rules intercept invalid code payloads before hitting payment states.
* **Transactional Service Integrity**: Integrated calculation flows right into checkout processes for automated data syncs.
* **Clean Relational Isolation**: Decoupled discount logic blocks into structured, dedicated backend tables.
