import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../services/api";

function Register() {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [message, setMessage] = useState("");
    const [submitting, setSubmitting] = useState(false);
    const navigate = useNavigate();

    const handleRegister = async (event) => {
        event.preventDefault();
        setError("");
        setMessage("");
        setSubmitting(true);
        try {
            await api.post("/auth/register", { name, email, password });
            setMessage("Your account is ready. Taking you to sign in…");
            window.setTimeout(() => navigate("/login"), 900);
        } catch (requestError) {
            setError(requestError.response?.data?.message || "We couldn't create your account. Please try again.");
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <section className="auth-page">
            <div className="auth-art register-art">
                <span className="eyebrow">A GOOD PLACE TO START</span>
                <div className="auth-art-circle"><span>find<br />your<br /><i>favorite</i></span></div>
                <span className="auth-art-caption">A THOUGHTFUL COLLECTION ✳</span>
            </div>
            <div className="auth-form-wrap">
                <span className="eyebrow"><span className="eyebrow-dot" /> MAKE YOURSELF AT HOME</span>
                <h1>Meet your<br /><span>new favorite.</span></h1>
                <p className="auth-description">Create an account and start exploring.</p>
                {error && <p className="form-message form-error" role="alert">{error}</p>}
                {message && <p className="form-message form-success" role="status">{message}</p>}
                <form className="auth-form" onSubmit={handleRegister}>
                    <label>Your name<input type="text" value={name} onChange={(event) => setName(event.target.value)} placeholder="How should we call you?" autoComplete="name" required /></label>
                    <label>Email address<input type="email" value={email} onChange={(event) => setEmail(event.target.value)} placeholder="you@example.com" autoComplete="email" required /></label>
                    <label>Password<input type="password" value={password} onChange={(event) => setPassword(event.target.value)} placeholder="Create a password" autoComplete="new-password" required /></label>
                    <button className="button button-dark auth-submit" type="submit" disabled={submitting}>{submitting ? "Creating your account…" : "Create account"} <span aria-hidden="true">↗</span></button>
                </form>
                <p className="auth-switch">Already have an account? <Link to="/login">Sign in</Link></p>
            </div>
        </section>
    );
}

export default Register;
