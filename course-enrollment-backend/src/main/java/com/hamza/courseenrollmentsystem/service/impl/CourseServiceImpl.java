package com.hamza.courseenrollmentsystem.service.impl;

import com.hamza.courseenrollmentsystem.dto.CourseDto;
import com.hamza.courseenrollmentsystem.entity.Category;
import com.hamza.courseenrollmentsystem.entity.Course;
import com.hamza.courseenrollmentsystem.repository.CategoryRepository;
import com.hamza.courseenrollmentsystem.repository.CourseRepository;
import com.hamza.courseenrollmentsystem.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    public CourseServiceImpl(CourseRepository courseRepository, CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDto> findAll() {
        return courseRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        return convertToDto(course);
    }

    @Override
    public CourseDto create(CourseDto dto) {
        Course course = new Course();
        mapDtoToEntity(dto, course);
        Course savedCourse = courseRepository.save(course);
        return convertToDto(savedCourse);
    }

    @Override
    public CourseDto update(Long id, CourseDto dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        mapDtoToEntity(dto, course);
        Course updatedCourse = courseRepository.save(course);
        return convertToDto(updatedCourse);
    }

    @Override
    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseDto> findByCategoryId(Long categoryId) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getCategory() != null && course.getCategory().getId().equals(categoryId))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CourseDto convertToDto(Course course) {
        String categoryName = course.getCategory() != null ? course.getCategory().getName() : null;
        Long categoryId = course.getCategory() != null ? course.getCategory().getId() : null;

        return new CourseDto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getInstructorApiId(),
                categoryId,
                categoryName
        );
    }

    private void mapDtoToEntity(CourseDto dto, Course course) {
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setInstructorApiId(dto.getInstructorApiId());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));
            course.setCategory(category);
        } else {
            course.setCategory(null);
        }
    }
}

