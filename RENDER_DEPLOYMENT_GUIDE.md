# Render Deployment Guide for Course Enrollment System

This guide will help you deploy your Course Enrollment System on Render.com.

## Prerequisites

- GitHub account
- Render account (sign up at https://render.com)
- MySQL database running locally (for now)

## Architecture

- **Backend**: Spring Boot application (Java 17)
- **Frontend**: React + Vite application
- **Database**: MySQL (running locally on your machine)

## Important Notes

⚠️ **Database Connection**: Since your MySQL database is running locally, the backend deployed on Render will need to connect to your local machine. This setup is NOT recommended for production but can work for testing:

### Option 1: Use a Cloud Database (Recommended for Production)
Consider using a cloud MySQL service like:
- **Render PostgreSQL** (free tier available, but requires PostgreSQL instead of MySQL)
- **PlanetScale** (free tier for MySQL)
- **Railway** (offers MySQL)
- **FreeMySQLHosting.net** (free but limited)

### Option 2: Keep Database Local (Testing Only)
If you want to keep using your local MySQL:
1. You'll need to expose your local MySQL to the internet (using ngrok or similar)
2. This is NOT secure and only suitable for testing

## Step-by-Step Deployment

### Part 1: Deploy Backend (Spring Boot)

1. **Push your code to GitHub** (if not already done)

2. **Go to Render Dashboard**
   - Visit https://dashboard.render.com
   - Click "New +" → "Web Service"

3. **Connect Repository**
   - Connect your GitHub account
   - Select your repository
   - Click "Connect"

4. **Configure Backend Service**
   - **Name**: `course-enrollment-backend` (or your preferred name)
   - **Region**: Choose closest to you
   - **Branch**: `main` (or your default branch)
   - **Root Directory**: `course-enrollment-backend`
   - **Environment**: `Java`
   - **Build Command**: `./mvnw clean install -DskipTests`
   - **Start Command**: `java -jar target/Course-Enrollment-System-0.0.1-SNAPSHOT.jar`
   - **Instance Type**: Free

5. **Set Environment Variables**
   Click "Advanced" and add these environment variables:
   
   ```
   PORT=8080
   DATABASE_URL=jdbc:mysql://YOUR_MYSQL_HOST:3306/online_course_db
   DB_USERNAME=course_user
   DB_PASSWORD=Test123!#
   ALLOWED_ORIGINS=https://your-frontend-app.onrender.com
   ```
   
   **Note**: Replace `YOUR_MYSQL_HOST` with:
   - Your local IP if exposing local MySQL (not recommended)
   - Cloud database host if using cloud MySQL (recommended)

6. **Deploy**
   - Click "Create Web Service"
   - Wait for the build to complete (10-15 minutes first time)
   - Note down your backend URL: `https://course-enrollment-backend-XXXX.onrender.com`

### Part 2: Deploy Frontend (React + Vite)

1. **Go to Render Dashboard**
   - Click "New +" → "Static Site"

2. **Connect Repository**
   - Select your repository again
   - Click "Connect"

3. **Configure Frontend Service**
   - **Name**: `course-enrollment-frontend` (or your preferred name)
   - **Branch**: `main`
   - **Root Directory**: `student-frontend`
   - **Build Command**: `npm install && npm run build`
   - **Publish Directory**: `dist`

4. **Set Environment Variables**
   Click "Advanced" and add:
   
   ```
   VITE_API_URL=https://your-backend-app.onrender.com
   ```
   
   **Replace** `your-backend-app` with your actual backend URL from Part 1

5. **Deploy**
   - Click "Create Static Site"
   - Wait for the build to complete (5-10 minutes)
   - Your frontend will be live at: `https://course-enrollment-frontend-XXXX.onrender.com`

### Part 3: Update Backend CORS

After frontend is deployed:

1. Go to your backend service on Render
2. Update the `ALLOWED_ORIGINS` environment variable:
   ```
   ALLOWED_ORIGINS=https://your-actual-frontend-url.onrender.com,http://localhost:5173
   ```
3. Render will automatically redeploy

## Testing Your Deployment

1. Visit your frontend URL
2. Try logging in
3. Test all features

## Troubleshooting

### Backend Won't Start
- Check Render logs
- Verify all environment variables are set correctly
- Ensure database is accessible

### Frontend Can't Connect to Backend
- Check CORS settings
- Verify `VITE_API_URL` is correct
- Check browser console for errors

### Database Connection Issues
- Verify database is accessible from internet
- Check firewall settings
- Verify credentials

## Free Tier Limitations

⚠️ **Render Free Tier**:
- Services spin down after 15 minutes of inactivity
- First request after spin-down takes ~30 seconds
- 750 hours/month free (enough for one service)
- Services share the 750 hours if you have multiple

## Alternative: Use PostgreSQL Instead of MySQL

If database connectivity is an issue, consider:

1. **Use Render PostgreSQL** (free tier)
   - Change dependency in `pom.xml`:
     ```xml
     <dependency>
       <groupId>org.postgresql</groupId>
       <artifactId>postgresql</artifactId>
     </dependency>
     ```
   - Update `application.properties`:
     ```properties
     spring.datasource.url=${DATABASE_URL}
     spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
     ```

2. Create PostgreSQL database on Render:
   - Dashboard → "New +" → "PostgreSQL"
   - Free tier available
   - Copy the "Internal Database URL"
   - Use it as `DATABASE_URL` environment variable

## Next Steps After Deployment

1. ✅ Test all functionality
2. ✅ Set up custom domain (optional)
3. ✅ Configure HTTPS (automatic on Render)
4. ✅ Monitor logs and performance
5. ✅ Set up proper database backup

## Support

If you encounter issues:
- Check Render documentation: https://render.com/docs
- View service logs in Render dashboard
- Check GitHub repository issues

---

**Created**: December 2025
**Last Updated**: December 2025

