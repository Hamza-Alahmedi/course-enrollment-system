-- Database Schema Initialization Script
-- Run this if you need to recreate the database from scratch

-- Drop existing tables (in correct order due to foreign keys)
DROP TABLE IF EXISTS feedback;
DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create categories table
CREATE TABLE categories (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create courses table
CREATE TABLE courses (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    instructor_api_id VARCHAR(255),
    category_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_course_category FOREIGN KEY (category_id) REFERENCES categories(id)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create enrollments table
CREATE TABLE enrollments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    enrollment_date DATETIME(6),
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_enrollment_user FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_enrollment_course FOREIGN KEY (course_id) REFERENCES courses(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY uk_user_course (user_id, course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create feedback table
CREATE TABLE feedback (
    id BIGINT NOT NULL AUTO_INCREMENT,
    rating INT,
    comment TEXT,
    feedback_date DATETIME(6),
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_feedback_user FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_feedback_course FOREIGN KEY (course_id) REFERENCES courses(id)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create indexes for better performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_enrollments_user ON enrollments(user_id);
CREATE INDEX idx_enrollments_course ON enrollments(course_id);
CREATE INDEX idx_feedback_user ON feedback(user_id);
CREATE INDEX idx_feedback_course ON feedback(course_id);
CREATE INDEX idx_courses_category ON courses(category_id);

