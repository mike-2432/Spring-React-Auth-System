import React, { useContext, useState } from 'react'


// INTERFACES //
interface ContextProps {
    temp: string;
    setTemp(temp: string):void;
}

// CONTEXT //
const AppContext = React.createContext<ContextProps>(null!);
export function useGlobalContext() {
    return useContext(AppContext)
}


// APP PROVIDER //
export function AppProvider({children} : any) {

    // states //
    const [temp, setTemp] = useState("--Temp--");

    // functions //

    // return //
    return (
        <AppContext.Provider value={{
            temp,
            setTemp
        }}> { children } </AppContext.Provider>
    )
}
