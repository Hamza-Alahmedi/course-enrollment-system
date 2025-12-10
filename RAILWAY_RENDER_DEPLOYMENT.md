# 🚂 Railway MySQL + Render Deployment Guide

## 🎯 Your Deployment Architecture

```
┌─────────────────────────────────────────────┐
│           DEPLOYMENT ARCHITECTURE            │
├─────────────────────────────────────────────┤
│                                             │
│  📦 Database (MySQL)                        │
│  Platform: Railway.app                      │
│  ✅ Free tier available                     │
│  ✅ Persistent storage                      │
│                                             │
│  🖥️ Backend (Spring Boot)                   │
│  Platform: Render.com                       │
│  ✅ Free tier (750 hrs/month)               │
│                                             │
│  🌐 Frontend (React + Vite)                 │
│  Platform: Render.com                       │
│  ✅ Free static site hosting                │
│                                             │
└─────────────────────────────────────────────┘
```

---

## 📋 Complete Deployment Checklist

### Phase 1: Setup Railway MySQL Database (15 minutes)

#### Step 1.1: Create Railway Account
- [ ] Go to https://railway.app
- [ ] Click "Login" → "Login with GitHub"
- [ ] Authorize Railway to access GitHub
- [ ] ✅ Account created!

#### Step 1.2: Create MySQL Database
- [ ] Click "New Project"
- [ ] Select "Provision MySQL"
- [ ] Wait for deployment (~2 minutes)
- [ ] ✅ MySQL database created!

#### Step 1.3: Get Database Credentials
- [ ] Click on your MySQL service
- [ ] Go to "Variables" tab
- [ ] Copy these values:
  ```
  MYSQLHOST=______________________
  MYSQLPORT=______________________
  MYSQLDATABASE=__________________
  MYSQLUSER=______________________
  MYSQLPASSWORD=__________________
  ```
- [ ] ✅ Credentials saved!

#### Step 1.4: Construct Connection String
Create your `DATABASE_URL` using this format:
```
jdbc:mysql://MYSQLHOST:MYSQLPORT/MYSQLDATABASE
```

Example:
```
jdbc:mysql://containers-us-west-123.railway.app:7890/railway
```

- [ ] ✅ Connection string ready!

#### Step 1.5: (Optional) Import Your Local Data
If you want to keep your existing data:

**Option A: Using Railway MySQL Client**
- [ ] Click "Data" tab in Railway
- [ ] Click "Query" to open SQL editor
- [ ] Run `export-database.bat` on your PC first
- [ ] Copy SQL from backup file
- [ ] Paste and execute in Railway
- [ ] ✅ Data imported!

**Option B: Let Hibernate Create Tables**
- [ ] Skip import (start fresh)
- [ ] Backend will auto-create tables
- [ ] ✅ Clean start!

---

### Phase 2: Deploy Backend to Render (20 minutes)

#### Step 2.1: Push Code to GitHub (if not done)
```powershell
cd "C:\Users\hamza\OneDrive\Desktop\Online Course Enrolment System"
git add .
git commit -m "Prepare for Railway + Render deployment"
git push origin main
```
- [ ] ✅ Code on GitHub!

#### Step 2.2: Create Render Account
- [ ] Go to https://render.com
- [ ] Click "Get Started"
- [ ] Sign up with GitHub
- [ ] Authorize Render
- [ ] ✅ Account created!

#### Step 2.3: Create Backend Web Service
- [ ] Click "New +" → "Web Service"
- [ ] Click "Connect" next to your repository
- [ ] Configure service:

| Setting | Value |
|---------|-------|
| **Name** | `course-enrollment-backend` |
| **Region** | Choose closest to you |
| **Branch** | `main` |
| **Root Directory** | `course-enrollment-backend` |
| **Runtime** | `Java` |
| **Build Command** | `./mvnw clean install -DskipTests` |
| **Start Command** | `java -jar target/Course-Enrollment-System-0.0.1-SNAPSHOT.jar` |
| **Instance Type** | `Free` |

- [ ] ✅ Service configured!

#### Step 2.4: Add Environment Variables
Click "Advanced" → "Add Environment Variable"

Add these variables using your Railway credentials:

```
PORT=8080

DATABASE_URL=jdbc:mysql://[YOUR_RAILWAY_HOST]:[PORT]/[DATABASE_NAME]

DB_USERNAME=[YOUR_RAILWAY_MYSQL_USER]

DB_PASSWORD=[YOUR_RAILWAY_MYSQL_PASSWORD]

ALLOWED_ORIGINS=http://localhost:5173
```

