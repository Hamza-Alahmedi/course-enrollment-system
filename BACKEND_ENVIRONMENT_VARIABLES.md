# 🔐 Backend Environment Variables - Complete Configuration

## ✅ Updated Files:
1. `CustomAuthenticationSuccessHandler.java` - Now uses `${frontend.url}` environment variable
2. `application.properties` - Added `frontend.url` configuration

---

## 🎯 Backend Environment Variables for Render

Go to **Render Dashboard → Your Backend Service → Environment**

Add/Update these variables:

```env
# ============================================
# DATABASE CONFIGURATION (Railway)
# ============================================

# Database Connection URL
DATABASE_URL=jdbc:mysql://[RAILWAY_HOST]:[RAILWAY_PORT]/[RAILWAY_DATABASE]

# Database Username (from Railway)
DB_USERNAME=[RAILWAY_USER]

# Database Password (from Railway)
DB_PASSWORD=[RAILWAY_PASSWORD]


# ============================================
# SERVER CONFIGURATION
# ============================================

# Port (Render sets this automatically)
PORT=8080


# ============================================
# FRONTEND CONFIGURATION (Important!)
# ============================================

# Frontend URL - Used for login redirects
FRONTEND_URL=https://[YOUR-FRONTEND-URL].onrender.com


# ============================================
# CORS CONFIGURATION
# ============================================

# Allowed Origins - Must include frontend URL
ALLOWED_ORIGINS=https://[YOUR-FRONTEND-URL].onrender.com,http://localhost:5173
```

---

## 📋 Step-by-Step: Add Environment Variables

### 1️⃣ Get Your Frontend URL

1. Go to **Render Dashboard**
2. Click your **Frontend Static Site**
3. Copy the URL (e.g., `https://course-enrollment-frontend-abc123.onrender.com`)

---

### 2️⃣ Get Your Railway MySQL Credentials

