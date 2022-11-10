import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useGlobalContext } from '../Context';
import URL from '../URL';
import ChangePass from '../components/ChangePassword';

// USER PAGE //
const User = () => {
    const navigate = useNavigate();
    const [ username, setUsername] = useState("");
    const [ email, setEmail ] = useState("");
    const { jwt, setJwt } = useGlobalContext();

    // FUNCTIONS //
    useEffect(() => {
        const getUserDetails = async() => {
            const response = await fetch(URL+"/api/user/getDetails", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer "+jwt,
                }                
            });     
            const details = await response.json();
            if(details) {
                setUsername(details.username);
                setEmail(details.email);
            }
        }
        getUserDetails();
    },[jwt])

    const handleLogout = () => {
        setJwt("");
        navigate("/");
    }

    // RETURN //
    return (
        <div className="page-container">
            <div className="user-page-text-box">
                <h1>Welcome {username}</h1>
                <button className="logout-btn" onClick={handleLogout}>Logout</button>   
            </div>

            <div className="user-page-change-pass">
                <ChangePass email={email}/>
            </div>                                 
        </div>
    )
}

export default User