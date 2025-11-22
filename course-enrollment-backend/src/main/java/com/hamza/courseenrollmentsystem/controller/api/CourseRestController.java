package com.hamza.courseenrollmentsystem.controller.api;

import com.hamza.courseenrollmentsystem.dto.CourseDto;
import com.hamza.courseenrollmentsystem.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CourseRestController {

    private final CourseService courseService;

    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseService.findAll();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        try {
            CourseDto course = courseService.findById(id);
            return ResponseEntity.ok(course);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CourseDto>> getCoursesByCategory(@PathVariable Long categoryId) {
        List<CourseDto> courses = courseService.findByCategoryId(categoryId);
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createCourse(@Valid @RequestBody CourseDto courseDto) {
        try {
            CourseDto createdCourse = courseService.create(courseDto);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Course created successfully");
            response.put("data", createdCourse);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseDto courseDto) {
        try {
            CourseDto updatedCourse = courseService.update(id, courseDto);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Course updated successfully");
            response.put("data", updatedCourse);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCourse(@PathVariable Long id) {
        try {
            courseService.delete(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Course deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}

