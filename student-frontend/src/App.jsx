import { useState, useEffect } from 'react'
import StudentDashboard from './components/StudentDashboard'
import './App.css'

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [userRole, setUserRole] = useState(null);

  useEffect(() => {
    // Check if user is already logged in
    const studentId = localStorage.getItem('studentId');
    const role = localStorage.getItem('userRole');

    // Check if redirected from backend login with studentId in URL
    const urlParams = new URLSearchParams(window.location.search);
    const studentIdFromUrl = urlParams.get('studentId');

    if (studentIdFromUrl) {
      localStorage.setItem('studentId', studentIdFromUrl);
      // Backend redirect doesn't include role, default to STUDENT
      localStorage.setItem('userRole', 'STUDENT');
      setUserRole('STUDENT');
      setIsLoggedIn(true);
      // Clean up URL
      window.history.replaceState({}, document.title, window.location.pathname);
    } else if (studentId && role) {
      setUserRole(role);
      setIsLoggedIn(true);
    }

    setIsLoading(false);
  }, []);

  if (isLoading) {
    return <div className="loading-container">Loading...</div>;
  }

  if (isLoggedIn) {
    // If admin logged in from frontend, redirect to backend admin dashboard
    if (userRole === 'ADMIN') {
      window.location.href = 'https://course-enrollment-system-dxav.onrender.com/admin/dashboard';
      return <div className="loading-container">Redirecting to Admin Dashboard...</div>;
    }
    return <StudentDashboard />;
  }

  return <LoginPage onLogin={(role) => {
    setUserRole(role);
    setIsLoggedIn(true);
  }} />;
}

function LoginPage({ onLogin }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const response = await fetch('https://course-enrollment-system-dxav.onrender.com/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include', // Important: send cookies for session
        body: JSON.stringify({ email, password })
      });

      if (response.ok) {
        const data = await response.json();

        // Store user data
        localStorage.setItem('studentId', data.studentId || data.id);
        localStorage.setItem('userEmail', data.email);
        localStorage.setItem('userRole', data.role);
        localStorage.setItem('username', data.username);

        // Store JWT token
        if (data.token) {
          localStorage.setItem('token', data.token);
        }

        // Check if user is ADMIN
        if (data.role === 'ADMIN') {
          // Redirect admin to backend admin dashboard
          window.location.href = 'https://course-enrollment-system-dxav.onrender.com/admin/dashboard';
          return;
        }

        // For STUDENT role, call onLogin callback
        onLogin(data.role);
      } else {
        const errorData = await response.json();
        setError(errorData.message || 'Login failed. Please check your credentials.');
      }
    } catch (error) {
      console.error('Error during login:', error);
      setError('Error connecting to server. Please try again.');
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h1>Course Enrollment System</h1>
        <form onSubmit={handleLogin}>
          {error && <div className="error-message">{error}</div>}
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              id="email"
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              id="password"
              type="password"
              placeholder="Enter your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="login-btn">Login</button>
        </form>
        <p className="register-link">
          Don't have an account? <a href="https://course-enrollment-system-dxav.onrender.com/register">Register here</a>
        </p>
      </div>
    </div>
  );
}

export default App

