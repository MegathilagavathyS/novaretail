import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../services/api";

function Cart() {
    const userId = localStorage.getItem("userId");
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(Boolean(userId));
    const [error, setError] = useState("");

    useEffect(() => {
        if (!userId) return;
        let active = true;
        api.get(`/cart/${encodeURIComponent(userId)}`)
            .then(({ data }) => {
                if (active) setItems(Array.isArray(data?.items) ? data.items : []);
            })
            .catch((requestError) => {
                if (active) {
                    setError([401, 403].includes(requestError.response?.status)
                        ? "Your session may have expired. Please sign in to view your bag."
                        : requestError.response?.data?.message || "We couldn't load your bag. Please try again.");
                }
            })
            .finally(() => {
                if (active) setLoading(false);
            });
        return () => { active = false; };
    }, [userId]);

    const subtotal = items.reduce((sum, item) => sum + Number(item.product?.price || 0) * Number(item.quantity || 0), 0);

    return (
        <section className="cart-page">
            <span className="eyebrow"><span className="eyebrow-dot" /> YOUR LITTLE COLLECTION</span>
            <div className="cart-heading"><h1>Your bag<span>.</span></h1><Link to="/products">← Keep exploring</Link></div>
            {!userId ? (
                <div className="state-panel"><span className="state-symbol">♡</span><h2>Your bag is waiting</h2><p>Sign in to see the things you’ve picked out.</p><Link className="button button-dark" to="/login">Sign in</Link></div>
            ) : loading ? (
                <div className="state-panel"><span className="loader" /><p>Opening your bag…</p></div>
            ) : error ? (
                <div className="state-panel error-panel"><span className="state-symbol">!</span><h2>We couldn't open your bag</h2><p>{error}</p>{error.includes("sign in") ? <Link className="button button-dark" to="/login">Sign in</Link> : <button className="button button-dark" onClick={() => window.location.reload()}>Try again</button>}</div>
            ) : items.length === 0 ? (
                <div className="state-panel"><span className="state-symbol">♡</span><h2>A little room for something good</h2><p>Your bag is empty for now. Go find a new favorite.</p><Link className="button button-dark" to="/products">Explore the collection</Link></div>
            ) : (
                <div className="cart-layout">
                    <div className="cart-items">
                        {items.map((item) => (
                            <article className="cart-item" key={item.id}>
                                <div className="cart-item-art"><span>{(item.product?.name || "N").slice(0, 1).toUpperCase()}</span></div>
                                <div className="cart-item-copy"><h2>{item.product?.name || "NovaRetail find"}</h2><p>{item.product?.category?.name || "Thoughtfully picked"}</p><span>Quantity: {item.quantity}</span></div>
                                <strong>{(Number(item.product?.price || 0) * Number(item.quantity || 0)).toLocaleString("en-IN", { style: "currency", currency: "INR" })}</strong>
                            </article>
                        ))}
                    </div>
                    <aside className="cart-summary"><span className="eyebrow">THE LITTLE DETAILS</span><div><span>Subtotal</span><strong>{subtotal.toLocaleString("en-IN", { style: "currency", currency: "INR" })}</strong></div><p>Shipping and payment are handled by the store’s checkout flow.</p><span className="summary-note">Checkout isn’t available in this frontend yet.</span></aside>
                </div>
            )}
        </section>
    );
}

export default Cart;
