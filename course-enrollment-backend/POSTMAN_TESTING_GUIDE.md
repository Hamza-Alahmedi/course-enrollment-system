# Postman Testing Guide for Rating API

## CORS Fix Applied ✅

The CORS error you encountered has been **fixed**. The issue was:
- **Problem**: Using `origins = "*"` with `allowCredentials = true` is not allowed
- **Solution**: Changed to `originPatterns = "*"` which allows wildcard patterns with credentials

The updated `RatingApiController.java` now has:
```java
@CrossOrigin(
    originPatterns = "*",
    allowCredentials = "true",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
```

---

## Prerequisites

1. **Application Running**: Ensure the Spring Boot app is running on `http://localhost:8080`
2. **Test Data**: Have at least:
   - One admin user (e.g., `admin@example.com`)
   - One student user (e.g., `student@example.com`)
   - One or more courses
   - Student enrolled in at least one course

---

## Setup Postman Environment

### Create Environment Variables

1. Open Postman → Environments → Create New Environment
2. Add these variables:

| Variable Name | Initial Value | Current Value |
|--------------|---------------|---------------|
| `baseUrl` | `http://localhost:8080` | `http://localhost:8080` |
| `JSESSIONID` | (leave empty) | (will be auto-filled) |
| `courseId` | `1` | `1` |
| `studentEmail` | `student@example.com` | `student@example.com` |
| `studentPassword` | `password` | `password` |

3. Save and select this environment

---

## Test Sequence

### 1. Login as Student

**Purpose**: Authenticate and get session cookie

**Request**:
- **Method**: `POST`
- **URL**: `{{baseUrl}}/login`
- **Body**: `x-www-form-urlencoded`
  ```
  email: {{studentEmail}}
  password: {{studentPassword}}
  ```
- **Settings**: Enable "Automatically follow redirects"

**Tests Script** (Paste in "Tests" tab):
```javascript
// Auto-capture JSESSIONID from response
const cookieJar = pm.cookies;
cookieJar.get('JSESSIONID', function(error, cookie) {
    if (!error && cookie) {
        pm.environment.set('JSESSIONID', cookie);
        console.log('✅ JSESSIONID saved:', cookie);
    }
});

// Alternative: Extract from Set-Cookie header
const setCookie = pm.response.headers.get('set-cookie');
if (setCookie) {
    const match = setCookie.match(/JSESSIONID=([^;]+)/);
    if (match) {
        pm.environment.set('JSESSIONID', match[1]);
        console.log('✅ JSESSIONID from header:', match[1]);
    }
}
```

**Expected Response**:
- Status: `302 Found` (redirect)
- Headers: `Set-Cookie: JSESSIONID=...`
- Redirects to: `/student/dashboard`

**Troubleshooting**:
- ❌ Status `200` with login page → Wrong credentials
- ❌ Status `302` to `/login?error` → Authentication failed

---

### 2. GET Average Rating (Public Endpoint)

**Purpose**: Get the average rating for a course

**Request**:
- **Method**: `GET`
- **URL**: `{{baseUrl}}/api/courses/{{courseId}}/rating/avg`
- **Headers**: None required (public endpoint)

**Expected Response**:
```json
{
  "averageRating": 4.2,
  "message": "Success"
}
```

**If no ratings yet**:
```json
{
  "averageRating": 0.0,
  "message": "No ratings yet"
}
```

**Status Codes**:
- `200 OK` → Success
- `500 Internal Server Error` → Course not found or DB error

---

### 3. GET User's Rating (Authenticated)

**Purpose**: Get the logged-in student's rating for a specific course

**Request**:
- **Method**: `GET`
- **URL**: `{{baseUrl}}/api/courses/{{courseId}}/rating/me`
- **Headers**: 
  - `Cookie: JSESSIONID={{JSESSIONID}}`
  
  Or rely on Postman's automatic cookie handling if login was done in same workspace.

**Expected Responses**:

**User has rated**:
```json
{
  "rating": 5
}
```

**User hasn't rated**:
```json
{
  "rating": null
}
```

