package com.hamza.courseenrollmentsystem.repository;

import com.hamza.courseenrollmentsystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> { }
