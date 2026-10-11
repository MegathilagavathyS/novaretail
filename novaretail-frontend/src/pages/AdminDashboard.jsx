import { useCallback, useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import api from "../services/api";

const emptyProduct = { name: "", description: "", price: "", stock: "", categoryId: "" };
const revenueMonth = new Intl.DateTimeFormat("en", { month: "short" });
const monthName = (month) => revenueMonth.format(new Date(2026, Number(month) - 1, 1));
const money = (amount) => Number(amount || 0).toLocaleString("en-IN", { style: "currency", currency: "INR", maximumFractionDigits: 0 });

function AdminDashboard({ onSessionExpired }) {
    const [section, setSection] = useState("overview");
    const [summary, setSummary] = useState(null);
    const [products, setProducts] = useState([]);
    const [categories, setCategories] = useState([]);
    const [lowStock, setLowStock] = useState([]);
    const [monthlyRevenue, setMonthlyRevenue] = useState([]);
    const [loading, setLoading] = useState(true);
    const [accessDenied, setAccessDenied] = useState(false);
    const [formOpen, setFormOpen] = useState(false);
    const [productForm, setProductForm] = useState(emptyProduct);
    const [categoryName, setCategoryName] = useState("");
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");
    const [notice, setNotice] = useState("");

    const loadStore = useCallback(async () => {
        setLoading(true);
        setError("");
        const results = await Promise.allSettled([
            api.get("/dashboard"),
            api.get("/products"),
            api.get("/categories"),
            api.get("/dashboard/low-stock"),
            api.get("/dashboard/monthly-revenue"),
        ]);
        const [dashboard, productList, categoryList, lowStockList, revenueList] = results;
        const denied = results.some((result) => result.status === "rejected" && [401, 403].includes(result.reason?.response?.status));
        if (denied) {
            setAccessDenied(true);
            onSessionExpired();
            setError("Your administrator session is missing or expired. Sign in with an admin account and try again.");
        } else if (dashboard.status === "rejected" || productList.status === "rejected" || categoryList.status === "rejected") {
            const failure = [dashboard, productList, categoryList].find((result) => result.status === "rejected");
            setError(failure.reason?.response?.data?.message || failure.reason?.message || "We couldn't load store data.");
        } else {
            setSummary(dashboard.value.data);
            setProducts(Array.isArray(productList.value.data) ? productList.value.data : []);
            setCategories(Array.isArray(categoryList.value.data) ? categoryList.value.data : []);
            setLowStock(lowStockList.status === "fulfilled" && Array.isArray(lowStockList.value.data) ? lowStockList.value.data : []);
            setMonthlyRevenue(revenueList.status === "fulfilled" && Array.isArray(revenueList.value.data) ? revenueList.value.data : []);
            if (lowStockList.status === "rejected" || revenueList.status === "rejected") {
                setNotice("Some analytics could not be loaded. Core store data is available.");
            }
        }
        setLoading(false);
    }, [onSessionExpired]);

    useEffect(() => {
        loadStore();
    }, [loadStore]);

    const totalRevenueInChart = useMemo(
        () => Math.max(...monthlyRevenue.map((row) => Number(row?.[1] || 0)), 1),
        [monthlyRevenue],
    );

    const createProduct = async (event) => {
        event.preventDefault();
        setSaving(true);
        setError("");
        setNotice("");
        try {
            await api.post("/products", {
                ...productForm,
                price: Number(productForm.price),
                stock: Number(productForm.stock),
                categoryId: Number(productForm.categoryId),
            });
            setProductForm(emptyProduct);
            setFormOpen(false);
            setNotice("Product created.");
            await loadStore();
        } catch (requestError) {
            setError(requestError.response?.data?.message || "Couldn't create this product. Check its details and category.");
        } finally {
            setSaving(false);
        }
    };

    const deleteProduct = async (product) => {
        if (!window.confirm(`Delete "${product.name}"? This cannot be undone.`)) return;
        setError("");
        setNotice("");
        try {
            await api.delete(`/products/${product.id}`);
            setNotice(`${product.name} was deleted.`);
            await loadStore();
        } catch (requestError) {
            setError(requestError.response?.data?.message || `Couldn't delete ${product.name}.`);
        }
    };

    const createCategory = async (event) => {
        event.preventDefault();
        setSaving(true);
        setError("");
        setNotice("");
        try {
            await api.post("/categories", { name: categoryName.trim() });
            setCategoryName("");
            setNotice("Category created.");
            await loadStore();
        } catch (requestError) {
            setError(requestError.response?.data?.message || "Couldn't create this category.");
        } finally {
            setSaving(false);
        }
    };

    return (
        <section className="admin-page">
            <aside className="admin-sidebar">
                <div className="admin-sidebar-heading"><span className="eyebrow">STORE WORKSPACE</span><h2>Back office</h2></div>
                <span className="admin-nav-label">MANAGE</span>
                <button className={`admin-nav-item${section === "overview" ? " active" : ""}`} onClick={() => setSection("overview")}><span>◫</span> Overview</button>
                <button className={`admin-nav-item${section === "products" ? " active" : ""}`} onClick={() => setSection("products")}><span>◇</span> Products <span className="admin-nav-count">{products.length}</span></button>
                <button className={`admin-nav-item${section === "categories" ? " active" : ""}`} onClick={() => setSection("categories")}><span>◈</span> Categories <span className="admin-nav-count">{categories.length}</span></button>
                <div className="admin-sidebar-note"><span>✳</span><p>Make every detail feel like NovaRetail.</p></div>
            </aside>

            <div className="admin-content">
                <header className="admin-topbar">
                    <div><span className="eyebrow">GOOD TO HAVE YOU HERE</span><h1>{section === "overview" ? "Your store, at a glance." : section === "products" ? "Product collection." : "Product categories."}</h1></div>
                    <div className="admin-topbar-actions">
                        <span className="admin-role"><span /> ADMIN</span>
                        <button className="button button-dark admin-add-button" onClick={() => { setSection("products"); setFormOpen((open) => !open); }}>+ Add product</button>
                    </div>
                </header>

                {error && <div className="admin-alert admin-alert-error" role="alert">{error} <button onClick={() => setError("")} aria-label="Dismiss error">×</button></div>}
                {notice && <div className="admin-alert" role="status">{notice} <button onClick={() => setNotice("")} aria-label="Dismiss message">×</button></div>}
                {loading ? (
                    <div className="state-panel"><span className="loader" /><p>Getting your store ready…</p></div>
                ) : accessDenied ? (
                    <div className="state-panel error-panel"><span className="state-symbol">!</span><h2>Administrator sign-in required</h2><p>Your session expired or doesn’t have admin access.</p><Link className="button button-dark" to="/login">Sign in again</Link></div>
                ) : (
                    <>
                        {section === "overview" && (
                            <>
                                <div className="admin-stat-grid">
                                    <article className="admin-stat"><span>TOTAL REVENUE</span><strong>{money(summary?.totalRevenue)}</strong><small>From delivered orders</small><i>↗</i></article>
                                    <article className="admin-stat"><span>ORDERS PLACED</span><strong>{Number(summary?.totalOrders || 0).toLocaleString("en-IN")}</strong><small>All-time store orders</small><i>▤</i></article>
                                    <article className="admin-stat"><span>PRODUCTS LIVE</span><strong>{Number(summary?.totalProducts ?? products.length).toLocaleString("en-IN")}</strong><small>Across {categories.length} categories</small><i>◇</i></article>
                                    <article className="admin-stat"><span>YOUR COMMUNITY</span><strong>{Number(summary?.totalUsers || 0).toLocaleString("en-IN")}</strong><small>Registered customers</small><i>♧</i></article>
                                </div>
                                <div className="admin-overview-grid">
                                    <section className="admin-panel revenue-panel">
                                        <div className="admin-panel-heading"><div><span className="eyebrow">THE BIG PICTURE</span><h2>Monthly revenue</h2></div><span className="admin-panel-meta">ALL AVAILABLE MONTHS</span></div>
                                        {monthlyRevenue.length ? (
                                            <div className="revenue-chart" aria-label="Monthly revenue chart">
                                                {monthlyRevenue.map((row) => (
                                                    <div className="revenue-column" key={row[0]}>
                                                        <span className="revenue-value">{money(row[1])}</span>
                                                        <div className="revenue-bar-track"><span style={{ height: `${Math.max(Number(row[1]) / totalRevenueInChart * 100, 4)}%` }} /></div>
                                                        <span className="revenue-month">{monthName(row[0])}</span>
                                                    </div>
                                                ))}
                                            </div>
                                        ) : <div className="admin-empty-chart"><span>↗</span><p>No monthly revenue data yet.</p></div>}
                                    </section>
                                    <section className="admin-panel stock-panel">
                                        <div className="admin-panel-heading"><div><span className="eyebrow">A LITTLE HEADS-UP</span><h2>Running low</h2></div><button className="text-action" onClick={() => setSection("products")}>See products →</button></div>
                                        {lowStock.length ? <div className="low-stock-list">{lowStock.slice(0, 5).map((item) => <div className="low-stock-row" key={item.id}><span className="stock-product-mark">{(item.name || "N").slice(0, 1)}</span><span><strong>{item.name}</strong><small>{item.category?.name || "Product"}</small></span><span className={`stock-level${Number(item.stock) <= 3 ? " critical" : ""}`}>{item.stock} left</span></div>)}</div> : <div className="admin-empty-stock"><span>✓</span><p>Everything is comfortably stocked.</p></div>}
                                    </section>
                                </div>
                                <section className="admin-panel recent-products-panel">
                                    <div className="admin-panel-heading"><div><span className="eyebrow">RECENTLY IN THE COLLECTION</span><h2>Products</h2></div><button className="text-action" onClick={() => setSection("products")}>Manage products →</button></div>
                                    <ProductTable products={products.slice(0, 5)} onDelete={deleteProduct} />
                                </section>
                            </>
                        )}

                        {section === "products" && (
                            <section className="admin-panel management-panel">
                                <div className="admin-panel-heading">
                                    <div><span className="eyebrow">THE NOVARETAIL EDIT</span><h2>All products <span className="table-count">{products.length}</span></h2></div>
                                    <button className="button button-dark admin-add-button" onClick={() => setFormOpen((open) => !open)}>{formOpen ? "Cancel" : "+ Add product"}</button>
                                </div>
                                <p className="admin-capability-note">Create and remove products here. Product editing is not currently exposed by the backend API.</p>
                                {formOpen && (
                                    <form className="admin-form" onSubmit={createProduct}>
                                        <div className="admin-form-heading"><span className="eyebrow">ADD SOMETHING GOOD</span><h3>New product</h3></div>
                                        <label>Product name<input value={productForm.name} onChange={(event) => setProductForm({ ...productForm, name: event.target.value })} required maxLength={120} /></label>
                                        <label>Description<textarea value={productForm.description} onChange={(event) => setProductForm({ ...productForm, description: event.target.value })} rows="3" /></label>
                                        <div className="admin-form-row">
                                            <label>Price (₹)<input type="number" min="0.01" step="0.01" value={productForm.price} onChange={(event) => setProductForm({ ...productForm, price: event.target.value })} required /></label>
                                            <label>Stock<input type="number" min="0" step="1" value={productForm.stock} onChange={(event) => setProductForm({ ...productForm, stock: event.target.value })} required /></label>
                                            <label>Category<select value={productForm.categoryId} onChange={(event) => setProductForm({ ...productForm, categoryId: event.target.value })} required><option value="">Choose category</option>{categories.map((category) => <option key={category.id} value={category.id}>{category.name}</option>)}</select></label>
                                        </div>
                                        {!categories.length && <p className="form-hint">Create a category before adding a product.</p>}
                                        <button className="button button-dark" type="submit" disabled={saving || !categories.length}>{saving ? "Saving product…" : "Create product"} <span aria-hidden="true">↗</span></button>
                                    </form>
                                )}
                                <ProductTable products={products} onDelete={deleteProduct} />
                            </section>
                        )}

                        {section === "categories" && (
                            <section className="admin-panel management-panel">
                                <div className="admin-panel-heading"><div><span className="eyebrow">ORGANIZE YOUR COLLECTION</span><h2>Categories <span className="table-count">{categories.length}</span></h2></div></div>
                                <p className="admin-capability-note">Categories are used when creating products. The backend has no safe category-edit flow; deleting a category can also delete its products, so removal is intentionally not offered here.</p>
                                <form className="category-create-form" onSubmit={createCategory}>
                                    <label>New category name<input value={categoryName} onChange={(event) => setCategoryName(event.target.value)} placeholder="e.g. Home & living" required maxLength={80} /></label>
                                    <button className="button button-dark" type="submit" disabled={saving || !categoryName.trim()}>{saving ? "Adding…" : "Add category"} <span aria-hidden="true">↗</span></button>
                                </form>
                                <div className="category-admin-grid">
                                    {categories.map((category, index) => {
                                        const linkedProducts = products.filter((product) => product.categoryName === category.name);
                                        return <article className={`category-admin-card product-tone-${index % 4}`} key={category.id}><span className="category-admin-mark">{String(index + 1).padStart(2, "0")}</span><div><h3>{category.name}</h3><p>{linkedProducts.length} {linkedProducts.length === 1 ? "product" : "products"} in this category</p></div></article>;
                                    })}
                                    {!categories.length && <div className="admin-empty-chart"><p>No categories yet. Add one to start organizing products.</p></div>}
                                </div>
                            </section>
                        )}
                    </>
                )}
            </div>
        </section>
    );
}

function ProductTable({ products, onDelete }) {
    return (
        <div className="product-table-wrap">
            <table className="product-table">
                <thead><tr><th>PRODUCT</th><th>CATEGORY</th><th>PRICE</th><th>IN STOCK</th><th>RATING</th><th aria-label="Actions" /></tr></thead>
                <tbody>
                    {products.map((product, index) => (
                        <tr key={product.id}>
                            <td><span className={`table-product-mark product-tone-${index % 4}`}>{(product.name || "N").slice(0, 1).toUpperCase()}</span><span className="table-product-name">{product.name}</span></td>
                            <td>{product.categoryName || "—"}</td>
                            <td>{money(product.price)}</td>
                            <td><span className={`table-stock${Number(product.stock) < 10 ? " low" : ""}`}><i />{product.stock}</span></td>
                            <td>★ {Number(product.averageRating || 0).toFixed(1)}</td>
                            <td><button className="table-delete" onClick={() => onDelete(product)} aria-label={`Delete ${product.name}`}>Delete</button></td>
                        </tr>
                    ))}
                    {!products.length && <tr><td className="table-empty" colSpan="6">No products here yet. Add the first product to your collection.</td></tr>}
                </tbody>
            </table>
        </div>
    );
}

export default AdminDashboard;
