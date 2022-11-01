import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AppProvider } from './Context'

// PAGES //
import Home from './pages/Home'
import Login from './pages/Login'
import Register from './pages/Register'

// COMPONENTS //


// RETURN //
export default function App() {
  return (
    <BrowserRouter>
      <AppProvider>

        {/* Components */}
        

        {/* Routes */}    
        <Routes>
          <Route path="/" element={ <Home /> }/>
          <Route path="/login" element={ <Login /> } />
          <Route path="/register" element={ <Register /> } />
        </Routes>

      </AppProvider>      
    </BrowserRouter>
  )
}