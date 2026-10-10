import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import api from "../services/api";

function Products() {
    const [products, setProducts] = useState([]);
    const [query, setQuery] = useState("");
    const [category, setCategory] = useState("All");
    const [sort, setSort] = useState("featured");
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [notice, setNotice] = useState("");
    const [addingProduct, setAddingProduct] = useState(null);
    const userId = localStorage.getItem("userId");

    useEffect(() => {
        let active = true;
        api.get("/products")
            .then(({ data }) => {
                if (!Array.isArray(data)) {
                    throw new Error("The product service returned an unexpected response.");
                }
                if (active) setProducts(data);
            })
            .catch((requestError) => {
                if (!active) return;
                if ([401, 403].includes(requestError.response?.status)) {
                    setError("Sign in to browse the collection.");
                } else {
                    setError(requestError.response?.data?.message || requestError.message || "We couldn't load the collection.");
                }
            })
            .finally(() => {
                if (active) setLoading(false);
            });
        return () => { active = false; };
    }, []);

    const categories = useMemo(
        () => ["All", ...new Set(products.map((product) => product.categoryName).filter(Boolean))],
        [products],
    );

    const visibleProducts = useMemo(() => {
        const normalizedQuery = query.trim().toLowerCase();
        const result = products.filter((product) => {
            const matchesQuery = !normalizedQuery
                || `${product.name || ""} ${product.description || ""} ${product.categoryName || ""}`.toLowerCase().includes(normalizedQuery);
            return matchesQuery && (category === "All" || product.categoryName === category);
        });

        if (sort === "price-low") result.sort((a, b) => Number(a.price) - Number(b.price));
        if (sort === "price-high") result.sort((a, b) => Number(b.price) - Number(a.price));
        if (sort === "rating") result.sort((a, b) => Number(b.averageRating || 0) - Number(a.averageRating || 0));
        return result;
    }, [products, query, category, sort]);

    const addToBag = async (product) => {
        if (!userId) {
            setNotice("Please sign in before adding items to your bag.");
            return;
        }
        setAddingProduct(product.id);
        setNotice("");
        try {
            await api.post("/cart/add", { userId: Number(userId), productId: product.id, quantity: 1 });
            setNotice(`${product.name} added to your bag.`);
        } catch (requestError) {
            if ([401, 403].includes(requestError.response?.status)) {
                setNotice("Please sign in before adding items to your bag.");
            } else {
                setNotice(requestError.response?.data?.message || "We couldn't add this item. Please try again.");
            }
        } finally {
            setAddingProduct(null);
        }
    };

    return (
        <div className="shop-page">
            <section className="shop-heading">
                <div>
                    <span className="eyebrow"><span className="eyebrow-dot" /> THE NOVARETAIL EDIT</span>
                    <h1>Find your <span>good thing.</span></h1>
                    <p>Everyday pieces, picked to make your day a little better.</p>
                </div>
                <span className="shop-heading-note">A COLLECTION<br />WORTH KEEPING</span>
            </section>

            <section className="shop-controls" aria-label="Product filters">
                <label className="search-box">
                    <span aria-hidden="true">⌕</span>
                    <input value={query} onChange={(event) => setQuery(event.target.value)} placeholder="Search the collection" aria-label="Search products" />
                </label>
                <div className="filter-row">
                    <div className="category-filters" aria-label="Filter by category">
                        {categories.map((item) => (
                            <button key={item} className={`filter-chip${category === item ? " active" : ""}`} onClick={() => setCategory(item)}>
                                {item}
                            </button>
                        ))}
                    </div>
                    <label className="sort-control">Sort by
                        <select value={sort} onChange={(event) => setSort(event.target.value)}>
                            <option value="featured">Featured</option>
                            <option value="price-low">Price: low to high</option>
                            <option value="price-high">Price: high to low</option>
                            <option value="rating">Top rated</option>
                        </select>
                    </label>
                </div>
            </section>

            {notice && <p className="inline-notice" role="status">{notice} {notice.includes("sign in") && <Link to="/login">Sign in</Link>}</p>}

            {loading ? (
                <div className="state-panel"><span className="loader" /><p>Finding good things for you…</p></div>
            ) : error ? (
                <div className="state-panel error-panel">
                    <span className="state-symbol">!</span><h2>{error}</h2>
                    {error.includes("Sign in") ? <Link className="button button-dark" to="/login">Sign in to continue</Link> : <button className="button button-dark" onClick={() => window.location.reload()}>Try again</button>}
                </div>
            ) : visibleProducts.length === 0 ? (
                <div className="state-panel"><span className="state-symbol">⌕</span><h2>No finds just yet</h2><p>Try another search or choose a different category.</p></div>
            ) : (
                <>
                    <div className="result-line"><span>{visibleProducts.length} {visibleProducts.length === 1 ? "find" : "finds"}</span><span>Thoughtfully picked for you</span></div>
                    <div className="product-grid">
                        {visibleProducts.map((product, index) => (
                            <article className={`product-card product-tone-${index % 4}`} key={product.id}>
                                <div className="product-art">
                                    <span className="product-category">{product.categoryName || "NovaRetail pick"}</span>
                                    <span className="product-art-mark" aria-hidden="true">{(product.name || "N").slice(0, 1).toUpperCase()}</span>
                                    <span className="product-art-number">NR — {String(index + 1).padStart(2, "0")}</span>
                                </div>
                                <div className="product-info">
                                    <div className="product-title-line"><h2>{product.name}</h2><span className="product-rating">★ {Number(product.averageRating || 0).toFixed(1)}</span></div>
                                    <p className="product-description">{product.description || "A thoughtful addition to your everyday."}</p>
                                    <div className="product-buy-row">
                                        <span className="product-price">{Number(product.price).toLocaleString("en-IN", { style: "currency", currency: "INR" })}</span>
                                        <button className="add-button" onClick={() => addToBag(product)} disabled={addingProduct === product.id || Number(product.stock) < 1}>
                                            {Number(product.stock) < 1 ? "Sold out" : addingProduct === product.id ? "Adding…" : "Add to bag +"}
                                        </button>
                                    </div>
                                    <span className="product-stock">{Number(product.stock) > 0 ? "Ready when you are" : "Currently unavailable"}</span>
                                </div>
                            </article>
                        ))}
                    </div>
                </>
            )}
        </div>
    );
}

export default Products;
