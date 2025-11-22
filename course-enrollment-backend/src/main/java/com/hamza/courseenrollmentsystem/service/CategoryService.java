package com.hamza.courseenrollmentsystem.service;

import com.hamza.courseenrollmentsystem.dto.CategoryDto;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();
    CategoryDto findById(Long id);
    CategoryDto create(CategoryDto dto);
    CategoryDto update(Long id, CategoryDto dto);
    void delete(Long id);
}

