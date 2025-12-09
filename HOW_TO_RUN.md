# 🚀 HOW TO RUN THE PROJECT

## ✅ Frontend is Starting!

I've started the React frontend. It should be running on **http://localhost:5173**

---

## 🔴 Backend Needs Manual Start

The backend cannot be started automatically because Maven wrapper is missing and Maven is not installed globally.

### ⚡ EASIEST WAY - Use IntelliJ IDEA

1. **Open IntelliJ IDEA**
2. **Open Project**: 
   - File → Open
   - Navigate to: `C:\Users\hamza\OneDrive\Desktop\Online Course Enrolment System\course-enrollment-backend`
   - Click "OK"
3. **Wait for Maven sync** to complete (bottom right progress bar)
4. **Find the main class**:
   - Navigate to: `src/main/java/com/hamza/courseenrollmentsystem/CourseEnrollmentSystemApplication.java`
5. **Run the application**:
   - Right-click on `CourseEnrollmentSystemApplication.java`
   - Select "Run 'CourseEnrollmentSystemApplication.main()'"
6. **Backend starts** on http://localhost:8080

---

## Alternative: Install Maven First

If you don't have IntelliJ IDEA, install Maven:

### Install Maven (Windows)

1. **Download Maven**:
   - Go to: https://maven.apache.org/download.cgi
   - Download: `apache-maven-3.9.6-bin.zip`

2. **Extract**:
   - Extract to: `C:\Program Files\Apache\maven`

3. **Add to PATH**:
   - Open: System Properties → Environment Variables
   - Edit "Path" variable
   - Add: `C:\Program Files\Apache\maven\bin`
   - Click OK

4. **Verify**:
   ```powershell
   mvn --version
   ```

5. **Run Backend**:
   ```powershell
   cd "C:\Users\hamza\OneDrive\Desktop\Online Course Enrolment System\course-enrollment-backend"
   mvn spring-boot:run
   ```

---

## ✅ Once Both Are Running

### Frontend (React)
- **URL**: http://localhost:5173
- **Status**: Should be running now
- **Check**: Open browser and go to http://localhost:5173

### Backend (Spring Boot)
- **URL**: http://localhost:8080
- **Status**: Needs to be started manually (see above)
- **Check**: Once started, go to http://localhost:8080

---

## 🎯 What You'll See

### Frontend
1. Open http://localhost:5173
2. You'll see the student login page
3. Enter credentials and login
4. Access the enhanced student dashboard with:
   - Search functionality
   - Category filtering
   - Course enrollment
   - Student profile

### Backend
1. Backend provides APIs for:
   - User authentication
   - Course management
   - Student enrollment
   - Admin dashboard
2. Admin dashboard: http://localhost:8080/admin/home

---

## 🔍 Check if Frontend is Running

Open PowerShell and run:
```powershell
netstat -ano | findstr :5173
```
If you see output, frontend is running!

---

## 📊 Project Status

| Component | Status | Action Needed |
|-----------|--------|---------------|
| **Frontend** | 🟢 STARTING | Check http://localhost:5173 |
| **Backend** | 🔴 NOT STARTED | Use IntelliJ or install Maven |
| **Database** | ⚠️ AUTO-CREATE | Will be created when backend starts |

---

## 🎓 Full Startup Checklist

- [ ] Frontend started (should be running now)
- [ ] Backend started (use IntelliJ or Maven)
- [ ] Open http://localhost:5173 in browser
- [ ] Login with student credentials
- [ ] Test the enhanced dashboard features

---

## 🆘 Troubleshooting

### Frontend Not Loading
```powershell
# Check if it's running
netstat -ano | findstr :5173

# If not, start it manually
cd "C:\Users\hamza\OneDrive\Desktop\Online Course Enrolment System\student-frontend"
npm run dev
```

### Backend Won't Start
- **Option 1**: Use IntelliJ IDEA (easiest)
- **Option 2**: Install Maven globally
- **Option 3**: Copy Maven wrapper from another Spring Boot project

### Port Already in Use
```powershell
# Kill process on port 8080 (backend)
netstat -ano | findstr :8080
taskkill /PID [PID_NUMBER] /F

# Kill process on port 5173 (frontend)
netstat -ano | findstr :5173
taskkill /PID [PID_NUMBER] /F
```

---

## 🎉 Quick Summary

**Frontend**: ✅ Started automatically - Check http://localhost:5173

**Backend**: ⚠️ Needs manual start:
1. Open IntelliJ IDEA
2. Open `course-enrollment-backend` folder
3. Run `CourseEnrollmentSystemApplication.java`

**OR**

1. Install Maven
2. Run `mvn spring-boot:run`

---

## 📂 Files Opened for You

I've opened the project folder in File Explorer. You can:
- Navigate to `course-enrollment-backend` and open in IntelliJ
- Or double-click `start-all.bat` (but you'll need Maven first)

---

**The frontend is starting now! Use IntelliJ IDEA to start the backend.** 🚀

