import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { useGlobalContext } from '../Context';

// USER PAGE //
const User = () => {
    const navigate = useNavigate();
    const [ username, setUsername] = useState("");
    const { jwt, setJwt } = useGlobalContext();

    useEffect(() => {
        const getUsername = async() => {
            const response = await fetch("http://localhost:8080/user/getUsername", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer "+jwt,
                }                
            });     
            const name = await response.text();        
            if(name) setUsername(name);
        }
        getUsername();
    },[jwt])

    const handleLogout = () => {
        setJwt("");
        navigate("/");
    }

    return (
        <div className="user-page">
            <div className="user-page-text">
                <h1>Welcome {username}</h1>
            </div>

            <button className="logout-btn" onClick={handleLogout}>Logout</button>                        
        </div>
    )
}

export default User