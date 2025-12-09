# Course Rating Feature - Implementation Guide

## Overview
This document describes the implementation of the course rating feature for the Course Enrollment System.

## Features Implemented

### 1. **Student Rating Functionality**
- Students can rate courses they are enrolled in (1-5 stars)
- Rating modal with hover effects and interactive stars
- Students can view and update their own ratings
- Average rating display on each course card

### 2. **Admin Rating Display**
- Admin can view average ratings for all courses
- Ratings displayed with star visualization
- Numeric rating score shown next to stars

### 3. **Backend REST API**
Three REST endpoints implemented:

#### GET `/api/courses/{id}/rating/avg`
Returns the average rating for a course.

**Response:**
```json
{
  "averageRating": 4.5,
  "message": "Success"
}
```

#### GET `/api/courses/{id}/rating/me`
Returns the current authenticated user's rating for a course.

**Response:**
```json
{
  "rating": 5
}
```

#### POST `/api/courses/{id}/rating`
Saves or updates a user's rating for a course.

**Request Body:**
```json
{
  "rating": 5
}
```

**Response:**
```json
{
  "rating": 5
}
```

## Database Schema

### Feedback Table (Updated)
The existing `feedback` table is used to store ratings:

```sql
CREATE TABLE feedback (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    rating INTEGER,           -- 1-5 star rating
    comment TEXT,            -- Optional text feedback
    feedback_date DATETIME,
    user_id BIGINT,
    course_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);
```

## Files Created/Modified

### New Files Created:

1. **DTOs**
   - `RatingDto.java` - DTO for rating requests/responses
   - `AverageRatingDto.java` - DTO for average rating responses

2. **Service**
   - `RatingService.java` - Business logic for rating operations

3. **Controller**
   - `RatingApiController.java` - REST API endpoints for ratings

4. **Scripts**
   - `run-rating-app.bat` - Helper script to build and run the application

### Modified Files:

1. **Repositories**
   - `FeedbackRepository.java` - Added rating query methods
   - `EnrollmentRepository.java` - Added enrollment check methods

2. **Controllers**
   - `StudentController.java` - Updated to pass enrollments to view

3. **Templates**
   - `student_dashboard.html` - Complete rewrite with rating UI
   - `admin/courses.html` - Added rating column and display

## How to Test

### Prerequisites
1. Ensure you have at least one admin user and one student user
2. Create some courses and categories
3. Enroll a student in at least one course

### Testing Steps

#### 1. Start the Application
Run the application using:
```bash
.\run-rating-app.bat
```

Or if Maven is in your PATH:
```bash
mvn spring-boot:run
```

#### 2. Test as Student
1. Login as a student (e.g., `student@example.com`)
2. You should see your enrolled courses on the dashboard
3. Click "Rate this Course" button on any course
4. A modal will appear with 5 stars
5. Hover over stars to see the hover effect
6. Click on a star (1-5) to select your rating
7. Click "Submit" to save your rating
8. The page will refresh and show:
   - Your rating (marked as "Your rating:")
   - The updated average rating for the course

#### 3. Test as Admin
1. Logout and login as an admin (e.g., `admin@example.com`)
2. Click "Manage Courses" from the admin dashboard
3. You should see a "Rating" column
4. Courses with ratings will display:
   - Filled stars (gold color)
   - Numeric average (e.g., "4.5")

### Testing REST API Endpoints

You can test the API endpoints using curl or Postman:

#### Get Average Rating
```bash
curl -X GET http://localhost:8080/api/courses/1/rating/avg
```

#### Get My Rating (requires authentication)
```bash
curl -X GET http://localhost:8080/api/courses/1/rating/me \
  --cookie "JSESSIONID=your-session-id"
```

#### Submit Rating (requires authentication)
```bash
curl -X POST http://localhost:8080/api/courses/1/rating \
  -H "Content-Type: application/json" \
  -d '{"rating": 5}' \
  --cookie "JSESSIONID=your-session-id"
```

## Business Rules

1. **Enrollment Required**: Students can only rate courses they are enrolled in
2. **Rating Range**: Ratings must be between 1 and 5 (inclusive)
3. **Update Allowed**: Students can update their rating at any time
4. **One Rating Per Course**: Each student can have only one rating per course
5. **Authentication Required**: All rating operations require user authentication

## UI/UX Features

### Student Dashboard
- **Responsive Grid Layout**: Courses displayed in a card grid
- **Rating Modal**: Clean modal with large, interactive stars
- **Hover Effects**: Stars highlight on hover before clicking
- **Immediate Feedback**: Ratings update instantly after submission
- **Visual Indicators**: 
  - Empty stars for unrated courses
  - Filled gold stars for ratings
  - Average rating shown with score

### Admin Courses Page
- **Table Format**: Clean table layout with rating column
- **Star Visualization**: Quick visual assessment of course quality
- **Numeric Rating**: Precise average rating score
- **Consistent Design**: Matches existing admin panel style

## Error Handling

The system handles various error scenarios:

1. **Not Enrolled**: Returns 403 Forbidden if user tries to rate a course they're not enrolled in
2. **Invalid Rating**: Returns 400 Bad Request if rating is not between 1-5
3. **Course Not Found**: Returns 404 Not Found if course doesn't exist
4. **Not Authenticated**: Returns 401 Unauthorized if user is not logged in

## Future Enhancements (Optional)

Potential improvements for future versions:

1. **Rating Comments**: Allow text feedback along with ratings
2. **Rating Analytics**: Show rating trends over time
3. **Public Display**: Show ratings to all users (not just enrolled students)
4. **Half Stars**: Support decimal ratings (e.g., 4.5 stars visual)
5. **Rating Count**: Display number of ratings received
6. **Rating Distribution**: Show breakdown (how many 5-star, 4-star, etc.)
7. **Rating Validation**: Prevent rating before course completion
8. **Email Notifications**: Notify instructors of new ratings

## Troubleshooting

### Issue: Rating doesn't save
**Solution**: Check browser console for errors. Ensure user is logged in and enrolled in the course.

### Issue: Stars don't appear
**Solution**: Check if the browser supports the star character (★). Try refreshing the page.

### Issue: Average rating not updating
**Solution**: Clear browser cache and refresh. Check if API endpoint is working.

### Issue: 404 on API endpoints
**Solution**: Verify the application is running on port 8080. Check SecurityConfig for proper API endpoint permissions.

## Code Structure

```
src/main/java/com/hamza/courseenrollmentsystem/
├── controller/
│   ├── api/
│   │   └── RatingApiController.java    # REST API endpoints
│   └── StudentController.java          # Student dashboard controller
├── service/
│   └── RatingService.java              # Rating business logic
├── repository/
│   ├── FeedbackRepository.java         # Updated with rating queries
│   └── EnrollmentRepository.java       # Updated with enrollment checks
├── dto/
│   ├── RatingDto.java                  # Rating request/response DTO
│   └── AverageRatingDto.java           # Average rating response DTO
└── entity/
    └── Feedback.java                    # Existing entity (uses rating field)

src/main/resources/templates/
├── student/
│   └── student_dashboard.html          # Student UI with rating modal
└── admin/
    └── courses.html                     # Admin UI with rating display
```

## Conclusion

The rating feature is now fully implemented and ready to use. Students can rate courses they're enrolled in, and administrators can view average ratings for all courses. The feature is built with clean code, proper error handling, and a user-friendly interface.

For any issues or questions, refer to the troubleshooting section or check the application logs.

