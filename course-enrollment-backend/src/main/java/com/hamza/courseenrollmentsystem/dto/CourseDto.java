package com.hamza.courseenrollmentsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CourseDto {
    private Long id;

    @NotBlank(message = "Course title is required")
    private String title;

    private String description;

    private String instructorApiId;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    private String categoryName;

    public CourseDto() {}

    public CourseDto(Long id, String title, String description, String instructorApiId, Long categoryId, String categoryName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.instructorApiId = instructorApiId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructorApiId() {
        return instructorApiId;
    }

    public void setInstructorApiId(String instructorApiId) {
        this.instructorApiId = instructorApiId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

