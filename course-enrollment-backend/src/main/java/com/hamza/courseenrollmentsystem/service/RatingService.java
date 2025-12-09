package com.hamza.courseenrollmentsystem.service;

import com.hamza.courseenrollmentsystem.dto.AverageRatingDto;
import com.hamza.courseenrollmentsystem.dto.RatingDto;
import com.hamza.courseenrollmentsystem.entity.Course;
import com.hamza.courseenrollmentsystem.entity.Feedback;
import com.hamza.courseenrollmentsystem.entity.User;
import com.hamza.courseenrollmentsystem.repository.CourseRepository;
import com.hamza.courseenrollmentsystem.repository.EnrollmentRepository;
import com.hamza.courseenrollmentsystem.repository.FeedbackRepository;
import com.hamza.courseenrollmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RatingService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    /**
     * Get average rating for a course
     */
    public AverageRatingDto getAverageRating(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            return new AverageRatingDto(null, "Course not found");
        }

        Double avgRating = feedbackRepository.findAverageRatingByCourseId(courseId);

        if (avgRating == null) {
            return new AverageRatingDto(0.0, "No ratings yet");
        }

        return new AverageRatingDto(avgRating, "Success");
    }

    /**
     * Get user's rating for a course
     */
    public RatingDto getUserRating(Long courseId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Feedback feedback = feedbackRepository.findByUserAndCourse(user, course)
                .orElse(null);

        if (feedback != null && feedback.getRating() != null) {
            return new RatingDto(feedback.getRating());
        }

        return new RatingDto(null);
    }

    /**
     * Save or update rating for a course
     */
    @Transactional
    public RatingDto saveRating(Long courseId, String userEmail, Integer rating) {
        // Validate rating
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        // Get user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Check if user is enrolled
        if (!enrollmentRepository.existsByUserAndCourse(user, course)) {
            throw new RuntimeException("You must be enrolled in this course to rate it");
        }

        // Find existing feedback or create new
        Feedback feedback = feedbackRepository.findByUserAndCourse(user, course)
                .orElse(new Feedback());

        // Update rating
        feedback.setRating(rating);
        feedback.setUser(user);
        feedback.setCourse(course);
        feedback.setFeedbackDate(LocalDateTime.now());

        feedbackRepository.save(feedback);

        return new RatingDto(rating);
    }
}

