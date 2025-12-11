# 🎯 Deployment Checklist

Use this checklist to track your deployment progress.

---

## 📋 Pre-Deployment

- [ ] All code changes reviewed
- [ ] No compilation errors in backend
- [ ] No errors in frontend code
- [ ] Documentation files created and reviewed

---

## 🔧 Git Operations

- [ ] Navigated to project directory
- [ ] Ran `git status` to see changes
- [ ] Staged all changes with `git add .`
- [ ] Created commit with descriptive message
- [ ] Pushed to GitHub with `git push origin main`
- [ ] Verified commit appears on GitHub

**Git Commands:**
```bash
cd "C:\Users\hamza\OneDrive\Desktop\Online Course Enrolment System"
git status
git add .
git commit -m "Fix: Authentication and session management for production"
git push origin main
```

---

## 🌐 Environment Variables - Backend

Go to: Render Dashboard → Backend Service → Environment

- [ ] Added/Updated: `ALLOWED_ORIGINS=https://course-enrollment-frontend-c9mr.onrender.com`
- [ ] Added/Updated: `FRONTEND_URL=https://course-enrollment-frontend-c9mr.onrender.com`
- [ ] Verified: `DATABASE_URL` is correct
- [ ] Verified: `DB_USERNAME` is correct
- [ ] Verified: `DB_PASSWORD` is correct
- [ ] Verified: `PORT=8080`
- [ ] Saved environment variables

---

## 🌐 Environment Variables - Frontend

Go to: Render Dashboard → Frontend Service → Environment

- [ ] Added/Updated: `VITE_API_URL=https://course-enrollment-system-dxav.onrender.com`
- [ ] Saved environment variables

---

## 🚀 Deployment

### Backend Deployment:
- [ ] Render detected push to GitHub
- [ ] Backend build started
- [ ] Build completed successfully
- [ ] Logs show: "Started CourseEnrollmentSystemApplication"
- [ ] No ERROR messages in logs
- [ ] Service is "Live" (green indicator)

### Frontend Deployment:
- [ ] Render detected push to GitHub
- [ ] Frontend build started
- [ ] npm install completed
- [ ] vite build completed
- [ ] Build succeeded
- [ ] Service is "Live" (green indicator)

**Time Check:** ⏱️ Both should deploy in 5-10 minutes

---

## ✅ Testing Phase

### Test 1: Frontend Login
- [ ] Opened: https://course-enrollment-frontend-c9mr.onrender.com
- [ ] Entered student email and password
- [ ] Clicked Login
- [ ] Dashboard loaded successfully
- [ ] Student name displayed
- [ ] No console errors
- [ ] Courses displayed

**Result:** ✅ PASS / ❌ FAIL

---

### Test 2: Rate a Course
- [ ] Navigated to "My Courses" tab
- [ ] Found an enrolled course
- [ ] Clicked on star rating (1-5 stars)
- [ ] Notification: "Rating submitted successfully!" appeared
- [ ] No "User not authenticated" error
- [ ] Average rating updated

**Result:** ✅ PASS / ❌ FAIL

---

### Test 3: Logout and Re-login
- [ ] Clicked Logout button
- [ ] Redirected to login page
- [ ] Entered credentials again
- [ ] Login successful
- [ ] Dashboard loaded
- [ ] Can rate courses again

**Result:** ✅ PASS / ❌ FAIL

---

### Test 4: Backend Login (Thymeleaf)
- [ ] Opened: https://course-enrollment-system-dxav.onrender.com/login
- [ ] Entered STUDENT credentials
- [ ] Clicked Login
- [ ] Redirected to: https://course-enrollment-frontend-c9mr.onrender.com?studentId=X
- [ ] Dashboard loaded in React frontend
- [ ] All features work

**Result:** ✅ PASS / ❌ FAIL

---

### Test 5: Admin Panel
- [ ] Opened: https://course-enrollment-system-dxav.onrender.com/login
- [ ] Entered ADMIN credentials
- [ ] Redirected to: /admin/dashboard
- [ ] Admin panel loaded
- [ ] Can view courses
- [ ] Can add/edit courses
- [ ] Can manage categories

**Result:** ✅ PASS / ❌ FAIL

---

### Test 6: Session Persistence
- [ ] Logged in to frontend
- [ ] Pressed F5 (refresh page)
- [ ] Still logged in (dashboard shows)
- [ ] Can still rate courses
- [ ] JSESSIONID cookie exists (F12 → Application → Cookies)

**Result:** ✅ PASS / ❌ FAIL

---

## 🐛 Troubleshooting (If Tests Fail)

### If Test 1 Fails (Login Error):
- [ ] Checked browser console for errors
- [ ] Verified VITE_API_URL in frontend env vars
- [ ] Tested backend directly: https://course-enrollment-system-dxav.onrender.com/api/courses
- [ ] Checked backend logs on Render
- [ ] Verified backend is "Live"

### If Test 2 Fails (Rating Error):
- [ ] Checked browser DevTools → Network tab
- [ ] Looked for JSESSIONID cookie
- [ ] Verified ALLOWED_ORIGINS in backend env vars
- [ ] Checked backend logs for auth errors
- [ ] Tried logging out and back in

### If Test 3 Fails (Re-login Error):
- [ ] Cleared browser cache and cookies
- [ ] Checked localStorage is cleared on logout
- [ ] Verified logout API is called (Network tab)
- [ ] Checked backend logs for logout request

### If CORS Errors Appear:
- [ ] Verified ALLOWED_ORIGINS exactly matches frontend URL
- [ ] Restarted backend service on Render
- [ ] Checked WebConfig.java is deployed
- [ ] Verified no typos in URLs

---

## 📊 Final Verification

- [ ] All 6 tests passed
- [ ] No console errors
- [ ] No CORS errors
- [ ] Both services show "Live" on Render
- [ ] Database connection working
- [ ] All features functional

---

## 🎉 Success Criteria

**Deployment is successful when:**
- ✅ All 6 tests pass
- ✅ No errors in browser console
- ✅ No errors in backend logs
- ✅ Students can login from frontend
- ✅ Students can rate courses
- ✅ Logout and re-login works
- ✅ Admin panel accessible

---

## 📝 Notes Section

Use this space to track issues or observations:

**Issues Encountered:**
```
(Write any problems you faced)
```

**Solutions Applied:**
```
(Write what fixed the problems)
```

**Deploy Time:**
- Started: _______ (time)
- Completed: _______ (time)
- Total Duration: _______ minutes

---

## ✅ Completion

- [ ] All tests passed
- [ ] Application working in production
- [ ] Documentation reviewed
- [ ] Ready for users

**Final Status:** ⬜ In Progress / ✅ Complete / ❌ Failed

**Date Completed:** ________________

**Notes:**
```
(Final thoughts or observations)
```

---

## 📞 Quick Reference

**Frontend URL:** https://course-enrollment-frontend-c9mr.onrender.com  
**Backend URL:** https://course-enrollment-system-dxav.onrender.com  
**Render Dashboard:** https://dashboard.render.com

**Documentation:**
- Quick Start: QUICK_START_DEPLOY.md
- Full Guide: DEPLOY_AUTHENTICATION_FIX.md
- Technical Details: AUTHENTICATION_FIX_COMPLETE.md
- Git Guide: GIT_COMMIT_GUIDE.md

---

**Good luck with your deployment! 🚀**

