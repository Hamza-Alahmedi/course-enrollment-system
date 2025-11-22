package com.hamza.courseenrollmentsystem.repository;

import com.hamza.courseenrollmentsystem.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}

