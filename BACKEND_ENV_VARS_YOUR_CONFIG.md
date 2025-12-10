# 🎯 Backend Environment Variables - YOUR CONFIGURATION

## ✅ Your URLs:
- **Frontend URL:** `https://course-enrollment-frontend-c9mr.onrender.com`
- **Backend URL:** (Your backend Render URL)

---

## 🔧 EXACT Environment Variables to Add in Render

Go to: **Render Dashboard → Your Backend Service → Environment → Add Environment Variable**

Copy these **EXACT values** (replace only the Railway database values):

---

### 1️⃣ FRONTEND_URL (⭐ This fixes the redirect!)
```
Key:   FRONTEND_URL
Value: https://course-enrollment-frontend-c9mr.onrender.com
```

---

### 2️⃣ ALLOWED_ORIGINS (CORS Configuration)
```
Key:   ALLOWED_ORIGINS
Value: https://course-enrollment-frontend-c9mr.onrender.com,http://localhost:5173
```

---

### 3️⃣ DATABASE_URL (From Railway)
```
Key:   DATABASE_URL
Value: jdbc:mysql://[YOUR_RAILWAY_HOST]:[YOUR_RAILWAY_PORT]/[YOUR_RAILWAY_DATABASE]
```

**Example format:**
```
jdbc:mysql://containers-us-west-123.railway.app:6543/railway
```

**How to get:**
1. Go to Railway Dashboard → Your MySQL service
2. Click "Variables" tab
3. Find: `MYSQLHOST`, `MYSQLPORT`, `MYSQLDATABASE`
4. Construct the URL using the format above

---

### 4️⃣ DB_USERNAME (From Railway)
```
Key:   DB_USERNAME
Value: [YOUR_RAILWAY_MYSQL_USER]
```

**Example:**
```
root
```

**How to get:**
1. Railway Dashboard → MySQL service → Variables
2. Copy the value of `MYSQLUSER`

---

### 5️⃣ DB_PASSWORD (From Railway)
```
Key:   DB_PASSWORD
Value: [YOUR_RAILWAY_MYSQL_PASSWORD]
```

**How to get:**
1. Railway Dashboard → MySQL service → Variables
2. Copy the value of `MYSQLPASSWORD`

---

### 6️⃣ PORT
```
Key:   PORT
Value: 8080
```

---

## 📋 Complete Configuration Summary

Here's what your environment variables should look like in Render:

```
┌─────────────────────────┬──────────────────────────────────────────────────┐
│ Key                     │ Value                                            │
├─────────────────────────┼──────────────────────────────────────────────────┤
│ FRONTEND_URL            │ https://course-enrollment-frontend-c9mr.o...     │
│ ALLOWED_ORIGINS         │ https://course-enrollment-frontend-c9mr.o...     │
│ DATABASE_URL            │ jdbc:mysql://[YOUR_RAILWAY_HOST]:...            │
│ DB_USERNAME             │ [YOUR_RAILWAY_USER]                              │
│ DB_PASSWORD             │ [YOUR_RAILWAY_PASSWORD]                          │
│ PORT                    │ 8080                                             │
└─────────────────────────┴──────────────────────────────────────────────────┘
```

---

## ✅ Step-by-Step Instructions

### Step 1: Commit and Push Code Changes

Open PowerShell and run:

```powershell
cd "C:\Users\hamza\OneDrive\Desktop\Online Course Enrolment System"
git add .
git commit -m "Fix: Use environment variable for frontend URL redirect"
git push origin main
```

---

### Step 2: Add Environment Variables in Render

