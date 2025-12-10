@echo off
REM Export MySQL Database to SQL file
REM This script exports your current database so you can import it to PostgreSQL later

echo ========================================
echo MySQL Database Export Script
echo ========================================
echo.

REM Set variables
set DB_NAME=online_course_db
set DB_USER=course_user
set DB_PASS=Test123!#
set OUTPUT_FILE=database_backup_%date:~-4,4%%date:~-10,2%%date:~-7,2%_%time:~0,2%%time:~3,2%%time:~6,2%.sql
set OUTPUT_FILE=%OUTPUT_FILE: =0%

echo Database: %DB_NAME%
echo User: %DB_USER%
echo Output file: %OUTPUT_FILE%
echo.
echo Exporting database...
echo.

REM Export the database
mysqldump -u %DB_USER% -p%DB_PASS% %DB_NAME% > %OUTPUT_FILE%

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo SUCCESS! Database exported to:
    echo %OUTPUT_FILE%
    echo ========================================
    echo.
    echo You can use this file to:
    echo 1. Backup your data
    echo 2. Import to PostgreSQL after conversion
    echo.
) else (
    echo.
    echo ========================================
    echo ERROR! Failed to export database
    echo ========================================
    echo.
    echo Please check:
    echo 1. MySQL is running
    echo 2. Username and password are correct
    echo 3. Database name is correct
    echo 4. mysqldump is in your PATH
    echo.
)

pause

