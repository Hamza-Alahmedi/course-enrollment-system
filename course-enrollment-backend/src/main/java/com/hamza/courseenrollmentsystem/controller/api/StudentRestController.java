package com.hamza.courseenrollmentsystem.controller.api;

import com.hamza.courseenrollmentsystem.dto.CourseDto;
import com.hamza.courseenrollmentsystem.entity.Course;
import com.hamza.courseenrollmentsystem.entity.Enrollment;
import com.hamza.courseenrollmentsystem.entity.User;
import com.hamza.courseenrollmentsystem.repository.CourseRepository;
import com.hamza.courseenrollmentsystem.repository.EnrollmentRepository;
import com.hamza.courseenrollmentsystem.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class StudentRestController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentRestController(UserRepository userRepository,
                                  CourseRepository courseRepository,
                                  EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Map<String, Object>> getStudentInfo(@PathVariable Long studentId) {
        try {
            User student = userRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            Map<String, Object> studentInfo = new HashMap<>();
            studentInfo.put("id", student.getId());
            studentInfo.put("username", student.getUsername());
            studentInfo.put("email", student.getEmail());
            studentInfo.put("role", student.getRole());

            return ResponseEntity.ok(studentInfo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<CourseDto>> getStudentCourses(@PathVariable Long studentId) {
        try {
            User student = userRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            List<CourseDto> enrolledCourses = student.getEnrollments().stream()
                    .map(enrollment -> {
                        Course course = enrollment.getCourse();
                        CourseDto dto = new CourseDto();
                        dto.setId(course.getId());
                        dto.setTitle(course.getTitle());
                        dto.setDescription(course.getDescription());
                        dto.setInstructorApiId(course.getInstructorApiId());
                        dto.setCategoryId(course.getCategory().getId());
                        dto.setCategoryName(course.getCategory().getName());
                        return dto;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(enrolledCourses);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<Map<String, Object>> enrollInCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        try {
            User student = userRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            // Check if already enrolled
            boolean alreadyEnrolled = student.getEnrollments().stream()
                    .anyMatch(e -> e.getCourse().getId().equals(courseId));

            if (alreadyEnrolled) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Already enrolled in this course");
                return ResponseEntity.badRequest().body(response);
            }

            // Create new enrollment
            Enrollment enrollment = new Enrollment();
            enrollment.setUser(student);
            enrollment.setCourse(course);
            enrollment.setEnrollmentDate(LocalDateTime.now());
            enrollmentRepository.save(enrollment);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Successfully enrolled in course");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{studentId}/unenroll/{courseId}")
    public ResponseEntity<Map<String, Object>> unenrollFromCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        try {
            User student = userRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            Enrollment enrollment = student.getEnrollments().stream()
                    .filter(e -> e.getCourse().getId().equals(courseId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Not enrolled in this course"));

            enrollmentRepository.delete(enrollment);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Successfully unenrolled from course");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}

