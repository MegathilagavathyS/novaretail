# Payment Verification Module (Razorpay)

## Objective

Verify a payment after the user completes payment through Razorpay.

---

# Flow

```text
User
 ↓
Place Order
 ↓
Create Razorpay Order
 ↓
User Pays
 ↓
Razorpay Returns

razorpay_order_id
razorpay_payment_id
razorpay_signature

 ↓

POST /payments/verify

 ↓

Payment Status = SUCCESS
Order Status = PAID
```

---

# Database Changes

## Payment Table

```sql
payment
```

Columns:

```text
id
razorpay_order_id
razorpay_payment_id
razorpay_signature
payment_status
amount
order_id
payment_date
```

---

# Payment Creation

Endpoint:

```http
POST /payments/create/{orderId}
```

Example:

```http
POST /payments/create/5
```

Response:

```json
{
  "id":"order_T335JOHKP7ffUu",
  "amount":17999800,
  "currency":"INR"
}
```

Database:

```text
payment_status = PENDING
```

---

# Payment Verification

Endpoint:

```http
POST /payments/verify
```

Request:

```json
{
  "razorpayOrderId":"order_T335JOHKP7ffUu",
  "razorpayPaymentId":"pay_ABC123",
  "razorpaySignature":"xyz123"
}
```

Service Actions:

```text
Find Payment
 ↓
Save Payment Id
 ↓
Save Signature
 ↓
Mark Payment SUCCESS
 ↓
Mark Order PAID
```

---

# Database Result

Before:

```text
payment_status = PENDING
order.status = PENDING
```

After:

```text
payment_status = SUCCESS
order.status = PAID
```

---

# Repository Methods

```java
findByOrderId()

findByRazorpayOrderId()
```

---

# Common Bugs Faced

## Bug #1

Error:

```text
Payment not found
```

Reason:

```text
Wrong razorpay_order_id sent
```

Fix:

```text
Use exact value stored in payment table
```

---

## Bug #2

Error:

```text
Cannot resolve paymentService
```

Reason:

```java
return paymentService.verifyPayment(...)
```

inside controller

Variable doesn't exist.

````

Fix:

```java
return service.verifyPayment(request);
````

---

## Bug #3

Error:

```text
JWT Token Expired
```

Reason:

Old token used.

Fix:

```text
Login again
Copy fresh token
Use new Bearer Token
```

---

## Bug #4

Error:

```text
403 Forbidden
```

Reason:

ROLE mismatch.

```java
hasRole("ADMIN")
```

vs

```java
ROLE_ADMIN
```

Fix:

```java
hasRole("ADMIN")
```

and

```java
.authorities("ROLE_" + user.getRole())
```

---

# Edge Test Cases

## Valid Payment

Expected:

```text
SUCCESS
```

---

## Invalid Razorpay Order Id

Expected:

```text
Payment not found
```

---

## Invalid Payment Id

Expected:

```text
Verification Failed
```

---

## Empty Signature

Expected:

```text
400 Bad Request
```

---

## Verify Same Payment Twice

Expected:

```text
Already Paid
```

(optional enhancement)

---

## Non Existing Order

Expected:

```text
Order not found
```

---

# Learning Outcome

You learned:

* Razorpay Integration
* Payment Table Design
* Order ↔ Payment Mapping
* Payment Verification Flow
* Updating Order Status
* Payment Lifecycle
* Transaction Tracking
* Real-world E-Commerce Payments

````

Architecture:

```text
Order
 ↓
Payment Created
 ↓
PENDING
 ↓
Payment Verification
 ↓
SUCCESS
 ↓
Order PAID
````
