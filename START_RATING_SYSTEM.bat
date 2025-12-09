@echo off
echo ========================================
echo   COURSE RATING SYSTEM - QUICK START
echo ========================================
echo.

echo This will start both Backend and Frontend
echo Backend: http://localhost:8080
echo Frontend: http://localhost:5173
echo.
echo Press any key to continue...
pause >nul

echo.
echo [1/2] Starting Backend (Spring Boot)...
echo This will open in a new window.
echo Wait for "Started CourseEnrollmentSystemApplication"
echo.

start "Backend - Course Enrollment" cmd /k "cd /d "%~dp0course-enrollment-backend" && mvnw.cmd spring-boot:run"

echo Waiting 10 seconds for backend to initialize...
timeout /t 10 /nobreak >nul

echo.
echo [2/2] Starting Frontend (React + Vite)...
echo This will open in a new window.
echo.

start "Frontend - Student Portal" cmd /k "cd /d "%~dp0student-frontend" && npm run dev"

echo.
echo ========================================
echo   BOTH APPLICATIONS STARTING!
echo ========================================
echo.
echo Backend Terminal: Check window titled "Backend - Course Enrollment"
echo Frontend Terminal: Check window titled "Frontend - Student Portal"
echo.
echo Once both are started:
echo   - Backend: http://localhost:8080
echo   - Frontend: http://localhost:5173
echo   - Test Page: http://localhost:8080/rating-test.html
echo.
echo ========================================
echo   USEFUL LINKS
echo ========================================
echo.
echo Student Dashboard: http://localhost:5173
echo Login Page: http://localhost:8080/login
echo Rating Test: http://localhost:8080/rating-test.html
echo Admin Portal: http://localhost:8080/admin/home
echo.
echo To stop the applications:
echo   - Close both terminal windows
echo   - Or press Ctrl+C in each window
echo.
echo ========================================
echo   TESTING INSTRUCTIONS
echo ========================================
echo.
echo 1. Wait for both applications to start (30-60 seconds)
echo 2. Open http://localhost:5173 in your browser
echo 3. Login with your student credentials
echo 4. Enroll in a course from "Browse Courses" tab
echo 5. Go to "My Courses" tab
echo 6. Click stars to rate the course
echo 7. See your rating appear with "Your rating: X★"
echo 8. Verify average rating updates
echo.
echo For detailed testing, see TESTING_GUIDE.md
echo.
echo Press any key to exit this window...
pause >nul