1. Go to **Railway Dashboard** (https://railway.app/dashboard)
2. Click your **MySQL service**
3. Click **"Variables"** tab
4. Copy these values:
   - `MYSQLHOST`
   - `MYSQLPORT`
   - `MYSQLDATABASE`
   - `MYSQLUSER`
   - `MYSQLPASSWORD`

---

### 3️⃣ Add Variables to Render Backend

1. Go to **Render Dashboard**
2. Click your **Backend Service** (Docker service)
3. Click **"Environment"** (left sidebar)
4. Click **"Add Environment Variable"**
5. Add each variable one by one:

---

#### Variable 1: DATABASE_URL
```
Key:   DATABASE_URL
Value: jdbc:mysql://[MYSQLHOST]:[MYSQLPORT]/[MYSQLDATABASE]
```

**Example:**
```
jdbc:mysql://containers-us-west-123.railway.app:6543/railway
```

---

#### Variable 2: DB_USERNAME
```
Key:   DB_USERNAME
Value: [Your Railway MYSQLUSER]
```

**Example:**
```
root
```

---

#### Variable 3: DB_PASSWORD
```
Key:   DB_PASSWORD
Value: [Your Railway MYSQLPASSWORD]
```

**Example:**
```
AbCdEfGhIjKlMnOpQrStUvWxYz123456
```

---

#### Variable 4: PORT
```
Key:   PORT
Value: 8080
```

---

#### Variable 5: FRONTEND_URL ⭐ **NEW - This fixes the redirect!**
```
Key:   FRONTEND_URL
Value: https://[YOUR-FRONTEND-URL].onrender.com
```

**Example:**
```
https://course-enrollment-frontend-abc123.onrender.com
```

⚠️ **Important:** 
- Use `https://` (not `http://`)
- No trailing slash at the end
- Must be your exact Render frontend URL

---

#### Variable 6: ALLOWED_ORIGINS
```
Key:   ALLOWED_ORIGINS
Value: https://[YOUR-FRONTEND-URL].onrender.com,http://localhost:5173
```

**Example:**
```
https://course-enrollment-frontend-abc123.onrender.com,http://localhost:5173
```

---

### 4️⃣ Save and Redeploy

1. Click **"Save Changes"** in Render
2. Backend will automatically redeploy (~5 minutes)
3. Wait for deployment to complete
4. ✅ **Done!**

---

## 📝 Complete Example Configuration

Here's a complete example with dummy values:

```env
DATABASE_URL=jdbc:mysql://containers-us-west-123.railway.app:6543/railway
DB_USERNAME=root
DB_PASSWORD=AbCdEfGhIjKlMnOpQrStUvWxYz123456
PORT=8080
FRONTEND_URL=https://course-enrollment-frontend-abc123.onrender.com
ALLOWED_ORIGINS=https://course-enrollment-frontend-abc123.onrender.com,http://localhost:5173
```

---

## 🎯 What This Fixes

### Before (The Problem):
```java
// Hardcoded localhost
response.sendRedirect("http://localhost:5173?studentId=" + student.getId());
```

**Result:** When you login from backend, it redirects to `localhost:5173` (doesn't work on Render)

### After (The Solution):
```java
// Uses environment variable
response.sendRedirect(frontendUrl + "?studentId=" + student.getId());
```

**Result:** When you login from backend, it redirects to your live frontend URL on Render! ✅

---

## ✅ Testing After Update

### Test 1: Login from Backend HTML Page
1. Go to your backend URL: `https://your-backend.onrender.com/login`
2. Login with student credentials
3. Should redirect to: `https://your-frontend.onrender.com?studentId=1`
4. ✅ Student dashboard loads with data!

### Test 2: Login from Frontend React Page
1. Go to your frontend URL: `https://your-frontend.onrender.com`
2. Login with student credentials
3. Should stay on frontend and show dashboard
4. ✅ Works as expected!

---

## 🐛 Troubleshooting

### Problem: Still redirects to localhost

**Solution:**
1. Verify `FRONTEND_URL` variable is set in Render
2. Check it has correct frontend URL
3. Ensure backend redeployed after adding variable
4. Check backend logs for the frontend URL being used

### Problem: Redirect goes to wrong URL

**Solution:**
1. Check `FRONTEND_URL` value has no typos
2. Ensure it uses `https://` (not `http://`)
3. Remove any trailing slashes
4. Must match your exact frontend URL

### Problem: CORS error after redirect

**Solution:**
1. Check `ALLOWED_ORIGINS` includes your frontend URL
2. Format: `https://your-frontend.onrender.com,http://localhost:5173`
3. Save changes and wait for redeploy

---

## 📊 Environment Variables Summary

| Variable | Purpose | Example Value |
|----------|---------|---------------|
| `DATABASE_URL` | MySQL connection | `jdbc:mysql://host:port/db` |
| `DB_USERNAME` | Database user | `root` |
| `DB_PASSWORD` | Database password | `secret123` |
| `PORT` | Server port | `8080` |
| `FRONTEND_URL` ⭐ | **Login redirect URL** | `https://frontend.onrender.com` |
| `ALLOWED_ORIGINS` | CORS origins | `https://frontend.onrender.com,...` |

---

## 🎯 Quick Checklist

- [ ] Frontend deployed and URL copied
- [ ] Railway MySQL credentials copied
- [ ] Added `DATABASE_URL` to backend
- [ ] Added `DB_USERNAME` to backend
- [ ] Added `DB_PASSWORD` to backend
- [ ] Added `PORT=8080` to backend
- [ ] Added `FRONTEND_URL` with frontend URL ⭐
- [ ] Added `ALLOWED_ORIGINS` with frontend URL
- [ ] Saved changes in Render
- [ ] Backend redeployed successfully
- [ ] Tested login from backend HTML
- [ ] Tested login from frontend React
- [ ] ✅ Both work correctly!

---

## 🚀 Next Steps

1. **Commit the code changes:**
   ```powershell
   cd "C:\Users\hamza\OneDrive\Desktop\Online Course Enrolment System"
   git add .
   git commit -m "Fix: Use environment variable for frontend URL in login redirect"
   git push origin main
   ```

2. **Add environment variables in Render** (as shown above)

3. **Wait for backend to redeploy** (~5 minutes)

4. **Test login from backend** - Should redirect to frontend URL! ✅

---

**The key fix is adding `FRONTEND_URL` environment variable!** 🎉

**Status:** ✅ Code updated, ready for deployment
**Action Required:** Add environment variables in Render


