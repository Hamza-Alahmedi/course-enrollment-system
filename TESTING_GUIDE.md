# 🧪 Testing Guide - Course Rating System

## Quick Start Testing

### Step 1: Start Applications
```powershell
# Terminal 1 - Backend
cd "C:\Users\hamza\OneDrive\Desktop\Online Course Enrolment System\course-enrollment-backend"
.\mvnw.cmd spring-boot:run

# Terminal 2 - Frontend
cd "C:\Users\hamza\OneDrive\Desktop\Online Course Enrolment System\student-frontend"
npm run dev
```

### Step 2: Access Applications
- **Frontend**: http://localhost:5173
- **Backend**: http://localhost:8080
- **Rating Test Page**: http://localhost:8080/rating-test.html

---

## 🎯 Test Scenarios

### Scenario 1: View Average Ratings (No Login Required)
1. Open http://localhost:5173
2. Navigate to "Browse Courses" tab
3. **Expected**: See star ratings under each course
4. **Expected**: Courses with no ratings show "No ratings yet"
5. **Expected**: Stars are NOT clickable (read-only mode)

### Scenario 2: Login as Student
1. Click on student name/avatar area (should redirect to login)
   OR manually go to http://localhost:8080/login
2. Use credentials:
   - **Email**: student@example.com (or your registered email)
   - **Password**: password
3. **Expected**: Redirect to student dashboard

### Scenario 3: Enroll in a Course
1. In "Browse Courses" tab
2. Find a course you're NOT enrolled in
3. Click "Enroll Now" button
4. **Expected**: Button changes to "✓ Already Enrolled"
5. **Expected**: Success notification appears
6. **Expected**: Course appears in "My Courses" tab

### Scenario 4: Rate an Enrolled Course
1. Switch to "My Courses" tab
2. Find an enrolled course
3. Look for "Rate this course:" section
4. Hover over stars
5. **Expected**: Stars highlight on hover
6. **Expected**: Cursor changes to pointer
7. Click on a star (e.g., 4th star for 4-star rating)
8. **Expected**: "Rating submitted successfully!" notification
9. **Expected**: "Your rating: 4★" badge appears
10. **Expected**: Average rating updates

### Scenario 5: Update Existing Rating
1. On a course you already rated
2. Click a different star
3. **Expected**: Your rating updates
4. **Expected**: Average rating recalculates
5. **Expected**: Only ONE rating per student (not duplicated)

### Scenario 6: Try Rating Without Enrollment
1. Try to rate a course you're NOT enrolled in
2. **Expected**: Stars should NOT be interactive in Browse tab
3. Only "My Courses" tab allows rating

---

## 🔧 API Testing with Test Page

### Access Test Page
http://localhost:8080/rating-test.html

### Test 1: Get Average Rating
1. Enter Course ID: 1
2. Click "Get Average Rating"
3. **Expected Response**:
```json
{
  "averageRating": 4.5,
  "message": "Success"
}
```

### Test 2: Get User's Rating (Requires Login)
1. Make sure you're logged in
2. Enter Course ID of enrolled course
3. Click "Get My Rating"
4. **Expected Response**:
```json
{
  "rating": 5
}
```

### Test 3: Submit Rating (Requires Login + Enrollment)
1. Login first
2. Enter Course ID of enrolled course
3. Select stars (e.g., 4 stars)
4. Click "Submit Rating"
5. **Expected Response**:
```json
{
  "rating": 4
}
```

---

## 🐛 Error Testing

### Test: Unauthenticated Rating
1. Open incognito/private browser
2. Go to http://localhost:8080/rating-test.html
3. Try "Get My Rating"
4. **Expected**: "Error: Unauthorized. Please login first."

### Test: Rating Without Enrollment
1. Login as student
2. Try to rate a course you're NOT enrolled in
3. **Expected**: "Error: You must be enrolled in this course to rate it"

### Test: Invalid Rating Range
1. Use browser console:
```javascript
fetch('http://localhost:8080/api/courses/1/rating', {
  method: 'POST',
  credentials: 'include',
  headers: {'Content-Type': 'application/json'},
  body: JSON.stringify({rating: 6})
})
```
2. **Expected**: "Rating must be between 1 and 5"

---

## ✅ Validation Checklist

