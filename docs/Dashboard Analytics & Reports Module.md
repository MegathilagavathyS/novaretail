# Dashboard Analytics & Reports Module

## Objective

Provide business insights for the e-commerce application.

The dashboard helps administrators monitor:

* Users
* Products
* Orders
* Revenue
* Inventory
* Ratings

---

# Features Implemented

## Total Users

Returns total registered users.

Endpoint:

```http
GET /dashboard
```

Response:

```json
{
  "totalUsers": 12
}
```

---

## Total Products

Returns total products.

Response:

```json
{
  "totalProducts": 10
}
```

---

## Total Orders

Returns total orders placed.

Response:

```json
{
  "totalOrders": 8
}
```

---

## Total Revenue

Revenue is calculated from successful orders.

Recommended statuses:

```text
PAID
DELIVERED
```

Response:

```json
{
  "totalRevenue": 179998.0
}
```

---

# Orders By Status

Shows current order distribution.

Endpoint:

```http
GET /dashboard/orders/status
```

Response:

```json
[
  ["PENDING",7],
  ["PAID",1]
]
```

Use Case:

* Track pending shipments
* Track delivered orders
* Monitor cancellations

---

# Low Stock Products

Identifies products running out of stock.

Endpoint:

```http
GET /dashboard/low-stock
```

Query Logic:

```java
findByStockLessThan(10)
```

Example:

```json
[
  {
    "id":1,
    "name":"iPhone 16",
    "stock":5
  }
]
```

Use Case:

* Inventory alerts
* Reordering products

---

# Top Rated Products

Shows highest-rated products.

Endpoint:

```http
GET /dashboard/top-rated
```

Example:

```json
[
  [1,4.5],
  [2,4.3]
]
```

Meaning:

```text
Product ID 1 -> Rating 4.5
Product ID 2 -> Rating 4.3
```

Use Case:

* Best seller recommendations
* Featured products

---

# Monthly Revenue

Shows revenue grouped by month.

Endpoint:

```http
GET /dashboard/monthly-revenue
```

Example:

```json
[
  [6,1961978.2]
]
```

Meaning:

```text
Month 6 (June)
Revenue = ₹19,61,978.20
```

Use Case:

* Business growth analysis
* Sales reports

---

# Bugs Fixed During Development

## Enum Mapping Error

Error:

```text
No enum constant OrderStatus.PAID
```

Cause:

Database contained:

```sql
PAID
```

but enum did not.

Fix:

```java
PAID
```

added to OrderStatus.

---

## Revenue Showing 0

Cause:

Revenue query counted only:

```text
DELIVERED
```

orders.

Database contained:

```text
PENDING
PAID
```

but no DELIVERED orders.

Fix:

Either:

```sql
UPDATE orders
SET status='DELIVERED'
```

or count:

```text
PAID + DELIVERED
```

orders.

---

## Low Stock Empty Result

Cause:

No product stock below threshold.

Fix:

Reduce stock:

```sql
UPDATE product
SET stock=5
WHERE id=1;
```

---

## Monthly Revenue Validation

Verified using:

```sql
SELECT SUM(total_amount)
FROM orders;
```

Result matched dashboard output.

---

# APIs Summary

```http
GET /dashboard
GET /dashboard/orders/status
GET /dashboard/low-stock
GET /dashboard/top-rated
GET /dashboard/monthly-revenue
```

---

# Outcome

Successfully implemented:

* Dashboard Analytics
* Revenue Reports
* Inventory Monitoring
* Order Status Reporting
* Product Rating Analytics
* Monthly Sales Reports

This module provides administrator-level business intelligence for the e-commerce application.
