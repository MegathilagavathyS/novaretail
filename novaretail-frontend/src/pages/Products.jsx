import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../services/api";

function Products() {

    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchProducts = async () => {

        try {

            setLoading(true);
            setError("");

            const response = await api.get("/products");

            console.log("Products response:", response.data);

            setProducts(response.data);

        } catch (error) {

            console.error("Products error:", error);

            if (error.response) {

                if (error.response.status === 401) {

                    setError(
                        "Please login to view products."
                    );

                } else {

                    setError(
                        error.response.data?.message ||
                        "Failed to load products."
                    );

                }

            } else {

                setError(
                    "Cannot connect to backend."
                );

            }

        } finally {

            setLoading(false);

        }
    };

    if (loading) {

        return (
            <div className="products-loading">

                <h1>Products</h1>

                <p>
                    Loading products...
                </p>

            </div>
        );
    }

    if (error) {

        return (
            <div className="products-error">

                <h1>Products</h1>

                <p>
                    {error}
                </p>

            </div>
        );
    }

    return (
        <div className="products-page">

            <h1>NovaRetail Products</h1>

            {products.length === 0 ? (

                <p>
                    No products available.
                </p>

            ) : (

                <div className="product-grid">

                    {products.map((product) => (

                        <div
                            className="product-card"
                            key={product.id}
                        >

                            <h2>
                                {product.name}
                            </h2>

                            <p className="product-category">
                                Category:{" "}
                                {product.categoryName || "N/A"}
                            </p>

                            <p className="product-description">
                                {product.description}
                            </p>

                            <p className="product-price">
                                ₹{product.price}
                            </p>

                            <p className="product-stock">
                                Stock: {product.stock}
                            </p>

                            <p className="product-rating">
                                ⭐ {product.averageRating ?? 0}
                                {" "}
                                ({product.reviewCount ?? 0} reviews)
                            </p>

                            <Link
                                to={`/products/${product.id}`}
                            >
                                View Product
                            </Link>

                        </div>

                    ))}

                </div>
            )}

        </div>
    );
}

export default Products;