### Visual Validation
- [ ] Stars display correctly (5 stars per course)
- [ ] Filled stars are gold color (#f59e0b)
- [ ] Empty stars are gray (#cbd5e0)
- [ ] Hover effect works on interactive stars
- [ ] Average rating number displays (e.g., "4.5")
- [ ] "Your rating: X★" badge appears after rating
- [ ] Layout is responsive and professional

### Functional Validation
- [ ] Can view ratings without login
- [ ] Cannot submit ratings without login
- [ ] Cannot rate without enrollment
- [ ] Can only rate enrolled courses
- [ ] Rating updates existing rating (doesn't duplicate)
- [ ] Average recalculates after new rating
- [ ] Ratings persist after page refresh
- [ ] Multiple students can rate same course
- [ ] Each student has only 1 rating per course

### Backend Validation
- [ ] Backend running on port 8080
- [ ] Database connection working
- [ ] API endpoints responding
- [ ] CORS headers present
- [ ] Authentication working
- [ ] SQL queries executing correctly

### Frontend Validation
- [ ] Frontend running on port 5173
- [ ] Axios instance configured correctly
- [ ] State management working
- [ ] UI updates reactively
- [ ] Notifications appear
- [ ] No console errors

---

## 📊 Database Verification

### Check Feedback Table
```sql
-- View all ratings
SELECT f.id, u.username, c.title, f.rating, f.feedback_date
FROM feedback f
JOIN users u ON f.user_id = u.id
JOIN courses c ON f.course_id = c.id
ORDER BY f.feedback_date DESC;

-- Check average ratings per course
SELECT c.title, AVG(f.rating) as avg_rating, COUNT(f.rating) as rating_count
FROM courses c
LEFT JOIN feedback f ON c.id = f.course_id
GROUP BY c.id, c.title;

-- Check student's ratings
SELECT c.title, f.rating
FROM feedback f
JOIN courses c ON f.course_id = c.id
WHERE f.user_id = 1; -- Replace with actual student ID
```

---

## 🎭 User Journey Test

### Complete Student Journey
1. **Register** new student account
2. **Login** to system
3. **Browse** available courses
4. **View** ratings on courses (read-only)
5. **Enroll** in 2-3 courses
6. **Navigate** to "My Courses"
7. **Rate** first course with 5 stars
8. **Rate** second course with 4 stars
9. **Update** first course rating to 3 stars
10. **Switch** to Browse tab
11. **Verify** average ratings updated
12. **Logout** and login as different student
13. **Rate** same courses
14. **Verify** averages reflect both ratings

---

## 🔍 Browser Console Tests

### Check Network Requests
1. Open DevTools (F12)
2. Go to Network tab
3. Filter by "rating"
4. Submit a rating
5. **Expected**: See POST to `/api/courses/{id}/rating`
6. **Expected**: Status 200
7. **Expected**: Response body contains rating

### Check for Errors
1. Open Console tab
2. Look for any red errors
3. **Expected**: No CORS errors
4. **Expected**: No 404 errors
5. **Expected**: Debug logs show "Making request to:"

---

## 📱 Responsive Testing

### Desktop (1920x1080)
- [ ] Stars display properly
- [ ] Course cards well-spaced
- [ ] Rating info readable

### Tablet (768x1024)
- [ ] Grid adjusts to 2 columns
- [ ] Stars still clickable
- [ ] No overlap

### Mobile (375x667)
- [ ] Single column layout
- [ ] Stars large enough to tap
- [ ] Text readable

---

## 🚨 Known Issues & Workarounds

### Issue: "withCredentials" Warning
**Cause**: Axios instance already has withCredentials
**Solution**: Already fixed - using axios instance from `/api/axios.js`

### Issue: Stars Not Interactive
**Cause**: Course not enrolled
**Solution**: Enroll in course first, then go to "My Courses" tab

### Issue: Backend Connection Failed
**Cause**: Backend not running or wrong port
**Solution**: 
```powershell
cd course-enrollment-backend
.\mvnw.cmd spring-boot:run
```

---

## 📈 Performance Testing

### Load Test
1. Create 10 courses
2. Create 5 students
3. Have each student rate all courses
4. **Expected**: No slowdown
5. **Expected**: Averages calculate quickly

### Stress Test
1. Rapidly click stars multiple times
2. **Expected**: Only one request sent (debounced)
3. **Expected**: UI doesn't freeze

---

## ✨ Success Indicators

### You know it's working when:
✅ You see gold stars on course cards
✅ Clicking stars submits rating
✅ "Your rating: X★" badge appears
✅ Average rating updates immediately
✅ Rating persists after refresh
✅ Other students see updated averages
✅ No console errors
✅ Smooth animations and transitions

---

## 📞 Support & Debugging

### If Something Doesn't Work:

1. **Check Backend Logs**
   - Look in terminal running backend
   - Search for errors or exceptions

2. **Check Browser Console**
   - F12 → Console tab
   - Look for red errors

3. **Check Network Tab**
   - F12 → Network tab
   - Filter by "api"
   - Check status codes

4. **Verify Database**
   - Check if feedback table exists
   - Verify data is being saved

5. **Clear Browser Cache**
   - Hard refresh: Ctrl+Shift+R
   - Clear cookies for localhost

6. **Restart Applications**
   - Stop both backend and frontend
   - Start backend first
   - Then start frontend

---

**Testing Completed By**: _______________
**Date**: _______________
**All Tests Passed**: ☐ Yes  ☐ No
**Issues Found**: _______________________________________________

