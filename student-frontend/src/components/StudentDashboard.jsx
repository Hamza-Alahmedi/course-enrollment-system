import { useState, useEffect, useCallback } from 'react';
import axios from '../api/axios';
import './StudentDashboard.css';

function StudentDashboard() {
  const [studentInfo, setStudentInfo] = useState(null);
  const [myCourses, setMyCourses] = useState([]);
  const [availableCourses, setAvailableCourses] = useState([]);
  const [filteredCourses, setFilteredCourses] = useState([]);
  const [categories, setCategories] = useState([]);
  const [activeTab, setActiveTab] = useState('browse');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [courseRatings, setCourseRatings] = useState({});
  const [userRatings, setUserRatings] = useState({});
  const studentId = localStorage.getItem('studentId');

  const fetchStudentInfo = useCallback(async () => {
    try {
      const response = await axios.get(`/api/students/${studentId}`);
      setStudentInfo(response.data);
    } catch (err) {
      console.error('Failed to fetch student info:', err);
    }
  }, [studentId]);

  const fetchCategories = useCallback(async () => {
    try {
      const response = await axios.get('/api/categories');
      setCategories(response.data);
    } catch (err) {
      console.error('Failed to fetch categories:', err);
    }
  }, []);

  const fetchMyCourses = useCallback(async () => {
    setLoading(true);
    try {
      const response = await axios.get(`/api/students/${studentId}/courses`);
      setMyCourses(response.data);

      // Fetch ratings for enrolled courses
      response.data.forEach(course => {
        fetchCourseRating(course.id);
        fetchUserRating(course.id);
      });

      setError('');
    } catch (err) {
      setError('Failed to fetch your courses');
      console.error(err);
    } finally {
      setLoading(false);
    }
  }, [studentId]);

  const fetchAvailableCourses = useCallback(async () => {
    setLoading(true);
    try {
      const response = await axios.get('/api/courses');
      setAvailableCourses(response.data);
      setFilteredCourses(response.data);

      // Fetch average ratings for all courses
      response.data.forEach(course => {
        fetchCourseRating(course.id);
      });

      setError('');
    } catch (err) {
      setError('Failed to fetch available courses');
      console.error(err);
    } finally {
      setLoading(false);
    }
  }, []);

  const fetchCourseRating = async (courseId) => {
    try {
      const response = await axios.get(`/api/courses/${courseId}/rating/avg`);
      setCourseRatings(prev => ({
        ...prev,
        [courseId]: response.data.averageRating || 0
      }));
    } catch (err) {
      console.error(`Failed to fetch rating for course ${courseId}:`, err);
    }
  };

  const fetchUserRating = async (courseId) => {
    try {
      const response = await axios.get(`/api/courses/${courseId}/rating/me`);
      if (response.data.rating) {
        setUserRatings(prev => ({
          ...prev,
          [courseId]: response.data.rating
        }));
      }
    } catch (err) {
      console.error(`Failed to fetch user rating for course ${courseId}:`, err);
    }
  };

  const submitRating = async (courseId, rating) => {
    try {
      await axios.post(`/api/courses/${courseId}/rating`, { rating });

      // Update local state
      setUserRatings(prev => ({
        ...prev,
        [courseId]: rating
      }));

      // Refresh course rating
      await fetchCourseRating(courseId);

      showNotification('Rating submitted successfully!', 'success');
    } catch (err) {
      showNotification(err.response?.data || 'Failed to submit rating', 'error');
      console.error(err);
    }
  };

  useEffect(() => {
    fetchStudentInfo();
    fetchCategories();
    fetchMyCourses();
    fetchAvailableCourses();
  }, [fetchStudentInfo, fetchCategories, fetchMyCourses, fetchAvailableCourses]);

  // Filter courses based on search term and category
  useEffect(() => {
    let filtered = availableCourses;

    // Filter by search term
    if (searchTerm) {
      filtered = filtered.filter(course =>
        course.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        course.description.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }

    // Filter by category
    if (selectedCategory !== 'all') {
      filtered = filtered.filter(course => course.categoryId === parseInt(selectedCategory));
    }

    setFilteredCourses(filtered);
  }, [searchTerm, selectedCategory, availableCourses]);

  const enrollInCourse = async (courseId, courseTitle) => {
    try {
      await axios.post(`/api/students/${studentId}/enroll/${courseId}`);
      await fetchMyCourses();
      await fetchAvailableCourses();

      // Show success notification
      showNotification(`Successfully enrolled in "${courseTitle}"!`, 'success');
    } catch (err) {
      showNotification('Failed to enroll in course. Please try again.', 'error');
      console.error(err);
    }
  };

  const isEnrolled = (courseId) => {
    return myCourses.some(course => course.id === courseId);
  };

  const handleLogout = async () => {
    try {
      // Call logout API to invalidate session on backend
      await axios.post('/api/auth/logout');
    } catch (error) {
      console.error('Logout error:', error);
    } finally {
      // Clear local storage and redirect to backend login page
      localStorage.clear();
      window.location.href = 'https://course-enrollment-system-dxav.onrender.com/login';
    }
  };

  const showNotification = (message, type) => {
    // Simple notification - you can enhance this with a proper notification library
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    document.body.appendChild(notification);

    setTimeout(() => {
      notification.classList.add('show');
    }, 100);

    setTimeout(() => {
      notification.classList.remove('show');
      setTimeout(() => notification.remove(), 300);
    }, 3000);
  };

  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
  };

  const handleCategoryChange = (e) => {
    setSelectedCategory(e.target.value);
  };

  const clearFilters = () => {
    setSearchTerm('');
    setSelectedCategory('all');
  };

  const StarRating = ({ courseId, isEnrolled, averageRating, userRating }) => {
    const [hoveredRating, setHoveredRating] = useState(0);

    const handleStarClick = (rating) => {
      if (isEnrolled) {
        submitRating(courseId, rating);
      }
    };

    const displayRating = hoveredRating || userRating || 0;

    return (
      <div className="star-rating-container">
        <div className="stars">
          {[1, 2, 3, 4, 5].map((star) => (
            <span
              key={star}
              className={`star ${star <= displayRating ? 'filled' : ''} ${isEnrolled ? 'interactive' : ''}`}
              onMouseEnter={() => isEnrolled && setHoveredRating(star)}
              onMouseLeave={() => isEnrolled && setHoveredRating(0)}
              onClick={() => handleStarClick(star)}
              style={{ cursor: isEnrolled ? 'pointer' : 'default' }}
            >
              ★
            </span>
          ))}
        </div>
        <div className="rating-info">
          {averageRating > 0 ? (
            <span className="avg-rating">{averageRating.toFixed(1)}</span>
          ) : (
            <span className="no-rating">No ratings yet</span>
          )}
          {isEnrolled && userRating > 0 && (
            <span className="user-rating-indicator">Your rating: {userRating}★</span>
          )}
        </div>
      </div>
    );
  };

  return (
    <div className="dashboard-wrapper">
      {/* Top Navigation Bar */}
      <nav className="top-navbar">
        <div className="navbar-content">
          <div className="navbar-left">
            <h1 className="app-title">📚 Course Hub</h1>
          </div>
          <div className="navbar-right">
            <div className="user-info">
              <div className="user-avatar">
                {studentInfo?.username ? studentInfo.username.charAt(0).toUpperCase() : 'S'}
              </div>
              <div className="user-details">
                <span className="user-name">{studentInfo?.username || 'Student'}</span>
                <span className="user-role">Student</span>
              </div>
            </div>
            <button onClick={handleLogout} className="logout-btn">
              <span className="logout-icon">🚪</span> Logout
            </button>
          </div>
        </div>
      </nav>

      <div className="dashboard-container">
        {/* Welcome Section */}
        <div className="welcome-section">
          <h2>Welcome back, {studentInfo?.username || 'Student'}! 👋</h2>
          <p className="welcome-subtitle">
            {activeTab === 'my-courses'
              ? `You're enrolled in ${myCourses.length} ${myCourses.length === 1 ? 'course' : 'courses'}`
              : `Explore ${availableCourses.length} available courses`
            }
          </p>
        </div>

        {/* Tabs Navigation */}
        <div className="dashboard-tabs">
          <button
            className={activeTab === 'browse' ? 'tab active' : 'tab'}
            onClick={() => setActiveTab('browse')}
          >
            <span className="tab-icon">🔍</span>
            Browse Courses
            <span className="tab-badge">{availableCourses.length}</span>
          </button>
          <button
            className={activeTab === 'my-courses' ? 'tab active' : 'tab'}
            onClick={() => setActiveTab('my-courses')}
          >
            <span className="tab-icon">📖</span>
            My Courses
            <span className="tab-badge">{myCourses.length}</span>
          </button>
        </div>

        {error && <div className="error-message">⚠️ {error}</div>}

        {/* Browse Courses Tab */}
        {activeTab === 'browse' && (
          <div className="courses-section">
            {/* Search and Filter Section */}
            <div className="filters-section">
              <div className="search-box">
                <span className="search-icon">🔍</span>
                <input
                  type="text"
                  placeholder="Search courses by title or description..."
                  value={searchTerm}
                  onChange={handleSearchChange}
                  className="search-input"
                />
                {searchTerm && (
                  <button className="clear-search" onClick={() => setSearchTerm('')}>
                    ✕
                  </button>
                )}
              </div>

              <div className="filter-controls">
                <div className="filter-group">
                  <label htmlFor="category-filter">Category:</label>
                  <select
                    id="category-filter"
                    value={selectedCategory}
                    onChange={handleCategoryChange}
                    className="category-select"
                  >
                    <option value="all">All Categories</option>
                    {categories.map(category => (
                      <option key={category.id} value={category.id}>
                        {category.name}
                      </option>
                    ))}
                  </select>
                </div>

                {(searchTerm || selectedCategory !== 'all') && (
                  <button className="clear-filters-btn" onClick={clearFilters}>
                    Clear Filters
                  </button>
                )}

                <div className="results-count">
                  Showing {filteredCourses.length} of {availableCourses.length} courses
                </div>
              </div>
            </div>

            {/* Courses Grid */}
            {loading ? (
              <div className="loading-spinner">
                <div className="spinner"></div>
                <p>Loading courses...</p>
              </div>
            ) : filteredCourses.length === 0 ? (
              <div className="empty-state">
                <div className="empty-icon">🔍</div>
                <h3>No courses found</h3>
                <p>Try adjusting your search or filter criteria</p>
                {(searchTerm || selectedCategory !== 'all') && (
                  <button className="reset-btn" onClick={clearFilters}>
                    Clear all filters
                  </button>
                )}
              </div>
            ) : (
              <div className="courses-grid">
                {filteredCourses.map(course => (
                  <div key={course.id} className="course-card">
                    <div className="course-card-header">
                      <span className="course-category-badge">{course.categoryName}</span>
                    </div>
                    <h3 className="course-title">{course.title}</h3>
                    <p className="course-description">{course.description}</p>
                    <StarRating
                      courseId={course.id}
                      isEnrolled={isEnrolled(course.id)}
                      averageRating={courseRatings[course.id] || 0}
                      userRating={userRatings[course.id] || 0}
                    />
                    <div className="course-footer">
                      {isEnrolled(course.id) ? (
                        <button className="enrolled-btn" disabled>
                          ✓ Already Enrolled
                        </button>
                      ) : (
                        <button
                          className="enroll-btn"
                          onClick={() => enrollInCourse(course.id, course.title)}
                        >
                          + Enroll Now
                        </button>
                      )}
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        )}

        {/* My Courses Tab */}
        {activeTab === 'my-courses' && (
          <div className="courses-section">
            {loading ? (
              <div className="loading-spinner">
                <div className="spinner"></div>
                <p>Loading your courses...</p>
              </div>
            ) : myCourses.length === 0 ? (
              <div className="empty-state">
                <div className="empty-icon">📚</div>
                <h3>No courses enrolled yet</h3>
                <p>Start your learning journey by enrolling in courses!</p>
                <button
                  className="browse-btn"
                  onClick={() => setActiveTab('browse')}
                >
                  Browse Courses
                </button>
              </div>
            ) : (
              <div className="courses-grid">
                {myCourses.map(course => (
                  <div key={course.id} className="course-card enrolled-course">
                    <div className="course-card-header">
                      <span className="course-category-badge">{course.categoryName}</span>
                      <span className="enrolled-badge">✓ Enrolled</span>
                    </div>
                    <h3 className="course-title">{course.title}</h3>
                    <p className="course-description">{course.description}</p>
                    <div className="rating-section">
                      <p className="rating-label">Rate this course:</p>
                      <StarRating
                        courseId={course.id}
                        isEnrolled={true}
                        averageRating={courseRatings[course.id] || 0}
                        userRating={userRatings[course.id] || 0}
                      />
                    </div>
                    <div className="course-footer">
                      <div className="course-meta">
                        <span className="meta-item">
                          📅 Enrolled
                        </span>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default StudentDashboard;