**Example** (use YOUR values from Railway):
```
PORT=8080
DATABASE_URL=jdbc:mysql://containers-us-west-123.railway.app:7890/railway
DB_USERNAME=root
DB_PASSWORD=AbCdEfGh1234567890
ALLOWED_ORIGINS=http://localhost:5173
```

- [ ] ✅ Environment variables added!

#### Step 2.5: Deploy Backend
- [ ] Click "Create Web Service"
- [ ] Wait for build (~15 minutes first time)
- [ ] Check logs for errors
- [ ] Note your backend URL: `https://________________.onrender.com`
- [ ] ✅ Backend deployed!

#### Step 2.6: Test Backend
- [ ] Visit your backend URL
- [ ] You should see login page or response
- [ ] Check logs: "Started CourseEnrollmentSystemApplication"
- [ ] ✅ Backend working!

---

### Phase 3: Deploy Frontend to Render (15 minutes)

#### Step 3.1: Create Static Site
- [ ] Go to Render Dashboard
- [ ] Click "New +" → "Static Site"
- [ ] Connect your repository
- [ ] Configure service:

| Setting | Value |
|---------|-------|
| **Name** | `course-enrollment-frontend` |
| **Branch** | `main` |
| **Root Directory** | `student-frontend` |
| **Build Command** | `npm install && npm run build` |
| **Publish Directory** | `dist` |

- [ ] ✅ Service configured!

#### Step 3.2: Add Environment Variable
Click "Advanced" → "Add Environment Variable"

```
VITE_API_URL=https://[YOUR-BACKEND-URL].onrender.com
```

**Example** (use YOUR backend URL from Phase 2):
```
VITE_API_URL=https://course-enrollment-backend-abc123.onrender.com
```

- [ ] ✅ Environment variable added!

#### Step 3.3: Deploy Frontend
- [ ] Click "Create Static Site"
- [ ] Wait for build (~10 minutes)
- [ ] Note your frontend URL: `https://________________.onrender.com`
- [ ] ✅ Frontend deployed!

---

### Phase 4: Connect Frontend & Backend (5 minutes)

#### Step 4.1: Update Backend CORS
- [ ] Go to backend service on Render
- [ ] Click "Environment"
- [ ] Edit `ALLOWED_ORIGINS` variable
- [ ] Update to include frontend URL:

**Before**:
```
ALLOWED_ORIGINS=http://localhost:5173
```

**After** (use YOUR frontend URL):
```
ALLOWED_ORIGINS=https://course-enrollment-frontend-xyz789.onrender.com,http://localhost:5173
```

- [ ] Click "Save Changes"
- [ ] Wait for automatic redeploy (~5 minutes)
- [ ] ✅ CORS updated!

---

### Phase 5: Testing (10 minutes)

#### Step 5.1: Test Frontend Loading
- [ ] Visit your frontend URL
- [ ] Page loads without errors
- [ ] See login form
- [ ] ✅ Frontend loads!

#### Step 5.2: Test Student Login
- [ ] Login with student credentials
- [ ] Dashboard loads
- [ ] Can see courses
- [ ] Can enroll in courses
- [ ] Can rate courses
- [ ] ✅ Student features work!

#### Step 5.3: Test Admin Login
- [ ] Logout
- [ ] Login with admin credentials
- [ ] Admin dashboard loads
- [ ] Can manage categories
- [ ] Can manage courses
- [ ] ✅ Admin features work!

#### Step 5.4: Check Database
- [ ] Go to Railway dashboard
- [ ] Click on MySQL service
- [ ] Go to "Data" tab
- [ ] See tables created
- [ ] See data populated
- [ ] ✅ Database connected!

---

## 📊 Your Deployment URLs

Once completed, save these URLs:

```
DATABASE:  Railway MySQL
           railway.app/project/[your-project-id]

BACKEND:   https://_________________________.onrender.com

FRONTEND:  https://_________________________.onrender.com
```

---

## 🐛 Troubleshooting

### Backend Won't Start

**Check Render Logs**:
1. Go to backend service
2. Click "Logs" tab
3. Look for errors

**Common Issues**:

❌ **"Cannot connect to database"**
- Verify `DATABASE_URL` is correct
- Check Railway MySQL is running
- Verify credentials are correct
- Railway might be restarting (wait 1 minute)

❌ **"Port already in use"**
- Remove `PORT` variable (Render sets it automatically)
- Or keep it as `8080`

❌ **"Build failed"**
- Check Java version (should be 17)
- Try adding `MAVEN_OPTS=-Xmx1024m`