**Status Codes**:
- `200 OK` → Success (rating may be null)
- `401 Unauthorized` → Not logged in or session expired
- `500 Internal Server Error` → Server error

**Troubleshooting**:
- ❌ `401 Unauthorized`:
  - Run login request first
  - Check that JSESSIONID is saved in environment
  - Manually add Cookie header if automatic cookies aren't working

---

### 4. POST Save/Update Rating (Authenticated)

**Purpose**: Submit or update a student's rating for a course

**Request**:
- **Method**: `POST`
- **URL**: `{{baseUrl}}/api/courses/{{courseId}}/rating`
- **Headers**: 
  - `Content-Type: application/json`
  - `Cookie: JSESSIONID={{JSESSIONID}}`
- **Body** (raw JSON):
  ```json
  {
    "rating": 5
  }
  ```

**Expected Response**:
```json
{
  "rating": 5
}
```

**Status Codes**:
- `200 OK` → Rating saved/updated successfully
- `400 Bad Request` → Invalid rating (not 1-5 or missing)
- `401 Unauthorized` → Not logged in
- `403 Forbidden` → Student not enrolled in this course
- `500 Internal Server Error` → Server error

**Test Different Ratings**:
- Try values: `1`, `2`, `3`, `4`, `5` (all valid)
- Try invalid: `0`, `6`, `-1`, `null` (should return 400)

**Update Test**:
1. POST with `"rating": 5`
2. GET `/rating/me` → should return `5`
3. POST with `"rating": 3` (update)
4. GET `/rating/me` → should return `3`
5. GET `/rating/avg` → should reflect the new average

---

## Complete Postman Collection

### Import This Collection

Save this as `Rating-API-Tests.postman_collection.json`:

```json
{
  "info": {
    "name": "Course Rating API Tests",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "1. Login as Student",
      "event": [
        {
          "listen": "test",
          "script": {
            "exec": [
              "const setCookie = pm.response.headers.get('set-cookie');",
              "if (setCookie) {",
              "    const match = setCookie.match(/JSESSIONID=([^;]+)/);",
              "    if (match) {",
              "        pm.environment.set('JSESSIONID', match[1]);",
              "        console.log('✅ JSESSIONID saved:', match[1]);",
              "    }",
              "}"
            ]
          }
        }
      ],
      "request": {
        "method": "POST",
        "header": [],
        "body": {
          "mode": "urlencoded",
          "urlencoded": [
            {
              "key": "email",
              "value": "{{studentEmail}}"
            },
            {
              "key": "password",
              "value": "{{studentPassword}}"
            }
          ]
        },
        "url": {
          "raw": "{{baseUrl}}/login",
          "host": ["{{baseUrl}}"],
          "path": ["login"]
        }
      }
    },
    {
      "name": "2. GET Average Rating",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "{{baseUrl}}/api/courses/{{courseId}}/rating/avg",
          "host": ["{{baseUrl}}"],
          "path": ["api", "courses", "{{courseId}}", "rating", "avg"]
        }
      }
    },
    {
      "name": "3. GET My Rating",
      "request": {
        "method": "GET",
        "header": [
          {
            "key": "Cookie",
            "value": "JSESSIONID={{JSESSIONID}}"
          }
        ],
        "url": {
          "raw": "{{baseUrl}}/api/courses/{{courseId}}/rating/me",
          "host": ["{{baseUrl}}"],
          "path": ["api", "courses", "{{courseId}}", "rating", "me"]
        }
      }
    },
    {
      "name": "4. POST Save Rating",
      "event": [
        {
          "listen": "test",
          "script": {
            "exec": [
              "if (pm.response.code === 200) {",
              "    const body = pm.response.json();",
              "    console.log('✅ Rating saved:', body.rating);",
              "}"
            ]
          }
        }
      ],
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          },
          {
            "key": "Cookie",
            "value": "JSESSIONID={{JSESSIONID}}"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"rating\": 5\n}"
        },
        "url": {
          "raw": "{{baseUrl}}/api/courses/{{courseId}}/rating",
          "host": ["{{baseUrl}}"],
          "path": ["api", "courses", "{{courseId}}", "rating"]
        }
      }
    }
  ]
}
```

