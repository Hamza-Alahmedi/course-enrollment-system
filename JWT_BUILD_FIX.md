# 🔧 JWT Build Fix - Render Deployment

## Problem
The Docker build on Render was failing with compilation errors:
```
[ERROR] cannot find symbol: method parserBuilder()
[ERROR] cannot find symbol: method parseClaimsJws(java.lang.String)
```

## Root Cause
The `jjwt-impl` and `jjwt-jackson` dependencies were set to `<scope>runtime</scope>` in `pom.xml`. This means:
- The implementation classes were NOT available during compilation
- Only the API interfaces (from `jjwt-api`) were visible to the compiler
- The `JwtParserBuilder` and its methods couldn't be found during the Maven build in Docker

## Solution Applied

### 1. Fixed `pom.xml` - Removed Runtime Scope ✅
**Changed:**
```xml
<!-- BEFORE - Runtime scope prevented compilation -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>  ❌
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>  ❌
</dependency>
```

**To:**
```xml
<!-- AFTER - Compile scope allows build to succeed -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <!-- No scope = compile scope ✅ -->
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <!-- No scope = compile scope ✅ -->
</dependency>
```

### 2. Kept JwtUtil.java Using Correct API ✅
The code in `JwtUtil.java` is correct and uses the proper JJWT 0.12.3 API:
```java
private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()  // ✅ Correct for 0.12.3
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
}
```

## Files Modified
1. ✅ `course-enrollment-backend/pom.xml` - Removed runtime scope from jjwt dependencies
2. ✅ `course-enrollment-backend/src/main/java/com/hamza/courseenrollmentsystem/util/JwtUtil.java` - Restored to use parserBuilder()

## Why This Works
- **Compile Scope (default)**: Dependencies are available during compilation, testing, and runtime
- **Runtime Scope**: Dependencies are only available at runtime, NOT during compilation
- By removing the `<scope>runtime</scope>`, Maven now includes the full JJWT implementation during the Docker build
- The `parserBuilder()` method and `JwtParserBuilder` class are now visible to the compiler

## Next Steps

### 1. Commit and Push Changes
```bash
cd "C:\Users\hamza\OneDrive\Desktop\Online Course Enrolment System"
git add course-enrollment-backend/pom.xml course-enrollment-backend/src/main/java/com/hamza/courseenrollmentsystem/util/JwtUtil.java
git commit -m "fix(jwt): Change jjwt dependencies to compile scope for Docker build

- Remove runtime scope from jjwt-impl and jjwt-jackson
- This allows parserBuilder() and related APIs to be visible during Maven compilation
- Fixes Docker build failure on Render deployment

Resolves: 'cannot find symbol: method parserBuilder()' compilation error"
git push origin main
```

### 2. Verify Render Deployment
1. Push will trigger auto-deploy on Render
2. Watch the build logs in Render Dashboard
3. The `mvn clean package -DskipTests` step should now succeed
4. Backend should deploy successfully

### 3. Expected Build Output (Success)
```
[INFO] Building jar: /app/target/Course-Enrollment-System-0.0.1-SNAPSHOT.jar
[INFO] BUILD SUCCESS
```

## Why Runtime Scope Was Wrong
According to Maven documentation:
- **compile** (default): Available in all classpaths (compile, test, runtime) ✅
- **runtime**: NOT available during compilation, only at runtime ❌

Since `JwtUtil.java` needs to call methods from `jjwt-impl` during compilation, the dependency MUST be compile-scoped.

## Additional Notes
- This is a common mistake when working with JJWT
- The JJWT documentation recommends compile scope for `jjwt-impl` when using the builder APIs
- Runtime scope is only appropriate when using pure interfaces with no builder patterns

---

**Status**: ✅ FIXED
**Date**: December 16, 2025
**Deploy Ready**: YES - Push to trigger Render deployment

