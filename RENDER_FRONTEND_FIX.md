# 🎯 EXACT Render Configuration for Your Frontend

## ✅ Your Actual Folder Structure:
```
Online Course Enrolment System/
└── student-frontend/          ← Your frontend is HERE
    ├── package.json
    ├── vite.config.js
    ├── src/
    └── dist/ (created after build)
```

---

## 🔧 EXACT Render Settings (Copy These Values)

Go to: **Render Dashboard → Your Frontend Service → Settings**

### 1. Root Directory:
```
student-frontend
```

### 2. Build Command:
```
npm install && npm run build
```

### 3. Publish Directory:
```
dist
```

**That's it!** These 3 settings are all you need.

---

## 📋 Visual Guide - Where to Enter These:

```
Render Dashboard
  → Click your Frontend Service
    → Click "Settings" (left sidebar)
      → Scroll to "Build & Deploy" section
        
        ┌─────────────────────────────────────┐
        │ Root Directory                      │
        │ student-frontend                    │ ← Type this
        └─────────────────────────────────────┘
        
        ┌─────────────────────────────────────┐
        │ Build Command                       │
        │ npm install && npm run build        │ ← Type this
        └─────────────────────────────────────┘
        
        ┌─────────────────────────────────────┐
        │ Publish Directory                   │
        │ dist                                │ ← Type this
        └─────────────────────────────────────┘
        
        [Save Changes] ← Click this
```

---

## ⚠️ Common Mistake:

**WRONG:**
```
Root Directory: course-enrollment-frontend  ❌
Root Directory: /student-frontend           ❌
Root Directory: ./student-frontend          ❌
```

**CORRECT:**
```
Root Directory: student-frontend            ✅
```

---

## 🎯 After Clicking "Save Changes":

1. Render will **automatically redeploy**
2. Watch the logs - you should see:
   ```
   ==> Cloning from GitHub...
   ==> Entering directory student-frontend
   ==> Running 'npm install'
   ==> Running 'npm run build'
   ==> Build successful!
   ==> Deploying...
   ```
3. Your frontend will be live! 🚀

---

## 🐛 If You Still Get "vite: not found":

**Double-check these:**

1. **Root Directory** = `student-frontend` (exactly, no slashes)
2. **Build Command** = `npm install && npm run build`
3. **You clicked "Save Changes"**
4. **Render redeployed** (check logs for new deployment)

---

## ✅ Your package.json is Perfect!

Your `package.json` already has everything needed:
```json
{
  "scripts": {
    "build": "vite build",  ✅
    "start": "vite preview --port $PORT --host 0.0.0.0"  ✅
  },
  "devDependencies": {
    "vite": "^7.2.4"  ✅
  }
}
```

No code changes needed - just fix the Render settings!

---

**Update the Root Directory to `student-frontend` and save!**

