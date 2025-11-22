# Course Enrollment System - REST API Documentation

## Base URL
`http://localhost:8080`

## API Endpoints

### Categories API

#### 1. Get All Categories
- **Endpoint**: `GET /api/categories`
- **Description**: Retrieve all categories with course counts
- **Response**: 
```json
[
  {
    "id": 1,
    "name": "Programming",
    "courseCount": 5
  }
]
```

#### 2. Get Category by ID
- **Endpoint**: `GET /api/categories/{id}`
- **Description**: Retrieve a specific category
- **Response**: 
```json
{
  "id": 1,
  "name": "Programming",
  "courseCount": 5
}
```

#### 3. Create Category
- **Endpoint**: `POST /api/categories`
- **Description**: Create a new category
- **Request Body**: 
```json
{
  "name": "Programming"
}
```
- **Response**: 
```json
{
  "success": true,
  "message": "Category created successfully",
  "data": {
    "id": 1,
    "name": "Programming",
    "courseCount": 0
  }
}
```

#### 4. Update Category
- **Endpoint**: `PUT /api/categories/{id}`
- **Description**: Update an existing category
- **Request Body**: 
```json
{
  "name": "Advanced Programming"
}
```
- **Response**: 
```json
{
  "success": true,
  "message": "Category updated successfully",
  "data": {
    "id": 1,
    "name": "Advanced Programming",
    "courseCount": 5
  }
}
```

#### 5. Delete Category
- **Endpoint**: `DELETE /api/categories/{id}`
- **Description**: Delete a category
- **Response**: 
```json
{
  "success": true,
  "message": "Category deleted successfully"
}
```

---

### Courses API

#### 1. Get All Courses
- **Endpoint**: `GET /api/courses`
- **Description**: Retrieve all courses
- **Response**: 
```json
[
  {
    "id": 1,
    "title": "Java Programming",
    "description": "Learn Java from scratch",
    "instructorApiId": "inst123",
    "categoryId": 1,
    "categoryName": "Programming"
  }
]
```

#### 2. Get Course by ID
- **Endpoint**: `GET /api/courses/{id}`
- **Description**: Retrieve a specific course
- **Response**: 
```json
{
  "id": 1,
  "title": "Java Programming",
  "description": "Learn Java from scratch",
  "instructorApiId": "inst123",
  "categoryId": 1,
  "categoryName": "Programming"
}
```

#### 3. Get Courses by Category
- **Endpoint**: `GET /api/courses/category/{categoryId}`
- **Description**: Retrieve all courses in a specific category
- **Response**: 
```json
[
  {
    "id": 1,
    "title": "Java Programming",
    "description": "Learn Java from scratch",
    "instructorApiId": "inst123",
    "categoryId": 1,
    "categoryName": "Programming"
  }
]
```

#### 4. Create Course
- **Endpoint**: `POST /api/courses`
- **Description**: Create a new course
- **Request Body**: 
```json
{
  "title": "Java Programming",
  "description": "Learn Java from scratch",
  "instructorApiId": "inst123",
  "categoryId": 1
}
```
- **Response**: 
```json
{
  "success": true,
  "message": "Course created successfully",
  "data": {
    "id": 1,
    "title": "Java Programming",
    "description": "Learn Java from scratch",
    "instructorApiId": "inst123",
    "categoryId": 1,
    "categoryName": "Programming"
  }
}
```

#### 5. Update Course
- **Endpoint**: `PUT /api/courses/{id}`
- **Description**: Update an existing course
- **Request Body**: 
```json
{
  "title": "Advanced Java Programming",
  "description": "Master Java concepts",
  "instructorApiId": "inst123",
  "categoryId": 1
}
```
- **Response**: 
```json
{
  "success": true,
  "message": "Course updated successfully",
  "data": {
    "id": 1,
    "title": "Advanced Java Programming",
    "description": "Master Java concepts",
    "instructorApiId": "inst123",
    "categoryId": 1,
    "categoryName": "Programming"
  }
}
```

#### 6. Delete Course
- **Endpoint**: `DELETE /api/courses/{id}`
- **Description**: Delete a course
- **Response**: 
```json
{
  "success": true,
  "message": "Course deleted successfully"
}
```

---

## Error Responses

### Validation Error
```json
{
  "success": false,
  "message": "Validation failed",
  "errors": {
    "title": "Course title is required",
    "categoryId": "Category ID is required"
  }
}
```

### Not Found Error
```json
{
  "success": false,
  "message": "Course not found with id: 1"
}
```

### General Error
```json
{
  "success": false,
  "message": "An unexpected error occurred"
}
```

---

## CORS Configuration

The API is configured to accept requests from:
- `http://localhost:3000` (React dev server)
- `http://localhost:3001` (Alternative React port)

Allowed methods: `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`, `PATCH`

Credentials are allowed for authenticated requests.

---

## Testing with cURL

### Get All Categories
```bash
curl -X GET http://localhost:8080/api/categories
```

### Create a Category
```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{"name":"Programming"}'
```

### Get All Courses
```bash
curl -X GET http://localhost:8080/api/courses
```

### Create a Course
```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "title":"Java Programming",
    "description":"Learn Java from scratch",
    "instructorApiId":"inst123",
    "categoryId":1
  }'
```

### Update a Course
```bash
curl -X PUT http://localhost:8080/api/courses/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title":"Advanced Java",
    "description":"Master Java",
    "instructorApiId":"inst123",
    "categoryId":1
  }'
```

### Delete a Course
```bash
curl -X DELETE http://localhost:8080/api/courses/1
```

---

## React Integration Example

### Fetch All Courses
```javascript
const fetchCourses = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/courses');
    const data = await response.json();
    console.log(data);
  } catch (error) {
    console.error('Error fetching courses:', error);
  }
};
```

### Create a Course
```javascript
const createCourse = async (courseData) => {
  try {
    const response = await fetch('http://localhost:8080/api/courses', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(courseData),
    });
    const data = await response.json();
    if (data.success) {
      console.log('Course created:', data.data);
    } else {
      console.error('Error:', data.message);
    }
  } catch (error) {
    console.error('Error creating course:', error);
  }
};

// Usage
createCourse({
  title: 'Java Programming',
  description: 'Learn Java from scratch',
  instructorApiId: 'inst123',
  categoryId: 1
});
```

### Using Axios
```javascript
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Get all courses
const getCourses = () => api.get('/courses');

// Create a course
const createCourse = (data) => api.post('/courses', data);

// Update a course
const updateCourse = (id, data) => api.put(`/courses/${id}`, data);

// Delete a course
const deleteCourse = (id) => api.delete(`/courses/${id}`);

// Export for use in components
export { getCourses, createCourse, updateCourse, deleteCourse };
```

---

## Notes

1. All API endpoints are accessible without authentication (configured in SecurityConfig)
2. The API uses standard HTTP status codes:
   - 200 OK - Successful GET, PUT, DELETE
   - 201 Created - Successful POST
   - 400 Bad Request - Validation errors or business logic errors
   - 404 Not Found - Resource not found
   - 500 Internal Server Error - Unexpected errors

3. All POST and PUT responses include a wrapper with:
   - `success`: Boolean indicating success/failure
   - `message`: Human-readable message
   - `data`: The actual data (for successful operations)
   - `errors`: Validation errors (for failed operations)

4. The API is fully CORS-enabled for React development

