import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const navigate = useNavigate();

    const handleLogin = async (event) => {

        event.preventDefault();
        setError("");

        try {

            const response = await api.post("/auth/login", {
                email: email,
                password: password
            });

            console.log("Login response:", response.data);

            const token = response.data.token;

            if (!token) {
                setError("Token was not returned by backend.");
                return;
            }

            localStorage.setItem("token", token);

            navigate("/");

        } catch (error) {

            console.error("Login error:", error);

            if (error.response) {
                setError(
                    error.response.data?.message ||
                    "Login failed"
                );
            } else {
                setError("Cannot connect to backend.");
            }
        }
    };

    return (
        <div className="login-container">

            <h1>NovaRetail</h1>

            <h2>Login</h2>

            {error && (
                <p className="error">
                    {error}
                </p>
            )}

            <form onSubmit={handleLogin}>

                <div>
                    <label>Email</label>

                    <input
                        type="email"
                        value={email}
                        onChange={(event) =>
                            setEmail(event.target.value)
                        }
                        placeholder="Enter your email"
                        required
                    />
                </div>

                <div>
                    <label>Password</label>

                    <input
                        type="password"
                        value={password}
                        onChange={(event) =>
                            setPassword(event.target.value)
                        }
                        placeholder="Enter your password"
                        required
                    />
                </div>

                <button type="submit">
                    Login
                </button>

            </form>

        </div>
    );
}

export default Login;