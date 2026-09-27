import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function Register() {

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const navigate = useNavigate();

    const handleRegister = async (event) => {

        event.preventDefault();

        setMessage("");
        setError("");

        try {

            const response = await api.post("/auth/register", {
                name: name,
                email: email,
                password: password
            });

            console.log("Register response:", response.data);

            setMessage("Registration successful!");

            setTimeout(() => {
                navigate("/login");
            }, 1000);

        } catch (error) {

            console.error("Registration error:", error);

            if (error.response) {
                setError(
                    error.response.data?.message ||
                    "Registration failed"
                );
            } else {
                setError("Cannot connect to backend.");
            }
        }
    };

    return (
        <div className="login-container">

            <h1>NovaRetail</h1>

            <h2>Create Account</h2>

            {message && (
                <p style={{ color: "green" }}>
                    {message}
                </p>
            )}

            {error && (
                <p style={{ color: "red" }}>
                    {error}
                </p>
            )}

            <form onSubmit={handleRegister}>

                <div>
                    <label>Name</label>

                    <input
                        type="text"
                        value={name}
                        onChange={(event) =>
                            setName(event.target.value)
                        }
                        placeholder="Enter your name"
                        required
                    />
                </div>

                <br />

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

                <br />

                <div>
                    <label>Password</label>

                    <input
                        type="password"
                        value={password}
                        onChange={(event) =>
                            setPassword(event.target.value)
                        }
                        placeholder="Create password"
                        required
                    />
                </div>

                <br />

                <button type="submit">
                    Sign Up
                </button>

            </form>

            <br />

            <button onClick={() => navigate("/login")}>
                Already have an account? Login
            </button>

        </div>
    );
}

export default Register;