# Order Management - Spring Boot E-Commerce

## Objective

Convert Cart items into Orders and Order Items.

---

# Concepts Learned

## 1. Cart → Order Flow

User adds products to cart.

Example:

```text
User
 └── Cart
      ├── Product A × 2
      └── Product B × 1
```

When checkout happens:

```text
Cart
   ↓
Order
   ↓
OrderItems
```

---

## 2. One-To-Many Relationship

One Order can contain many OrderItems.

```java
@OneToMany(
        mappedBy = "order",
        cascade = CascadeType.ALL,
        orphanRemoval = true
)
private List<OrderItem> items;
```

Database:

```text
orders
   |
   └── order_item
```

---

## 3. Many-To-One Relationship

Many OrderItems belong to one Order.

```java
@ManyToOne
@JoinColumn(name = "order_id")
private Order order;
```

---

## 4. CascadeType.ALL

Saving Order automatically saves OrderItems.

```java
cascade = CascadeType.ALL
```

Example:

```java
orderRepository.save(order);
```

Automatically saves:

```text
Order
OrderItem 1
OrderItem 2
OrderItem 3
```

---

## 5. Order Total Calculation

```java
totalAmount +=
    product.getPrice()
    * cartItem.getQuantity();
```

Example:

```text
iPhone 16 = ₹89,999

Quantity = 2

Total = ₹179,998
```

---

## 6. Copying Product Price

Price is copied into OrderItem.

```java
orderItem.setPrice(
        product.getPrice());
```

Why?

If product price changes later:

```text
Current Price = ₹95,000
```

Old order still stores:

```text
₹89,999
```

Historical accuracy maintained.

---

## 7. Order Status

Added:

```java
private String status;
```

Possible values:

```text
PENDING
CONFIRMED
SHIPPED
DELIVERED
CANCELLED
```

Example:

```java
order.setStatus("PENDING");
```

---

## 8. Clearing Cart After Checkout

```java
cart.getItems().clear();

cartRepository.save(cart);
```

Result:

```text
Cart becomes empty
Order created successfully
```

---

## 9. DTO Usage

Returned:

```java
OrderResponseDTO
```

instead of Entity.

Benefits:

* Security
* Clean API
* Better architecture

---

## 10. Repository Query Methods

```java
List<Order> findByUserId(
        Integer userId);
```

Spring generates query automatically.

---

# APIs Created

## Place Order

POST

```http
/orders/place/{userId}
```

Example:

```http
POST /orders/place/1
```

---

## Get User Orders

GET

```http
/orders/{userId}
```

Example:

```http
GET /orders/1
```

---

## Get Order Details

GET

```http
/orders/details/{orderId}
```

Example:

```http
GET /orders/details/1
```

---

# Database Tables

orders

```text
id
order_date
status
user_id
total_amount
```

order_item

```text
id
quantity
price
order_id
product_id
```

---

# Real World Learning

This is how:

* Amazon
* Flipkart
* Myntra
* Swiggy
* Zomato

store orders internally.

The concepts are industry-standard JPA/Hibernate practices.

---

# Summary

Completed:

✅ User Management

✅ DTO Pattern

✅ Validation

✅ Exception Handling

✅ Pagination & Sorting

✅ Address Management

✅ Profile Management

✅ JWT Authentication

✅ Spring Security

✅ Category Management

✅ Product Management

✅ Cart Management

✅ Order Management

✅ OneToOne Mapping

✅ OneToMany Mapping

✅ ManyToOne Mapping

✅ ManyToMany Mapping

✅ Cascade Operations

✅ Hibernate Relationships

✅ Repository Queries

✅ E-Commerce Checkout Flow
