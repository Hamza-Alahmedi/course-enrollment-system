# Student Dashboard - Setup Instructions

## Prerequisites
- Java 17 or higher
- Node.js 20.x
- Maven

## Running the Application

### 1. Start the Backend (Spring Boot)

Navigate to the course-enrollment-backend directory and run:

```bash
cd course-enrollment-backend
mvnw spring-boot:run
```

Or on Windows:
```bash
cd course-enrollment-backend
mvnw.cmd spring-boot:run
```

The backend will start on `http://localhost:8080`

### 2. Start the Frontend (React + Vite)

Open a new terminal, navigate to the student-frontend directory and run:

```bash
cd student-frontend
npm install
npm run dev
```

The frontend will start on `http://localhost:5173`

### 3. Quick Start (Windows)

Simply double-click the `start-all.bat` file in the root directory to start both servers automatically.

## Project Structure

```
Online Course Enrolment System/
├── course-enrollment-backend/    # Spring Boot backend
│   ├── src/
│   ├── pom.xml
│   └── mvnw.cmd
├── student-frontend/              # React frontend
│   ├── src/
│   ├── package.json
│   └── vite.config.js
└── start-all.bat                  # Quick start script
```

## Features Implemented

### Student Dashboard
- **My Courses Tab**: View all courses the student is enrolled in
- **Browse Courses Tab**: View all available courses and enroll in new ones
- **Search Functionality**: Real-time search by course title or description
- **Category Filtering**: Filter courses by category
- **Student Profile**: Display student name and avatar in navbar
- **Enrollment Management**: Enroll in courses with one click
- **Automatic Login Redirect**: Students logging in are automatically redirected to the React dashboard

### API Endpoints

#### Authentication
- `POST /api/auth/login` - Login and get student ID
- `POST /api/auth/register` - Register new student

#### Student Management
- `GET /api/students/{studentId}` - Get student information
- `GET /api/students/{studentId}/courses` - Get all enrolled courses for a student
- `POST /api/students/{studentId}/enroll/{courseId}` - Enroll in a course
- `DELETE /api/students/{studentId}/unenroll/{courseId}` - Unenroll from a course

#### Courses
- `GET /api/courses` - Get all available courses
- `GET /api/courses/{id}` - Get course by ID
- `GET /api/courses/category/{categoryId}` - Get courses by category

#### Categories
- `GET /api/categories` - Get all categories

## Login Flow

1. Students can log in via the React frontend at `http://localhost:5173`
2. Alternatively, students can log in via the traditional login page at `http://localhost:8080/login`
3. Upon successful login, students are redirected to the React dashboard
4. Student ID is stored in localStorage for session persistence

## Enhanced Features

### Modern UI/UX
- Beautiful gradient design with purple theme
- Responsive layout for all devices
- Smooth animations and transitions
- Toast notifications for user feedback
- Loading states with spinners
- Empty states with helpful messages

### Search & Filter
- Real-time search as you type
- Category dropdown filter
- Combined search and filter functionality
- Results counter showing filtered/total courses
- Clear filters button

### User Experience
- Personalized greeting with student name
- Avatar with student initial
- Course count badges on tabs
- Hover effects on interactive elements
- Professional course cards with category badges
- Enrolled status indicators

## Notes

- CORS is configured to allow requests from `http://localhost:5173`
- The backend uses Spring Security for authentication
- The frontend uses axios for HTTP requests
- Course data includes title, description, category, and instructor information
- All passwords are encrypted using BCrypt
- Session management via localStorage

## Troubleshooting

### Backend won't start
- Ensure Java 17+ is installed: `java -version`
- Check if port 8080 is available
- Verify database connection in `application.properties`

### Frontend won't start
- Ensure Node.js is installed: `node -v`
- Run `npm install` in student-frontend directory
- Check if port 5173 is available

### CORS errors
- Verify backend is running on port 8080
- Check CORS configuration in `CorsConfig.java`
- Ensure frontend is accessing `http://localhost:8080`

## Development

### Backend
- Location: `course-enrollment-backend/`
- Framework: Spring Boot 3.x
- Build tool: Maven
- Java version: 17+

### Frontend
- Location: `student-frontend/`
- Framework: React 18
- Build tool: Vite
- Package manager: npm

## Production Deployment

### Backend
```bash
cd course-enrollment-backend
mvnw clean package
java -jar target/course-enrollment-system-0.0.1-SNAPSHOT.jar
```

### Frontend
```bash
cd student-frontend
npm run build
# Deploy the dist/ folder to your web server
```

