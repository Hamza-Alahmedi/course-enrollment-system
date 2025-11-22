@echo off
cls
echo ============================================================
echo   FIXING 404 ERROR - Manage Categories and Manage Courses
echo ============================================================
echo.
echo This script will:
echo   1. Stop all Java processes
echo   2. Clean old build files
echo   3. Rebuild the application with Java 17
echo   4. Start the application
echo.
echo Press any key to continue...
pause >nul

cd /d "C:\Users\hamza\OneDrive\Desktop\Online Course Enrolment System\Course Enrollment System"

echo.
echo [STEP 1/4] Stopping all Java processes...
taskkill /F /IM java.exe 2>nul
if %errorlevel% equ 0 (
    echo   ^> Java processes stopped
) else (
    echo   ^> No Java processes were running
)
timeout /t 2 /nobreak >nul

echo.
echo [STEP 2/4] Cleaning old build files...
call .\mvnw.cmd clean
if %errorlevel% neq 0 (
    echo   ERROR: Clean failed!
    pause
    exit /b 1
)
echo   ^> Clean completed successfully

echo.
echo [STEP 3/4] Building application (Java 17)...
call .\mvnw.cmd package -DskipTests
if %errorlevel% neq 0 (
    echo   ERROR: Build failed!
    pause
    exit /b 1
)
echo   ^> Build completed successfully

echo.
echo [STEP 4/4] Starting application...
echo.
echo ============================================================
echo   APPLICATION STARTING...
echo ============================================================
echo.
echo When you see "Started CourseEnrollmentSystemApplication"
echo the application is ready to use.
echo.
echo Then test these URLs:
echo   1. http://localhost:8080/login
echo      Login: admin@example.com / admin123
echo.
echo   2. http://localhost:8080/admin/home
echo      Dashboard should load
echo.
echo   3. Click "Manage Categories"
echo      Should show categories list (NOT 404!)
echo.
echo   4. Click "Manage Courses"
echo      Should show courses list (NOT 404!)
echo.
echo ============================================================
echo.

call .\mvnw.cmd spring-boot:run

