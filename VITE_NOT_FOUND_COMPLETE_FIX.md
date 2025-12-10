# 🚨 VITE NOT FOUND - COMPLETE TROUBLESHOOTING

## ✅ What I Just Fixed:
Moved `vite` from `devDependencies` to `dependencies` in your `package.json`.

**Why?** Some deployment platforms skip `devDependencies` in production, causing Vite to not be installed.

---

## 🎯 NOW DO THIS (Step by Step):

### Step 1: Commit and Push the Fixed package.json

```powershell
cd "C:\Users\hamza\OneDrive\Desktop\Online Course Enrolment System"
git add student-frontend/package.json
git commit -m "Move vite to dependencies for Render deployment"
git push origin main
```

---

### Step 2: Check Your Render Service Type

**CRITICAL:** Are you using a **Web Service** or **Static Site**?

#### ✅ If You Created a STATIC SITE:

Go to Render Dashboard → Your Frontend → Settings:

```
Root Directory:      student-frontend
Build Command:       npm install && npm run build
Publish Directory:   dist
```

**No Start Command needed** - Static Sites don't use it!

#### ❌ If You Created a WEB SERVICE (This is Wrong!):

**You need to DELETE it and create a STATIC SITE instead!**

**Why?** Frontend doesn't need a web server - it's just HTML/CSS/JS files.

---

### Step 3: Verify Render Configuration

#### Option A: You Have a STATIC SITE (Correct)

1. Go to **Render Dashboard**
2. Click your frontend service
3. Look at the top - it should say **"Static Site"** (not "Web Service")
4. Go to **Settings**
5. Verify:
   ```
   Root Directory:      student-frontend
   Build Command:       npm install && npm run build
   Publish Directory:   dist
   ```
6. Click **"Save Changes"**

#### Option B: You Have a WEB SERVICE (Wrong - Fix This)

**You created the wrong service type!** Here's how to fix:

1. **Delete the Web Service:**
   - Go to your frontend Web Service
   - Settings → scroll to bottom
   - Click "Delete Web Service"

2. **Create a Static Site:**
   - Dashboard → "New +" → **"Static Site"** (NOT Web Service!)
   - Connect your repository
   - Configure:
     ```
     Name: course-enrollment-frontend
     Branch: main
     Root Directory: student-frontend
     Build Command: npm install && npm run build
     Publish Directory: dist
     ```
   - Add Environment Variable:
     ```
     VITE_API_BASE_URL=https://your-backend.onrender.com
     ```
   - Click "Create Static Site"

---

## 🔍 How to Tell What You Have

### Check 1: Service Type

In Render Dashboard, look at your frontend service:

**Static Site:**
```
┌─────────────────────────────────────┐
│ 🌐 course-enrollment-frontend       │
│ Static Site                         │ ← Should say this!
└─────────────────────────────────────┘
```

**Web Service (WRONG):**
```
┌─────────────────────────────────────┐
│ 🖥️ course-enrollment-frontend       │
│ Web Service                         │ ← Wrong type!
└─────────────────────────────────────┘
```

### Check 2: Settings Available

**Static Site has:**
- ✅ Root Directory
- ✅ Build Command
- ✅ Publish Directory
- ❌ NO "Start Command" field

**Web Service has:**
- ✅ Root Directory
- ✅ Build Command
- ✅ Start Command ← If you see this, it's a Web Service!
- ❌ NO "Publish Directory"

---

## 🎯 The Most Common Issue:

**You probably created a WEB SERVICE instead of a STATIC SITE!**

For React frontends on Render:
- ❌ **Wrong:** Web Service
- ✅ **Correct:** Static Site

### Why This Matters:

**Web Service:**
- Expects a server to run (needs start command)
- Tries to run `npm start`
- Charges for compute time
- More complex

**Static Site:**
- Just builds and serves files
- No start command needed
- Free bandwidth
- Simple and correct!

---

## 🔧 Complete Fix (If You Have Web Service):

### 1. Delete Current Service
```
Render Dashboard
  → Click your frontend service
  → Settings
  → Scroll to bottom
  → "Delete Web Service"
  → Confirm deletion
```

### 2. Create New Static Site
```
Render Dashboard
  → New + → Static Site (NOT Web Service!)
  → Connect GitHub repository
  → Configure:
     Name: course-enrollment-frontend
     Branch: main
     Root Directory: student-frontend
     Build Command: npm install && npm run build
     Publish Directory: dist
  → Environment:
     VITE_API_BASE_URL = https://your-backend.onrender.com
  → Create Static Site
```

### 3. Wait for Build
- Takes 2-3 minutes
- Check logs for success
- Get your frontend URL

---

## ✅ After These Fixes:

### What Should Happen:

```bash
==> Cloning repository...
==> Entering directory student-frontend
==> Running 'npm install'
    Added 250 packages
    ✓ Installed vite@7.2.4          ← Should see this!
==> Running 'npm run build'
    vite v7.2.4 building...
    ✓ built in 3s
==> Deploying...
✓ Deploy successful!
```

---

## 🐛 Still Getting "vite not found"?

### Try This Build Command Instead:

```bash
npm ci && npm run build
```

**Why?** `npm ci` is stricter and ensures clean install.

---

## 📋 Final Checklist

- [ ] Committed and pushed updated package.json
- [ ] Verified service type (should be **Static Site**)
- [ ] Set Root Directory: `student-frontend`
- [ ] Set Build Command: `npm install && npm run build`
- [ ] Set Publish Directory: `dist`
- [ ] No "Start Command" field (Static Sites don't have this)
- [ ] Added `VITE_API_BASE_URL` environment variable
- [ ] Saved changes and redeployed

---

## 🎯 What to Do Right Now:

1. **Commit the package.json change** (commands above)
2. **Check if you have Static Site or Web Service**
3. **If Web Service: Delete it and create Static Site**
4. **If Static Site: Verify Root Directory is `student-frontend`**
5. **Trigger manual deploy** (Manual Deploy → Deploy)

---

## 💡 Most Likely Solution:

**You need to create a STATIC SITE, not a Web Service!**

That's why you're getting "start command not found" errors. Static Sites don't use start commands!

---

**After you commit the package.json change, verify your service type and reconfigure!** 🚀

