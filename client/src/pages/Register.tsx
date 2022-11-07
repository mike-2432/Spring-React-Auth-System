import React, { useState } from 'react'

// REGISTRATION PAGE //
const Register = () => {
    
    // STATES //
    const [ alertMsg, setAlertMsg ] = useState([false, ""])
    const [ formValues, setFormValues ] = useState({
        email: "",
        username: "",
        password: "",
        repeatedPassword: ""
    })

    const formInputs = [
        {
            id: 1,
            name: "email",
            type: "email",
            placeholder: "Email",
            label: "Email",
            errorMsg: "Please fill in your Email",
        },
        {
            id: 2,
            name: "username",
            type: "text",
            placeholder: "Username",
            label: "Username",
            errorMsg: "Please fill in your Username",
        },
        {
            id: 3,
            name: "password",
            type: "password",
            placeholder: "Password",
            label: "Password",
            errorMsg: "Please fill in your password",
        },
        {
            id: 4,
            name: "repeatedPassword",
            type: "password",
            placeholder: "Repeat Password",
            label: "Repeat Password",
            errorMsg: "Please fill in a matching password",
            pattern: formValues.password,
        }
    ]

    // FUNCTIONS //
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormValues({...formValues, [e.target.id] : e.target.value})
    }

    const handleSubmit = async(e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const response = await fetch("http://localhost:8080/auth/registration", {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                mode: 'cors',
                body: JSON.stringify(formValues)
            });
            const responseData = await response.text();            
            if(response.status!==201) {
                setAlertMsg([true, responseData])
            } else {
                setAlertMsg([true, "verification mail has been send"])
            }
        } catch(err) {
            console.error("Something went wrong...");
        }
    }

    // RETURN //
    return (
        <div className="login-page">
            <div className="login-box">

                <h1>Register</h1>

                <form className="register-form" onSubmit={handleSubmit}> 
                    {formInputs.map((formInput) => {
                        let { id, name, type, placeholder, label, errorMsg, pattern } = formInput;                    
                        return (
                            <div key={id}>
                                <label htmlFor={label}>{label}</label>
                                <input type={type} 
                                    placeholder={placeholder} 
                                    id={name} 
                                    pattern={pattern}
                                    value={formValues[name as keyof typeof formValues] || ''} 
                                    onChange={handleChange} 
                                    required 
                                />
                                <span className="form-err-msg">{errorMsg}</span>
                            </div>                        
                        )
                    })}

                    <button className="submit-btn" type="submit">Register</button>
                    {alertMsg && <div className="failed-register-alert">{alertMsg[1]}</div>}
                </form>   

            </div>
        </div>
    )
}

export default Register