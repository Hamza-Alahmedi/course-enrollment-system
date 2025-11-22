@echo off
echo ============================================
echo Starting Course Enrollment System
echo ============================================
echo.

echo Starting Backend (Spring Boot)...
start "Backend Server" cmd /k "cd /d "%~dp0course-enrollment-backend" && mvnw.cmd spring-boot:run"

timeout /t 5 /nobreak >nul

echo Starting Frontend (React)...
start "Frontend Server" cmd /k "cd /d "%~dp0student-frontend" && npm run dev"

echo.
echo ============================================
echo Both servers are starting...
echo Backend: http://localhost:8080
echo Frontend: http://localhost:5173
echo ============================================
echo.
echo Press any key to exit this window (servers will continue running)...
pause >nul

