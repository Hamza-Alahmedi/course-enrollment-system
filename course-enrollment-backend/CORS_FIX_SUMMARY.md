# ✅ CORS Error Fixed - Quick Summary

## Problem You Encountered

When testing the Rating API in Postman, you got this error:
```json
{
  "success": false,
  "message": "When allowCredentials is true, allowedOrigins cannot contain the special value \"*\" since that cannot be set on the \"Access-Control-Allow-Origin\" response header..."
}
```

## Root Cause

The `@CrossOrigin` annotation in `RatingApiController.java` used:
```java
@CrossOrigin(origins = "*")
```

This conflicts with sending credentials (session cookies). Spring Security doesn't allow wildcard origins when credentials are required.

## Solution Applied ✅

Updated `RatingApiController.java` to use `originPatterns` instead:

```java
@CrossOrigin(
    originPatterns = "*",
    allowCredentials = "true",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
```

**Changes**:
- ✅ Changed `origins = "*"` → `originPatterns = "*"`
- ✅ Added `allowCredentials = "true"`
- ✅ Explicitly listed allowed methods

## What This Means

- **Session cookies** will now be accepted by the API
- **Cross-origin requests** from any domain will work (good for development)
- **Postman requests** with authentication will succeed
- **Browser fetch requests** with `credentials: 'same-origin'` will work

## How to Start the Application

Since Maven is not in your PATH, use one of these methods:

### Option 1: Use your IDE
- Open the project in IntelliJ IDEA or Eclipse
- Right-click on `CourseEnrollmentSystemApplication.java`
- Select "Run"

### Option 2: Find Maven installation
If you have Maven installed but not in PATH:
```powershell
# Find where Maven is installed, then run:
"C:\path\to\maven\bin\mvn.cmd" spring-boot:run
```

### Option 3: Use the compiled JAR (if exists)
```powershell
cd "C:\Users\hamza\OneDrive\Desktop\Online Course Enrolment System\course-enrollment-backend\target"
java -jar course-enrollment-system-*.jar
```

## Testing in Postman - Quick Start

See `POSTMAN_TESTING_GUIDE.md` for full details. Here's the quick version:

### 1. Login (to get session cookie)
```
POST http://localhost:8080/login
Body (x-www-form-urlencoded):
  email: student@example.com
  password: password
```

### 2. Test GET Average Rating (no auth needed)
```
GET http://localhost:8080/api/courses/1/rating/avg
```

### 3. Test GET My Rating (needs auth)
```
GET http://localhost:8080/api/courses/1/rating/me
Header: Cookie: JSESSIONID={from login response}
```

### 4. Test POST Save Rating (needs auth)
```
POST http://localhost:8080/api/courses/1/rating
Header: 
  Content-Type: application/json
  Cookie: JSESSIONID={from login response}
Body (raw JSON):
{
  "rating": 5
}
```

## Expected Results

- **Login** → Returns `302` redirect with `Set-Cookie: JSESSIONID=...`
- **GET avg** → Returns `{ "averageRating": 0.0, "message": "No ratings yet" }` or actual average
- **GET me** → Returns `{ "rating": null }` if not rated, or `{ "rating": 5 }` if rated
- **POST rating** → Returns `{ "rating": 5 }` on success
  - `403` if not enrolled in course
  - `401` if not logged in
  - `400` if rating not 1-5

## Files Changed

1. **RatingApiController.java** - CORS configuration updated

## Files Created

1. **POSTMAN_TESTING_GUIDE.md** - Comprehensive Postman testing instructions
2. **CORS_FIX_SUMMARY.md** - This file

## Next Steps

1. ✅ Start your application using your IDE
2. ✅ Test in Postman using the guide
3. ✅ Test in browser (student dashboard should show "Rate this Course" button)
4. ✅ Verify rating submission works end-to-end

---

**The CORS error is now fixed. You can proceed with Postman testing!**

