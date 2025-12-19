-- ========================================
-- PASSWORD MIGRATION SCRIPT
-- ========================================
-- This script helps migrate plain text passwords to BCrypt format
--
-- IMPORTANT: You must generate BCrypt hashes first using PasswordHashGenerator.java
-- Then replace the placeholder hashes below with your actual BCrypt hashes
--
-- BCrypt hash format: $2a$10$... (60 characters)
-- ========================================

-- Example: Update admin user password
-- Replace 'YOUR_BCRYPT_HASH_HERE' with actual BCrypt hash from PasswordHashGenerator
-- UPDATE users
-- SET password = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'  -- Example hash for "admin123"
-- WHERE email = 'admin@example.com';

-- Example: Update student user password
-- UPDATE users
-- SET password = '$2a$10$xn3LI/AjqicNYZeQMQMRyuANt6T.Yh3dQw5fYqJtWQ/B8MqQxCKPi'  -- Example hash for "student123"
-- WHERE email = 'student@example.com';

-- ========================================
-- INSTRUCTIONS:
-- ========================================
-- 1. Run PasswordHashGenerator.java to generate BCrypt hashes
-- 2. Copy the BCrypt hash for each password
-- 3. Update the SQL commands above with real email addresses and hashes
-- 4. Run this script on your Railway MySQL database
-- 5. Test login with the new BCrypt passwords
-- ========================================

-- Verify password update (should show BCrypt hash starting with $2a$10$)
SELECT email, password, role
FROM users
ORDER BY role, email;

-- Note: After migration, all new users will automatically have BCrypt passwords
-- Only existing users with plain text passwords need manual migration

