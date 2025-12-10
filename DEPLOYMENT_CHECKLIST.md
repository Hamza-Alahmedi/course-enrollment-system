# ✅ Render Deployment Checklist

Use this checklist when you're ready to deploy.

## 📋 Pre-Deployment

- [ ] Code is working locally
- [ ] All changes committed to Git
- [ ] Code pushed to GitHub
- [ ] GitHub repository is public (or you have Render Pro)
- [ ] Decided on database solution:
  - [ ] Option A: PostgreSQL on Render (recommended)
  - [ ] Option B: External MySQL service
  - [ ] Option C: Other database solution

## 🗄️ Database Setup

### If Using PostgreSQL on Render:
- [ ] Convert application to PostgreSQL (ask me for help)
- [ ] Create PostgreSQL database on Render
- [ ] Note down the internal database URL
- [ ] Test connection (optional)

### If Using External MySQL:
- [ ] Sign up for MySQL hosting service
- [ ] Create database
- [ ] Create user with privileges
- [ ] Export local data: Run `export-database.bat`
- [ ] Import data to cloud database
- [ ] Test connection from local machine

## 🖥️ Backend Deployment

- [ ] Go to https://dashboard.render.com
- [ ] Click "New +" → "Web Service"
- [ ] Connect GitHub repository
- [ ] Configure service:
  - [ ] Name: `course-enrollment-backend`
  - [ ] Root Directory: `course-enrollment-backend`
  - [ ] Build Command: `./mvnw clean install -DskipTests`
  - [ ] Start Command: `java -jar target/Course-Enrollment-System-0.0.1-SNAPSHOT.jar`
- [ ] Add environment variables:
  - [ ] `PORT` = `8080`
  - [ ] `DATABASE_URL` = (your database connection string)
  - [ ] `DB_USERNAME` = (your database username)
  - [ ] `DB_PASSWORD` = (your database password)
  - [ ] `ALLOWED_ORIGINS` = `http://localhost:5173` (update later)
- [ ] Click "Create Web Service"
- [ ] Wait for deployment (~15 minutes)
- [ ] Note your backend URL: `https://_____.onrender.com`
- [ ] Test backend health: Visit your backend URL

## 🌐 Frontend Deployment

- [ ] Go to Render Dashboard
- [ ] Click "New +" → "Static Site"
- [ ] Connect same GitHub repository
- [ ] Configure service:
  - [ ] Name: `course-enrollment-frontend`
  - [ ] Root Directory: `student-frontend`
  - [ ] Build Command: `npm install && npm run build`
  - [ ] Publish Directory: `dist`
- [ ] Add environment variable:
  - [ ] `VITE_API_URL` = (your backend URL from above)
- [ ] Click "Create Static Site"
- [ ] Wait for deployment (~10 minutes)
- [ ] Note your frontend URL: `https://_____.onrender.com`

## 🔗 Connect Frontend & Backend

- [ ] Go to backend service settings on Render
- [ ] Update `ALLOWED_ORIGINS` environment variable:
  - [ ] Add your frontend URL
  - [ ] Format: `https://your-frontend.onrender.com,http://localhost:5173`
- [ ] Save changes
- [ ] Wait for automatic redeploy (~5 minutes)

## 🧪 Testing

- [ ] Visit your frontend URL
- [ ] Test login page loads
- [ ] Test student login
  - [ ] Can see dashboard
  - [ ] Can browse courses
  - [ ] Can enroll in courses
  - [ ] Can rate courses
- [ ] Test admin login
  - [ ] Can see admin dashboard
  - [ ] Can manage categories
  - [ ] Can manage courses
- [ ] Test logout
- [ ] Test registration (if applicable)

## 🐛 Troubleshooting (If Issues Occur)

### Backend Issues:
- [ ] Check Render logs for errors
- [ ] Verify database connection string
- [ ] Verify database is accessible
- [ ] Check environment variables are set correctly
- [ ] Verify build completed successfully

### Frontend Issues:
- [ ] Check browser console for errors
- [ ] Verify `VITE_API_URL` is correct
- [ ] Check network tab for API calls
- [ ] Verify backend CORS includes frontend URL

### Connection Issues:
- [ ] Verify backend URL in frontend env var
- [ ] Verify frontend URL in backend CORS
- [ ] Check both services are running (not spun down)
- [ ] Check browser console for CORS errors

## 📊 Post-Deployment

- [ ] Services are running
- [ ] All features work
- [ ] Note down your URLs:
  - Frontend: `https://_____________________`
  - Backend: `https://_____________________`
- [ ] Optional: Set up custom domain
- [ ] Optional: Monitor performance
- [ ] Optional: Set up database backups

## ⚠️ Important Notes

**Free Tier Limitations:**
- Services sleep after 15 minutes of inactivity
- First request takes ~30 seconds to wake up
- 750 hours/month limit (shared across services)

**Keep in Mind:**
- Both services share the 750 hours on free tier
- Database is separate (has its own limits)
- Monitor your usage in Render dashboard

## 🎉 Success!

Once all checkboxes are checked, your application is live on the internet!

Share your frontend URL with others to show your project.

---

**Need Help?** 
- See `RENDER_DEPLOYMENT_GUIDE.md` for detailed instructions
- See `RENDER_PREPARATION_SUMMARY.md` for what's been prepared
- Ask me if you get stuck!

**Last Updated**: December 9, 2025

