package com.hamza.courseenrollmentsystem.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDto {
    private Long id;

    @NotBlank(message = "Category name is required")
    private String name;

    private Integer courseCount;

    public CategoryDto() {}

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDto(Long id, String name, Integer courseCount) {
        this.id = id;
        this.name = name;
        this.courseCount = courseCount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }
}

