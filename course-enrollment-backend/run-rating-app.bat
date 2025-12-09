@echo off
echo Stopping any Java processes...
taskkill /F /IM java.exe 2>nul
timeout /t 2 /nobreak >nul

echo.
echo Compiling and running the application...
echo.

cd /d "%~dp0"

REM Try to find Maven
set MAVEN_CMD=

where mvn >nul 2>&1
if %errorlevel% equ 0 (
    set MAVEN_CMD=mvn
    goto :run
)

if exist "C:\Program Files\Apache\maven\bin\mvn.cmd" (
    set MAVEN_CMD="C:\Program Files\Apache\maven\bin\mvn.cmd"
    goto :run
)

if exist "C:\apache-maven\bin\mvn.cmd" (
    set MAVEN_CMD="C:\apache-maven\bin\mvn.cmd"
    goto :run
)

echo ERROR: Maven not found! Please install Maven or add it to PATH.
pause
exit /b 1

:run
echo Using Maven: %MAVEN_CMD%
echo.
echo Building project...
call %MAVEN_CMD% clean package -DskipTests

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo.
echo Starting application...
echo.
start cmd /k "java -jar target\course-enrollment-system-0.0.1-SNAPSHOT.jar"

echo.
echo Application is starting in a new window...
echo Check http://localhost:8080
echo.
pause

