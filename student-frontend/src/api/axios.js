import axios from 'axios';

// Use environment variable for API URL, fallback to production backend
const API_URL = import.meta.env.VITE_API_URL || 'https://course-enrollment-system-dxav.onrender.com';

// Create axios instance with default config
const axiosInstance = axios.create({
  baseURL: API_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add request interceptor for debugging and JWT token
axiosInstance.interceptors.request.use(
  (config) => {
    console.log('Making request to:', config.url);

    // Add JWT token to headers if it exists
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add response interceptor for error handling
axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response?.status === 401) {
      console.error('Unauthorized - Please login');
      // Optionally redirect to login
      // window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;

