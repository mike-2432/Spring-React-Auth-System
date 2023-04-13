import React, { useContext, useEffect, useState } from 'react';
import useLocalStorage from './useLocalStorage';
import URL from './URL';

// INTERFACES //
interface ContextProps {
  jwt: string;
  setJwt(jwt: string):void;
  isSidebarOpen: Boolean;
  toggleSidebar: React.MouseEventHandler;
}

// CONTEXT //
const AppContext = React.createContext<ContextProps>(null!);
export function useGlobalContext() {
  return useContext(AppContext)
}

// APP PROVIDER //
export function AppProvider({children} : any) {
  // jwt token control //
  // ================  //
  const [jwt, setJwt] = useLocalStorage("jwt","");
  useEffect(() => {
    const checkStatus = async() => {
      try {
        const response = await fetch(URL+"/api/user/allowed", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer "+jwt,
          }                
        });                
        if(response.status!==200) setJwt("");
      } catch(err) {
        setJwt("");
        console.log(err);
      }            
    }
    checkStatus();
  }, [jwt, setJwt]);

  // Navbar / Sidebar  //
  // ================  //
  const [ isSidebarOpen, setIsSidebarOpen] = useState(false);
  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  }

  // Return //
  return (
    <AppContext.Provider value={{
      jwt,
      setJwt,
      isSidebarOpen,
      toggleSidebar,
    }}> { children } </AppContext.Provider>
  )
}
