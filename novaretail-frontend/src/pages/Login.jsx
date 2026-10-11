import { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import api from "../services/api";

function Login({ onSignedIn }) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [submitting, setSubmitting] = useState(false);
    const navigate = useNavigate();
    const location = useLocation();

    const handleLogin = async (event) => {
        event.preventDefault();
        setError("");
        setSubmitting(true);
        try {
            const { data } = await api.post("/auth/login", { email, password });
            if (!data?.token) throw new Error("The sign-in service did not return an access token.");

            localStorage.setItem("token", data.token);
            const userRole = String(data.role || "").replace(/^ROLE_/i, "").toUpperCase();
            const { data: user } = await api.get(`/users/email/${encodeURIComponent(email)}`);
            if (!user?.id) throw new Error("Your account was verified, but we couldn't load your profile.");

            localStorage.setItem("userId", String(user.id));
            localStorage.setItem("userName", user.name || "");
            localStorage.setItem("role", userRole);
            onSignedIn(userRole);
            navigate(userRole === "ADMIN" ? "/admin" : location.state?.from || "/products");
        } catch (requestError) {
            localStorage.removeItem("token");
            localStorage.removeItem("userId");
            localStorage.removeItem("userName");
            localStorage.removeItem("role");
            setError(requestError.response?.data?.message || requestError.message || "Sign-in failed. Check your details and try again.");
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <section className="auth-page">
            <div className="auth-art">
                <span className="eyebrow">WELCOME BACK</span>
                <div className="auth-art-circle"><span>good<br />things<br /><i>inside</i></span></div>
                <span className="auth-art-caption">YOUR EVERYDAY, A LITTLE BETTER ✳</span>
            </div>
            <div className="auth-form-wrap">
                <span className="eyebrow"><span className="eyebrow-dot" /> PICK UP WHERE YOU LEFT OFF</span>
                <h1>Good to see<br />you <span>again.</span></h1>
                <p className="auth-description">Sign in to keep your finds close.</p>
                {error && <p className="form-message form-error" role="alert">{error}</p>}
                <form className="auth-form" onSubmit={handleLogin}>
                    <label>Email address<input type="email" value={email} onChange={(event) => setEmail(event.target.value)} placeholder="you@example.com" autoComplete="email" required /></label>
                    <label>Password<input type="password" value={password} onChange={(event) => setPassword(event.target.value)} placeholder="Enter your password" autoComplete="current-password" required /></label>
                    <button className="button button-dark auth-submit" type="submit" disabled={submitting}>{submitting ? "Signing you in…" : "Sign in"} <span aria-hidden="true">↗</span></button>
                </form>
                <p className="auth-switch">New to NovaRetail? <Link to="/register">Create an account</Link></p>
            </div>
        </section>
    );
}

export default Login;
