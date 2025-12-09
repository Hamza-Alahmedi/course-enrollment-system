package com.hamza.courseenrollmentsystem.repository;

import com.hamza.courseenrollmentsystem.entity.Enrollment;
import com.hamza.courseenrollmentsystem.entity.User;
import com.hamza.courseenrollmentsystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // Check if user is enrolled in a course
    boolean existsByUserAndCourse(User user, Course course);

    // Get all enrollments for a user
    List<Enrollment> findByUser(User user);
}

