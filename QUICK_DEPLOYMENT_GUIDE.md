# 🎯 Quick Reference: Railway + Render Deployment

## 📋 Deployment Order (Do in This Sequence)

```
1. Railway → Create MySQL Database     (5 min)
2. Render  → Deploy Backend            (20 min)
3. Render  → Deploy Frontend           (15 min)
4. Render  → Update Backend CORS       (5 min)
5. Test    → Verify Everything Works   (10 min)
```

---

## 🚂 Step 1: Railway MySQL (5 minutes)

### Actions:
1. Go to https://railway.app
2. Login with GitHub
3. "New Project" → "Provision MySQL"
4. Wait 2 minutes

### Get These Values:
```
MYSQLHOST     = ________________________
MYSQLPORT     = ________________________
MYSQLDATABASE = ________________________
MYSQLUSER     = ________________________
MYSQLPASSWORD = ________________________
```

### Create Connection String:
```
jdbc:mysql://[MYSQLHOST]:[MYSQLPORT]/[MYSQLDATABASE]
```

Example:
```
jdbc:mysql://containers-us-west-123.railway.app:7890/railway
```

**Save this!** You'll need it for Render backend.

---

## 🖥️ Step 2: Render Backend (20 minutes)

### Actions:
1. Go to https://render.com
2. Login with GitHub
3. "New +" → "Web Service"
4. Connect your repository

### Configuration:

| Field | Value |
|-------|-------|
| Name | `course-enrollment-backend` |
| Root Directory | `course-enrollment-backend` |
| Environment | `Docker` |
| Dockerfile Path | `Dockerfile` (auto-detected) |
| Instance Type | `Free` |

**Important:**
- Render auto-detects Dockerfile
- No build/start commands needed
- Docker handles everything

### Environment Variables:

```bash
PORT=8080

DATABASE_URL=jdbc:mysql://[YOUR_RAILWAY_HOST]:[PORT]/[DATABASE]

DB_USERNAME=[YOUR_RAILWAY_USER]

DB_PASSWORD=[YOUR_RAILWAY_PASSWORD]

ALLOWED_ORIGINS=http://localhost:5173
```

### Important:
- Use Railway values from Step 1
- Keep `ALLOWED_ORIGINS` as shown (update in Step 4)
- Save your backend URL: `https://__________.onrender.com`

---

## 🌐 Step 3: Render Frontend (15 minutes)

### Actions:
1. Render Dashboard → "New +" → "Static Site"
2. Connect same repository

### Configuration:

| Field | Value |
|-------|-------|
| Name | `course-enrollment-frontend` |
| Root Directory | `student-frontend` |
| Build Command | `npm install && npm run build` |
| Publish Directory | `dist` |

### Environment Variable:

```bash
VITE_API_URL=https://[YOUR_BACKEND_URL].onrender.com
```

**Use backend URL from Step 2!**

### Important:
- Save your frontend URL: `https://__________.onrender.com`

---

## 🔗 Step 4: Connect Services (5 minutes)

### Update Backend CORS:
1. Go to backend service on Render
2. Environment → Edit `ALLOWED_ORIGINS`
3. Change from:
   ```
   ALLOWED_ORIGINS=http://localhost:5173
   ```
4. To:
   ```
   ALLOWED_ORIGINS=https://[YOUR_FRONTEND_URL].onrender.com,http://localhost:5173
   ```

**Use frontend URL from Step 3!**

### Wait:
- Backend will redeploy (~5 minutes)
- Check logs to confirm restart

---

## ✅ Step 5: Test (10 minutes)

### Test Checklist:

- [ ] Visit frontend URL → Loads
- [ ] Login as student → Works
- [ ] Browse courses → Shows courses
- [ ] Enroll in course → Success
- [ ] Rate course → Success
- [ ] Logout → Works
- [ ] Login as admin → Works
- [ ] Manage courses → Works
- [ ] Manage categories → Works

### If All Pass: 🎉 **SUCCESS!**

---

## 📝 Your Deployment Info

Fill this out as you deploy:

```
┌─────────────────────────────────────────────┐
│         DEPLOYMENT INFORMATION              │
├─────────────────────────────────────────────┤
│ RAILWAY MYSQL:                              │
│ URL: railway.app/project/________________   │
│ Database: ________________________________  │
│ User: ____________________________________  │
│                                             │
│ RENDER BACKEND:                             │
│ URL: https://___________________.onrender.com│
│                                             │
│ RENDER FRONTEND:                            │
│ URL: https://___________________.onrender.com│
│                                             │
│ DEPLOYMENT DATE: ___________________        │
└─────────────────────────────────────────────┘
```

---

## ⚡ Quick Troubleshooting

### Backend Build Failed
```
# Check Docker build logs in Render
# Common fixes:
1. Verify Dockerfile exists in course-enrollment-backend/
2. Check .dockerignore file exists
3. Ensure Maven can download dependencies
4. Wait and retry (temporary network issues)
```

### Can't Connect to Database
```
1. Check Railway MySQL is running (green status)
2. Verify DATABASE_URL format
3. Check username/password (no spaces!)
4. Wait 1 minute and retry
```

### CORS Error
```
1. Verify frontend URL in ALLOWED_ORIGINS
2. Must include https://
3. No trailing slash
4. Comma-separated for multiple origins
```

### Frontend Shows Blank Page
```
1. Check browser console (F12)
2. Verify VITE_API_URL is correct
3. Check backend is running (visit URL)
4. Clear browser cache
```

---

## 💰 Cost Summary

| Service | Cost |
|---------|------|
| Railway MySQL | FREE ($5 credit/month) |
| Render Backend | FREE (750 hrs/month) |
| Render Frontend | FREE (100GB bandwidth) |
| **TOTAL** | **$0/month** ✅ |

---

## 🔗 Important Links

- **Railway Dashboard**: https://railway.app/dashboard
- **Render Dashboard**: https://dashboard.render.com
- **Your Frontend**: https://_________________.onrender.com
- **Your Backend**: https://_________________.onrender.com

---

## 📞 Emergency Contacts

### If Something Breaks:

**Check Logs**:
- Railway: Click service → "Deployments" → "View Logs"
- Render: Click service → "Logs" tab

**Restart Service**:
- Railway: "Deployments" → "Restart"
- Render: "Manual Deploy" → "Clear build cache & deploy"

**Rollback**:
- Render: "Manual Deploy" → Select previous deployment

---

## 🎯 Success Indicators

When deployment is successful, you'll see:

✅ Railway MySQL: "Active" status
✅ Render Backend: "Live" status, logs show "Started Application"
✅ Render Frontend: "Live" status
✅ Frontend loads without errors
✅ Can login and use all features
✅ Database tables created automatically

---

## 📊 Monitoring

### Daily:
- Check if site is accessible
- Test login functionality

### Weekly:
- Check Railway credit usage
- Check Render hours used
- Review error logs

### Monthly:
- Export database backup
- Review performance
- Check for security updates

---

## 🚀 After Deployment

### Share Your Work:
```
Portfolio: Add link to your website
Resume: Add "Deployed full-stack application"
LinkedIn: Post about your achievement
GitHub: Add live demo link to README
```

### Keep Improving:
- Monitor user feedback
- Fix bugs promptly
- Add new features
- Optimize performance

---

**Last Updated**: December 10, 2025
**Estimated Total Time**: 55 minutes
**Difficulty**: Medium
**Success Rate**: High (with this guide!)

