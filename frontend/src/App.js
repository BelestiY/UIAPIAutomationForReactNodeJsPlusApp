import React, { useState, useEffect } from 'react';
import Login from './Login';
import TodoApp from './TodoApp';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    // Check login status on app load
    fetch('http://localhost:5000/api/check-auth', {
      credentials: 'include'
    })
      .then(res => {
        if (res.ok) setIsLoggedIn(true);
      })
      .catch(() => setIsLoggedIn(false));
  }, []);

  const handleLogin = () => {
    setIsLoggedIn(true);
  };

  const handleLogout = () => {
    fetch('http://localhost:5000/api/logout', {
      method: 'POST',
      credentials: 'include'
    }).finally(() => setIsLoggedIn(false));
  };

  return (
    <div className="App">
      {isLoggedIn ? (
        <TodoApp onLogout={handleLogout} />
      ) : (
        <Login onLogin={handleLogin} />
      )}
    </div>
  );
}

export default App;
