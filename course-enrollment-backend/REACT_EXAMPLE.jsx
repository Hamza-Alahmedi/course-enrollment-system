// React Component Example for Course Enrollment System REST API
// Save this as: CourseList.jsx

import React, { useState, useEffect } from 'react';
import axios from 'axios';

const API_BASE = 'http://localhost:8080/api';

// Course List Component
const CourseList = () => {
  const [courses, setCourses] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [showForm, setShowForm] = useState(false);

  const [formData, setFormData] = useState({
    title: '',
    description: '',
    instructorApiId: '',
    categoryId: ''
  });

  // Fetch courses on component mount
  useEffect(() => {
    fetchCourses();
    fetchCategories();
  }, []);

  const fetchCourses = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`${API_BASE}/courses`);
      setCourses(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch courses: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await axios.get(`${API_BASE}/categories`);
      setCategories(response.data);
    } catch (err) {
      console.error('Failed to fetch categories:', err);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const response = await axios.post(`${API_BASE}/courses`, {
        ...formData,
        categoryId: parseInt(formData.categoryId)
      });

      if (response.data.success) {
        alert(response.data.message);
        setFormData({
          title: '',
          description: '',
          instructorApiId: '',
          categoryId: ''
        });
        setShowForm(false);
        fetchCourses(); // Refresh the list
      }
    } catch (err) {
      setError('Failed to create course: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this course?')) {
      return;
    }

    try {
      const response = await axios.delete(`${API_BASE}/courses/${id}`);
      if (response.data.success) {
        alert(response.data.message);
        fetchCourses(); // Refresh the list
      }
    } catch (err) {
      setError('Failed to delete course: ' + err.message);
    }
  };

  return (
    <div style={styles.container}>
      <h1 style={styles.title}>Course Management</h1>

      {error && <div style={styles.error}>{error}</div>}

      <button
        onClick={() => setShowForm(!showForm)}
        style={styles.button}
      >
        {showForm ? 'Cancel' : 'Add New Course'}
      </button>

      {/* Create Course Form */}
      {showForm && (
        <form onSubmit={handleSubmit} style={styles.form}>
          <h2>Create New Course</h2>

          <div style={styles.formGroup}>
            <label style={styles.label}>Title:</label>
            <input
              type="text"
              name="title"
              value={formData.title}
              onChange={handleInputChange}
              required
              style={styles.input}
            />
          </div>

          <div style={styles.formGroup}>
            <label style={styles.label}>Description:</label>
            <textarea
              name="description"
              value={formData.description}
              onChange={handleInputChange}
              rows="4"
              style={styles.textarea}
            />
          </div>

          <div style={styles.formGroup}>
            <label style={styles.label}>Instructor API ID (optional):</label>
            <input
              type="text"
              name="instructorApiId"
              value={formData.instructorApiId}
              onChange={handleInputChange}
              style={styles.input}
            />
          </div>

          <div style={styles.formGroup}>
            <label style={styles.label}>Category:</label>
            <select
              name="categoryId"
              value={formData.categoryId}
              onChange={handleInputChange}
              required
              style={styles.select}
            >
              <option value="">Select a category</option>
              {categories.map(cat => (
                <option key={cat.id} value={cat.id}>
                  {cat.name}
                </option>
              ))}
            </select>
          </div>

          <button type="submit" style={styles.button} disabled={loading}>
            {loading ? 'Creating...' : 'Create Course'}
          </button>
        </form>
      )}

      {/* Course List */}
      <div style={styles.courseList}>
        <h2>All Courses</h2>

        {loading && <p>Loading...</p>}

        {!loading && courses.length === 0 && (
          <p>No courses found. Create one to get started!</p>
        )}

        {!loading && courses.map(course => (
          <div key={course.id} style={styles.courseCard}>
            <div style={styles.courseHeader}>
              <h3 style={styles.courseTitle}>{course.title}</h3>
              <span style={styles.badge}>{course.categoryName}</span>
            </div>

            <p style={styles.courseDescription}>{course.description}</p>

            {course.instructorApiId && (
              <p style={styles.instructorId}>
                Instructor: {course.instructorApiId}
              </p>
            )}

            <button
              onClick={() => handleDelete(course.id)}
              style={styles.deleteButton}
            >
              Delete
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

// Inline styles
const styles = {
  container: {
    maxWidth: '1200px',
    margin: '0 auto',
    padding: '20px',
    fontFamily: 'Arial, sans-serif'
  },
  title: {
    color: '#333',
    marginBottom: '20px'
  },
  button: {
    padding: '12px 24px',
    background: '#667eea',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    fontSize: '16px',
    cursor: 'pointer',
    marginBottom: '20px'
  },
  deleteButton: {
    padding: '8px 16px',
    background: '#dc3545',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    fontSize: '14px',
    cursor: 'pointer',
    marginTop: '10px'
  },
  form: {
    background: '#f8f9fa',
    padding: '20px',
    borderRadius: '8px',
    marginBottom: '30px'
  },
  formGroup: {
    marginBottom: '15px'
  },
  label: {
    display: 'block',
    marginBottom: '5px',
    fontWeight: 'bold',
    color: '#555'
  },
  input: {
    width: '100%',
    padding: '10px',
    border: '2px solid #ddd',
    borderRadius: '5px',
    fontSize: '14px'
  },
  textarea: {
    width: '100%',
    padding: '10px',
    border: '2px solid #ddd',
    borderRadius: '5px',
    fontSize: '14px',
    fontFamily: 'inherit'
  },
  select: {
    width: '100%',
    padding: '10px',
    border: '2px solid #ddd',
    borderRadius: '5px',
    fontSize: '14px'
  },
  error: {
    background: '#f8d7da',
    color: '#721c24',
    padding: '15px',
    borderRadius: '5px',
    marginBottom: '20px',
    borderLeft: '4px solid #dc3545'
  },
  courseList: {
    marginTop: '30px'
  },
  courseCard: {
    background: 'white',
    border: '1px solid #ddd',
    borderRadius: '8px',
    padding: '20px',
    marginBottom: '15px',
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)'
  },
  courseHeader: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '10px'
  },
  courseTitle: {
    margin: 0,
    color: '#667eea'
  },
  badge: {
    background: '#667eea',
    color: 'white',
    padding: '5px 15px',
    borderRadius: '20px',
    fontSize: '12px',
    fontWeight: 'bold'
  },
  courseDescription: {
    color: '#666',
    lineHeight: '1.5'
  },
  instructorId: {
    color: '#999',
    fontSize: '14px',
    fontStyle: 'italic'
  }
};

export default CourseList;


// ============================================
// API Service File (Optional - Better Approach)
// Save this as: services/api.js
// ============================================

/*
import axios from 'axios';

const API_BASE = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Course API
export const courseAPI = {
  getAll: () => api.get('/courses'),
  getById: (id) => api.get(`/courses/${id}`),
  getByCategory: (categoryId) => api.get(`/courses/category/${categoryId}`),
  create: (data) => api.post('/courses', data),
  update: (id, data) => api.put(`/courses/${id}`, data),
  delete: (id) => api.delete(`/courses/${id}`)
};

// Category API
export const categoryAPI = {
  getAll: () => api.get('/categories'),
  getById: (id) => api.get(`/categories/${id}`),
  create: (data) => api.post('/categories', data),
  update: (id, data) => api.put(`/categories/${id}`, data),
  delete: (id) => api.delete(`/categories/${id}`)
};

// Usage in components:
// import { courseAPI, categoryAPI } from './services/api';
//
// const fetchCourses = async () => {
//   const response = await courseAPI.getAll();
//   setCourses(response.data);
// };
*/

