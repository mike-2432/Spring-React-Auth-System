import React, { useState } from 'react'

// LOGIN PAGE //
const Login = () => {

    // STATES //
    const [ formValues, setFormValues ] = useState({
        username: "",
        password: "",
    })

    const formInputs = [
        {
            id: 1,
            name: "username",
            type: "text",
            placeholder: "Username",
            label: "Username",
        },
        {
            id: 2,
            name: "password",
            type: "text",
            placeholder: "Password",
            label: "Password",
        }
    ]


    // FUNCTIONS //
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormValues({...formValues, [e.target.id] : e.target.value})
    }

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
    }


    // RETURN //
    return (
        <>
            <h1>Login</h1>
            
            <form onSubmit={handleSubmit}> 

                {formInputs.map((formInput) => {
                    let { id, name, type, placeholder, label } = formInput;                    
                    return (
                        <div key={id}>
                            <label htmlFor={label}>{label}</label>
                            <input type={type} 
                                placeholder={placeholder} 
                                id={name} 
                                value={formValues[name as keyof typeof formValues] || ''} 
                                onChange={handleChange} 
                                required 
                            />
                        </div>                        
                    )
                })}

                <button type="submit">Lopin</button>
            </form>   

        </>
    )
}

export default Login