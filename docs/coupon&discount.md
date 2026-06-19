# Coupon & Discount Module (Spring Boot E-Commerce)

## Overview

The Coupon module allows users to apply discount codes during order placement. It helps simulate real-world e-commerce offers like:

- FESTIVE SALE
- WELCOME DISCOUNT
- FIRST ORDER OFFERS

---

# Database Table

## coupon

| Column                | Type      | Description                     |
|----------------------|----------|---------------------------------|
| id                   | INT       | Primary Key                    |
| code                 | VARCHAR   | Unique coupon code             |
| discount_percentage  | DOUBLE    | Discount value (%)             |
| expiry_date         | DATETIME  | Expiration date               |
| active              | BOOLEAN   | Whether coupon is valid       |

---

# Example Data

```sql
INSERT INTO coupon (code, discount_percentage, expiry_date, active)
VALUES ('WELCOME10', 10, '2026-12-31 23:59:59', true);
