# Order Status Workflow Module

## Objective

Implement a complete order lifecycle system for the E-Commerce application.

The Order Status Workflow tracks an order from placement to delivery and allows administrators to manage order progress.

---

# Business Flow

```text
Add To Cart
      ↓
Place Order
      ↓
PENDING
      ↓
Payment Verification
      ↓
PAYMENT_SUCCESS
      ↓
PROCESSING
      ↓
PACKED
      ↓
SHIPPED
      ↓
OUT_FOR_DELIVERY
      ↓
DELIVERED
```

Optional states:

```text
CANCELLED
RETURNED
```

---

# Why Order Status Workflow?

Without status tracking:

```text
Order
 ↓
Completed
```

Not realistic.

Real e-commerce systems need:

* Order Tracking
* Shipping Updates
* Delivery Status
* Cancellation Support
* Return Support

---

# OrderStatus Enum

File:

```text
model/OrderStatus.java
```

```java
public enum OrderStatus {

    PENDING,

    PAYMENT_SUCCESS,

    PROCESSING,

    PACKED,

    SHIPPED,

    OUT_FOR_DELIVERY,

    DELIVERED,

    CANCELLED,

    RETURNED
}
```

---

# Order Entity Changes

Before:

```java
private String status;
```

After:

```java
@Enumerated(EnumType.STRING)
private OrderStatus status;
```

Purpose:

Stores values like:

```text
PENDING
SHIPPED
DELIVERED
```

instead of numbers.

---

# Automatic Status Updates

## When Order is Created

Inside OrderService:

```java
order.setStatus(
        OrderStatus.PENDING);
```

Database:

```text
PENDING
```

---

## When Payment is Verified

Inside PaymentService:

```java
order.setStatus(
        OrderStatus.PAYMENT_SUCCESS);
```

Database:

```text
PAYMENT_SUCCESS
```

---

# Admin Status Management

Admin can move orders through the workflow.

Example:

```text
PAYMENT_SUCCESS
      ↓
PROCESSING
      ↓
PACKED
      ↓
SHIPPED
      ↓
OUT_FOR_DELIVERY
      ↓
DELIVERED
```

---

# Order Status Update DTO

File:

```text
dto/OrderStatusRequestDTO.java
```

```java
public class OrderStatusRequestDTO {

    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(
            OrderStatus status) {

        this.status = status;
    }
}
```

---

# API Endpoints

## Place Order

Request:

```http
POST /orders/11
```

Response:

```json
{
  "id": 7,
  "totalAmount": 179998.0
}
```

Database:

```text
PENDING
```

---

## Verify Payment

Request:

```http
POST /payments/verify
```

Body:

```json
{
  "razorpayOrderId":"order_xxx",
  "razorpayPaymentId":"pay_xxx",
  "razorpaySignature":"signature_xxx"
}
```

Database:

```text
PAYMENT_SUCCESS
```

---

## Update Status

Request:

```http
PUT /orders/7/status
```

Body:

```json
{
  "status":"PROCESSING"
}
```

Response:

```json
{
  "id":7,
  "status":"PROCESSING"
}
```

---

# Status Transition Examples

## Processing

```json
{
  "status":"PROCESSING"
}
```

---

## Packed

```json
{
  "status":"PACKED"
}
```

---

## Shipped

```json
{
  "status":"SHIPPED"
}
```

---

## Out For Delivery

```json
{
  "status":"OUT_FOR_DELIVERY"
}
```

---

## Delivered

```json
{
  "status":"DELIVERED"
}
```

---

## Cancelled

```json
{
  "status":"CANCELLED"
}
```

---

## Returned

```json
{
  "status":"RETURNED"
}
```

---

# Postman Test Cases

## Happy Flow

```text
Place Order
→ Verify Payment
→ PROCESSING
→ PACKED
→ SHIPPED
→ OUT_FOR_DELIVERY
→ DELIVERED
```

Expected:

```text
200 OK
```

---

## Invalid Order ID

Request:

```http
PUT /orders/999/status
```

Expected:

```text
Order not found
```

---

## Invalid Status

Body:

```json
{
  "status":"HELLO"
}
```

Expected:

```text
400 Bad Request
```

---

## User Updating Status

User Token:

```http
PUT /orders/7/status
```

Expected:

```text
403 Forbidden
```

---

# Database Example

```sql
SELECT id,status
FROM orders;
```

Output:

```text
+----+------------------+
| id | status           |
+----+------------------+
|  5 | PAYMENT_SUCCESS  |
|  6 | SHIPPED          |
|  7 | DELIVERED        |
+----+------------------+
```

---

# Common Errors Faced During Development

## Error 1

```text
403 Forbidden
```

Cause:

```text
JWT Role Missing
```

Fix:

```java
ROLE_ADMIN
ROLE_USER
```

configured correctly.

---

## Error 2

```text
JWT Token Expired
```

Cause:

```text
Old token used
```

Fix:

```text
Login again
Copy fresh token
```

---

## Error 3

```text
Payment not found
```

Cause:

```text
Wrong Razorpay Order ID
```

Fix:

Use:

```text
payment.razorpay_order_id
```

from database.

---

# Concepts Learned

## Enum

Fixed list of values.

Example:

```java
OrderStatus.SHIPPED
```

---

## Workflow Management

Tracks order progress through multiple stages.

---

## Role Based Authorization

Only admins can update order status.

---

## Payment Integration

Payment verification automatically changes order state.

---

# Final Outcome

Successfully implemented a complete Order Status Workflow system with:

* Order Tracking
* Payment Integration
* Status Management
* Admin Control
* Delivery Lifecycle
* Cancellation Support
* Return Support
* Postman Testing
* Real E-Commerce Workflow
