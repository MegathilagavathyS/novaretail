import { useState } from "react";
import { BrowserRouter, Link, NavLink, Route, Routes } from "react-router-dom";
import "./App.css";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Products from "./pages/Products";
import Register from "./pages/Register";
import Cart from "./pages/Cart";
import AdminDashboard from "./pages/AdminDashboard";

function App() {
    const [signedIn, setSignedIn] = useState(() => Boolean(localStorage.getItem("token")));
    const [role, setRole] = useState(() => localStorage.getItem("role") || "");

    const handleSignOut = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("userId");
        localStorage.removeItem("userName");
        localStorage.removeItem("role");
        setSignedIn(false);
        setRole("");
    };

    return (
        <BrowserRouter>
            <div className="app-shell">
                <header className="site-header">
                    <Link className="brand" to="/" aria-label="NovaRetail home">
                        <span className="brand-mark">N</span>
                        <span>nova<span className="brand-light">retail</span></span>
                    </Link>
                    <nav className="main-nav" aria-label="Main navigation">
                        <NavLink to="/" end>Home</NavLink>
                        <NavLink to="/products">Shop</NavLink>
                        {role === "ADMIN" && <NavLink to="/admin">Admin</NavLink>}
                    </nav>
                    <div className="header-actions">
                        <NavLink className="cart-link" to="/cart">
                            <span aria-hidden="true">Bag</span>
                        </NavLink>
                        {signedIn ? (
                            <button className="button button-small button-quiet" onClick={handleSignOut}>
                                Sign out
                            </button>
                        ) : (
                            <Link className="button button-small button-dark" to="/login">
                                Sign in
                            </Link>
                        )}
                    </div>
                </header>
                <main>
                    <Routes>
                        <Route path="/" element={<Home />} />
                        <Route path="/products" element={<Products />} />
                        <Route path="/login" element={<Login onSignedIn={(userRole) => { setSignedIn(true); setRole(userRole); }} />} />
                        <Route path="/register" element={<Register />} />
                        <Route path="/cart" element={<Cart />} />
                        <Route path="/admin" element={role === "ADMIN" ? <AdminDashboard onSessionExpired={() => { localStorage.removeItem("token"); localStorage.removeItem("userId"); localStorage.removeItem("userName"); localStorage.removeItem("role"); setSignedIn(false); setRole(""); }} /> : <div className="admin-access-denied"><span className="eyebrow">ADMIN AREA</span><h1>Store management is for admins.</h1><p>Sign in with an administrator account to continue.</p><Link className="button button-dark" to={signedIn ? "/" : "/login"}>{signedIn ? "Back to the shop" : "Sign in"}</Link></div>} />
                        <Route path="*" element={<Home />} />
                    </Routes>
                </main>
                <footer className="site-footer">
                    <Link className="brand footer-brand" to="/">
                        <span className="brand-mark">N</span>
                        <span>nova<span className="brand-light">retail</span></span>
                    </Link>
                    <span>Thoughtful finds, delivered with care.</span>
                    <span>© {new Date().getFullYear()} NovaRetail</span>
                </footer>
            </div>
        </BrowserRouter>
    );
}

export default App;