**To Import**:
1. Open Postman
2. Click "Import" button
3. Paste the JSON above
4. Select your environment

---

## Testing with cURL (Alternative)

### 1. Login and Save Cookie
```powershell
curl -i -X POST "http://localhost:8080/login" `
  -H "Content-Type: application/x-www-form-urlencoded" `
  --data "email=student@example.com&password=password" `
  -c cookies.txt
```

### 2. GET Average Rating
```powershell
curl "http://localhost:8080/api/courses/1/rating/avg"
```

### 3. GET My Rating (with cookie)
```powershell
curl "http://localhost:8080/api/courses/1/rating/me" -b cookies.txt
```

### 4. POST Save Rating (with cookie)
```powershell
curl -X POST "http://localhost:8080/api/courses/1/rating" `
  -H "Content-Type: application/json" `
  -d "{\"rating\":5}" `
  -b cookies.txt -c cookies.txt
```

---

## Common Issues & Solutions

### Issue: 401 Unauthorized on /rating/me or /rating

**Cause**: Session cookie not sent or expired

**Solutions**:
1. Re-run the login request
2. Check that JSESSIONID is in environment variables
3. Manually add Cookie header: `Cookie: JSESSIONID=YOUR_SESSION_ID`
4. In Postman settings, ensure "Automatically follow redirects" is ON
5. Check Postman → Cookies → Manage Cookies → verify JSESSIONID exists for localhost

### Issue: 403 Forbidden on POST /rating

**Cause**: Student not enrolled in the course

**Solution**: 
- Enroll the student in the course first (use admin panel or enrollment API)
- Verify with: `GET /api/students/{studentId}/courses`

### Issue: 400 Bad Request on POST /rating

**Cause**: Invalid rating value

**Solution**: Ensure rating is an integer between 1 and 5

### Issue: CORS Error (should be fixed now)

**Cause**: The original `@CrossOrigin(origins = "*")` conflicted with credentials

**Solution**: ✅ Already fixed with `originPatterns = "*"`

If you still see CORS errors:
- Restart the Spring Boot app
- Clear browser/Postman cache
- Verify the controller has the updated `@CrossOrigin` annotation

---

## Validation Checklist

After testing, verify:

- [ ] Login returns JSESSIONID cookie
- [ ] GET /rating/avg returns average (0.0 if no ratings)
- [ ] GET /rating/me returns null before rating
- [ ] POST /rating with valid value (1-5) returns 200
- [ ] POST /rating with invalid value returns 400
- [ ] POST /rating without authentication returns 401
- [ ] POST /rating for non-enrolled course returns 403
- [ ] After rating, GET /rating/me returns the saved rating
- [ ] After rating, GET /rating/avg updates to reflect new average
- [ ] Updating rating (POST again) changes the existing rating (not creates duplicate)

---

## Next Steps

1. **Test in Browser**: After Postman tests pass, test in the student dashboard UI
2. **Test Enrollment Check**: Try rating a course the student isn't enrolled in (should get 403)
3. **Test Update**: Rate a course, then rate it again with different value
4. **Test Multiple Students**: Login as different students and rate the same course, verify average updates correctly
5. **Test Admin View**: Login as admin and verify average ratings show on admin course list

---

## Support

If you encounter issues:
1. Check application logs for exceptions
2. Verify database has enrollment records
3. Test with simple course IDs (1, 2, 3)
4. Try browser DevTools Network tab to inspect requests/responses
5. Check SecurityConfig allows /api/** endpoints

---

**✅ CORS Fix Summary**

The CORS error has been resolved by changing:
- ❌ `@CrossOrigin(origins = "*")` 
- ✅ `@CrossOrigin(originPatterns = "*", allowCredentials = "true")`

This allows:
- Session cookies to be sent with requests
- Wildcard origin patterns (for development)
- All HTTP methods (GET, POST, PUT, DELETE, OPTIONS)

**You can now test the rating API in Postman without CORS errors!**

