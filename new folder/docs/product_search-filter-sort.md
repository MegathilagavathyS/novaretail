# Product Search + Filter + Sort

## Goal

Allow users to:

- Search products by name
- Filter products by category
- Filter products by price range
- Sort products by price
- Sort products by name

---

# Search

Example:

GET /products/search?name=iphone

Result:

[
  {
    "id":1,
    "name":"iPhone 16"
  }
]

Repository:

findByNameContainingIgnoreCase()

---

# Category Filter

GET /products/category/1

Result:

All products under category 1

---

# Price Filter

GET /products/price?min=50000&max=100000

Result:

Products between 50k and 100k

---

# Sorting

Price Ascending:

GET /products/sort/price

Price Descending:

GET /products/sort/price-desc

Name Ascending:

GET /products/sort/name

---

# Benefits

Improves user experience.

Used in:

- Amazon
- Flipkart
- Myntra
- eBay

---

# JPA Methods

findByCategoryId()

findByPriceBetween()

findByNameContainingIgnoreCase()

findAll(Sort.by("price"))

---

# Edge Cases

Search keyword not found

Returns:

[]

Category does not exist

Returns:

[]

Invalid price range

min > max

Throw exception

Negative price

Reject request
