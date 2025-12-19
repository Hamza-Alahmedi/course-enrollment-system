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
    } else {
      // No logged in user - redirect to backend login page
      window.location.href = 'https://course-enrollment-system-dxav.onrender.com/login';
      return;
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

  // If not logged in, show redirecting message (should not reach here as useEffect redirects)
  return <div className="loading-container">Redirecting to login...</div>;
}

export default App