### Frontend Can't Connect to Backend

**Check Browser Console**:
1. Press F12
2. Go to Console tab
3. Look for errors

**Common Issues**:

❌ **CORS Error**
- Verify frontend URL in backend's `ALLOWED_ORIGINS`
- Must include `https://` prefix
- No trailing slash

❌ **404 Not Found**
- Verify `VITE_API_URL` is correct
- Check backend is running (not spun down)

### Database Connection Issues

**Test from Railway**:
1. Go to Railway MySQL service
2. Click "Data" tab
3. Try running: `SHOW TABLES;`
4. If error, database might be restarting

**Common Issues**:

❌ **"Access denied"**
- Check `DB_USERNAME` and `DB_PASSWORD`
- Make sure they match Railway variables exactly

❌ **"Unknown database"**
- Check `MYSQLDATABASE` name
- Verify in Railway variables

---

## 💰 Cost Breakdown

### Railway (MySQL Database)
- **Free Tier**: $5 credit/month
- **MySQL Usage**: ~$0.50-2/month
- **Your Cost**: FREE (covered by $5 credit)
- **Limit**: ~500MB storage

### Render (Backend)
- **Free Tier**: 750 hours/month
- **Your Cost**: FREE
- **Limit**: Sleeps after 15 min inactive

### Render (Frontend)
- **Free Tier**: 100GB bandwidth
- **Your Cost**: FREE
- **Limit**: Generous for student projects

### Total Monthly Cost: **$0** ✅

---

## ⚠️ Important Notes

### Free Tier Limitations

**Railway**:
- $5 free credit/month
- Perfect for one MySQL database
- Monitor usage in dashboard
- Database persists (doesn't sleep)

**Render Backend**:
- Sleeps after 15 minutes of inactivity
- Takes ~30 seconds to wake up
- 750 hours/month (enough for one service running 24/7)

**Render Frontend**:
- Always available (static site)
- No sleep time
- Fast loading

### Keep Services Active

**Backend**: First request after sleep takes time
**Database**: Always active on Railway
**Frontend**: Always active

**Tip**: Use a service like UptimeRobot (free) to ping your backend every 5 minutes to keep it awake.

---

## 🎓 Student Tips

### Save Money
- ✅ Railway: $5/month credit (enough for MySQL)
- ✅ Render: Free tier (perfect for learning)
- ✅ Total: $0/month

### Monitor Usage
- Railway: Check credit usage weekly
- Render: Check hours used monthly
- Set up alerts if available

### Backup Strategy
1. Export Railway MySQL weekly
2. Commit code changes to GitHub
3. Keep local development environment

---

## 📱 After Deployment

### Share Your Project
```
Your Live App: https://your-frontend.onrender.com

Add to:
- Resume/CV
- Portfolio website
- LinkedIn projects
- GitHub README
```

### Monitor Your Services
- Railway Dashboard: https://railway.app/dashboard
- Render Dashboard: https://dashboard.render.com

### Keep Learning
- Check logs regularly
- Understand error messages
- Improve performance
- Add features

---

## 🎉 Success Criteria

✅ Railway MySQL database created and accessible
✅ Backend deployed on Render and running
✅ Frontend deployed on Render and loading
✅ Backend can connect to Railway MySQL
✅ Frontend can connect to Backend
✅ Can login as student and admin
✅ All features working
✅ Total cost: $0/month

---

## 📞 Need Help?

### If Backend Won't Connect to Database:
1. Double-check Railway credentials
2. Verify DATABASE_URL format
3. Check Railway MySQL is running
4. Wait 1 minute (Railway might be restarting)
5. Check Render logs for specific error

### If Frontend Won't Connect to Backend:
1. Verify VITE_API_URL in frontend
2. Check ALLOWED_ORIGINS in backend
3. Open browser console (F12)
4. Look for CORS errors
5. Ensure backend is awake (visit URL)

### If Database Shows No Tables:
1. Check backend logs for Hibernate messages
2. Should see "Creating table..." messages
3. If not, check `spring.jpa.hibernate.ddl-auto=update`
4. Try restarting backend service

---

## 🚀 You're All Set!

With this setup, you have:
- ✅ Professional cloud deployment
- ✅ Persistent MySQL database (Railway)
- ✅ Scalable backend (Render)
- ✅ Fast frontend (Render)
- ✅ All for FREE!

**Your app is production-ready!** 🎊

---

**Last Updated**: December 10, 2025
**Architecture**: Railway MySQL + Render (Backend + Frontend)
**Status**: Ready to Deploy

