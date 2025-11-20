package com.hamza.courseenrollmentsystem.repository;

import com.hamza.courseenrollmentsystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> { }
