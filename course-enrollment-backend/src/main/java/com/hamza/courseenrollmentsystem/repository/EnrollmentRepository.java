package com.hamza.courseenrollmentsystem.repository;

import com.hamza.courseenrollmentsystem.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}

