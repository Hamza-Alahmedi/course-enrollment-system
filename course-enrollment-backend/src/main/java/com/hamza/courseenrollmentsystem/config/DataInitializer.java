//package com.hamza.courseenrollmentsystem.config;
//
//import com.hamza.courseenrollmentsystem.entity.*;
//import com.hamza.courseenrollmentsystem.repository.*;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//
//@Configuration
//public class DataInitializer {
//
//    @Bean
//    CommandLineRunner initDatabase(UserRepository userRepo,
//                                   CategoryRepository categoryRepo,
//                                   CourseRepository courseRepo,
//                                   EnrollmentRepository enrollmentRepo,
//                                   FeedbackRepository feedbackRepo) {
//        return args -> {
//
//            // Check if data already exists to prevent duplicates
//            if (userRepo.count() > 0) {
//                System.out.println("Data already exists, skipping initialization.");
//                return;
//            }
//
//            // ----- USERS -----
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//            User admin = new User();
//            admin.setUsername("admin");
//            admin.setEmail("admin@example.com");
//            admin.setPassword(encoder.encode("admin123"));
//            admin.setRole("ADMIN");
//
//            User student1 = new User();
//            student1.setUsername("alice");
//            student1.setEmail("alice@example.com");
//            student1.setPassword(encoder.encode("pass123"));
//            student1.setRole("STUDENT");
//
//            User student2 = new User();
//            student2.setUsername("bob");
//            student2.setEmail("bob@example.com");
//            student2.setPassword(encoder.encode("pass123"));
//            student2.setRole("STUDENT");
//
//            User student3 = new User();
//            student3.setUsername("charlie");
//            student3.setEmail("charlie@example.com");
//            student3.setPassword(encoder.encode("pass123"));
//            student3.setRole("STUDENT");
//
//            User student4 = new User();
//            student4.setUsername("diana");
//            student4.setEmail("diana@example.com");
//            student4.setPassword(encoder.encode("pass123"));
//            student4.setRole("STUDENT");
//
//            User student5 = new User();
//            student5.setUsername("eve");
//            student5.setEmail("eve@example.com");
//            student5.setPassword(encoder.encode("pass123"));
//            student5.setRole("STUDENT");
//
//            userRepo.saveAll(Arrays.asList(admin, student1, student2, student3, student4, student5));
//
//            // ----- CATEGORIES -----
//            Category cat1 = new Category();
//            cat1.setName("Programming");
//
//            Category cat2 = new Category();
//            cat2.setName("Design");
//
//            Category cat3 = new Category();
//            cat3.setName("Marketing");
//
//            categoryRepo.saveAll(Arrays.asList(cat1, cat2, cat3));
//
//            // ----- COURSES -----
//            Course course1 = new Course();
//            course1.setTitle("Java Basics");
//            course1.setDescription("Learn the fundamentals of Java programming.");
//            course1.setCategory(cat1);
//
//            Course course2 = new Course();
//            course2.setTitle("Spring Boot Advanced");
//            course2.setDescription("Build REST APIs using Spring Boot.");
//            course2.setCategory(cat1);
//
//            Course course3 = new Course();
//            course3.setTitle("UI/UX Design");
//            course3.setDescription("Learn the principles of good interface design.");
//            course3.setCategory(cat2);
//
//            Course course4 = new Course();
//            course4.setTitle("Digital Marketing 101");
//            course4.setDescription("Basics of online marketing and SEO.");
//            course4.setCategory(cat3);
//
//            Course course5 = new Course();
//            course5.setTitle("React.js Essentials");
//            course5.setDescription("Build dynamic web apps using React.js.");
//            course5.setCategory(cat1);
//
//            courseRepo.saveAll(Arrays.asList(course1, course2, course3, course4, course5));
//
//            // ----- ENROLLMENTS -----
//            Enrollment e1 = new Enrollment();
//            e1.setUser(student1);
//            e1.setCourse(course1);
//            e1.setEnrollmentDate(LocalDateTime.now().minusDays(5));
//
//            Enrollment e2 = new Enrollment();
//            e2.setUser(student2);
//            e2.setCourse(course2);
//            e2.setEnrollmentDate(LocalDateTime.now().minusDays(3));
//
//            Enrollment e3 = new Enrollment();
//            e3.setUser(student3);
//            e3.setCourse(course3);
//            e3.setEnrollmentDate(LocalDateTime.now().minusDays(2));
//
//            Enrollment e4 = new Enrollment();
//            e4.setUser(student4);
//            e4.setCourse(course1);
//            e4.setEnrollmentDate(LocalDateTime.now().minusDays(1));
//
//            Enrollment e5 = new Enrollment();
//            e5.setUser(student5);
//            e5.setCourse(course5);
//            e5.setEnrollmentDate(LocalDateTime.now());
//
//            Enrollment e6 = new Enrollment();
//            e6.setUser(student1);
//            e6.setCourse(course2);
//            e6.setEnrollmentDate(LocalDateTime.now());
//
//            enrollmentRepo.saveAll(Arrays.asList(e1, e2, e3, e4, e5, e6));
//
//            System.out.println("Mock data initialized successfully.");
//        };
//    }
//}
//
