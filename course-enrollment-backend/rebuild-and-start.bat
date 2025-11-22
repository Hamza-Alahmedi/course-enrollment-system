@echo off
echo ========================================
echo   COURSE ENROLLMENT SYSTEM - REBUILD
echo ========================================
echo.

cd /d "C:\Users\hamza\OneDrive\Desktop\Online Course Enrolment System\Course Enrollment System"

echo [1/4] Stopping any running instances...
taskkill /F /IM java.exe 2>nul
timeout /t 2 /nobreak >nul

echo [2/4] Cleaning old build...
call mvn clean
if %errorlevel% neq 0 (
    echo ERROR: Maven clean failed!
    pause
    exit /b 1
)
k
echo [3/4] Compiling project...
call mvn compile -DskipTests
if %errorlevel% neq 0 (
    echo ERROR: Maven compile failed!
    pause
    exit /b 1
)

echo [4/4] Starting application...
echo.
echo ========================================
echo   STARTING SERVER...
echo   Wait for: "Started CourseEnrollmentSystemApplication"
echo ========================================
echo.
echo After startup, test these URLs:
echo   1. http://localhost:8080/admin/debug
echo   2. http://localhost:8080/admin/categories-test
echo   3. http://localhost:8080/admin/categories
echo.

call mvn spring-boot:run