1. Go to **Render Dashboard** (https://dashboard.render.com)
2. Click your **Backend Service** (the Docker service)
3. Click **"Environment"** (left sidebar)
4. Add each variable using the button **"Add Environment Variable"**

#### Add Variable 1: FRONTEND_URL
- Click "Add Environment Variable"
- Key: `FRONTEND_URL`
- Value: `https://course-enrollment-frontend-c9mr.onrender.com`
- Click outside to save

#### Add Variable 2: ALLOWED_ORIGINS
- Click "Add Environment Variable"
- Key: `ALLOWED_ORIGINS`
- Value: `https://course-enrollment-frontend-c9mr.onrender.com,http://localhost:5173`
- Click outside to save

#### Add Variable 3-6: Database & Port
- Add `DATABASE_URL`, `DB_USERNAME`, `DB_PASSWORD`, `PORT`
- Use values from Railway MySQL service

5. Click **"Save Changes"** button at the bottom

---

### Step 3: Wait for Redeploy

1. Render will **automatically redeploy** your backend
2. Wait ~5-7 minutes for Docker build to complete
3. Check logs to ensure deployment succeeds

---

## 🎯 What This Fixes

### Before:
When you login from backend HTML page (`https://your-backend.onrender.com/login`):
```
→ Redirects to: http://localhost:5173?studentId=1
❌ Doesn't work (localhost is not accessible)
```

### After:
When you login from backend HTML page:
```
→ Redirects to: https://course-enrollment-frontend-c9mr.onrender.com?studentId=1
✅ Works perfectly! Opens your live frontend with student data
```

---

## 🧪 Testing After Update

### Test 1: Login from Backend HTML
1. Open: `https://your-backend.onrender.com/login`
2. Enter student credentials (email & password)
3. Click "Login"
4. **Expected Result:** Redirects to `https://course-enrollment-frontend-c9mr.onrender.com?studentId=X`
5. ✅ Student dashboard loads with your data!

### Test 2: Login from Frontend React
1. Open: `https://course-enrollment-frontend-c9mr.onrender.com`
2. Enter student credentials
3. Click "Login"
4. **Expected Result:** Stays on frontend, shows dashboard
5. ✅ Works as expected!

### Test 3: Check CORS
1. Open frontend
2. Open browser DevTools (F12) → Console tab
3. Try any API call
4. **Expected Result:** No CORS errors
5. ✅ Backend allows requests from frontend!

---

## 🐛 Troubleshooting

### Problem: Still redirects to localhost

**Check:**
1. Is `FRONTEND_URL` set in Render backend environment?
2. Did backend redeploy after adding the variable?
3. Clear browser cache and try again

**Solution:**
- Go to Render → Backend → Environment
- Verify `FRONTEND_URL` exists and has correct value
- Click "Manual Deploy" → "Deploy latest commit"

---

### Problem: "Cannot connect to database"

**Check:**
1. Is Railway MySQL running? (should show "Active" status)
2. Are `DATABASE_URL`, `DB_USERNAME`, `DB_PASSWORD` correct?
3. Check Railway MySQL variables match what you entered

**Solution:**
- Go to Railway → MySQL → Variables
- Copy fresh values
- Update Render backend environment variables
- Save and redeploy

---

### Problem: CORS error in browser console

**Check:**
1. Does `ALLOWED_ORIGINS` include your frontend URL?
2. Is the format correct? (comma-separated, no spaces)

**Solution:**
- Update `ALLOWED_ORIGINS` to:
  ```
  https://course-enrollment-frontend-c9mr.onrender.com,http://localhost:5173
  ```
- Save and wait for redeploy

---

## 📝 Quick Checklist

- [ ] Code changes committed and pushed to GitHub
- [ ] Added `FRONTEND_URL` = `https://course-enrollment-frontend-c9mr.onrender.com`
- [ ] Added `ALLOWED_ORIGINS` = `https://course-enrollment-frontend-c9mr.onrender.com,http://localhost:5173`
- [ ] Added Railway database credentials (DATABASE_URL, DB_USERNAME, DB_PASSWORD)
- [ ] Added `PORT` = `8080`
- [ ] Clicked "Save Changes" in Render
- [ ] Backend redeployed successfully
- [ ] Tested login from backend HTML page
- [ ] Confirmed redirect goes to frontend URL ✅
- [ ] Tested login from frontend React page
- [ ] No CORS errors in browser console ✅

---

## 🎉 Success Indicators

When everything is working correctly:

1. **Backend HTML login:**
   - Enter credentials
   - Click login
   - Browser goes to: `https://course-enrollment-frontend-c9mr.onrender.com?studentId=1`
   - Student dashboard loads with data

2. **Frontend React login:**
   - Enter credentials
   - Click login
   - Stays on frontend
   - Dashboard appears with courses

3. **No errors:**
   - No CORS errors in console
   - No 401/403 errors
   - API calls work smoothly

---

## 🚀 After Setup Complete

Your full stack will be working:
- ✅ **Frontend:** `https://course-enrollment-frontend-c9mr.onrender.com`
- ✅ **Backend:** `https://your-backend.onrender.com`
- ✅ **Database:** Railway MySQL (connected)
- ✅ **Login redirects:** Work correctly
- ✅ **CORS:** Configured properly
- ✅ **API calls:** All functional

---

**Now commit your code and add these environment variables in Render!** 🎯


