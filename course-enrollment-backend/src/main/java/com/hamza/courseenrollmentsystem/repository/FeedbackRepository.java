package com.hamza.courseenrollmentsystem.repository;

import com.hamza.courseenrollmentsystem.entity.Feedback;
import com.hamza.courseenrollmentsystem.entity.Course;
import com.hamza.courseenrollmentsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    // Find feedback by user and course
    Optional<Feedback> findByUserAndCourse(User user, Course course);

    // Calculate average rating for a course
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.course.id = :courseId AND f.rating IS NOT NULL")
    Double findAverageRatingByCourseId(@Param("courseId") Long courseId);

    // Check if user has rated a course
    boolean existsByUserAndCourse(User user, Course course);
}

