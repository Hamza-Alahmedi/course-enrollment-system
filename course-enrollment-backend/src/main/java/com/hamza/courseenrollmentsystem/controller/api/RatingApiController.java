package com.hamza.courseenrollmentsystem.controller.api;

import com.hamza.courseenrollmentsystem.dto.AverageRatingDto;
import com.hamza.courseenrollmentsystem.dto.RatingDto;
import com.hamza.courseenrollmentsystem.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(
    origins = "https://course-enrollment-frontend-c9mr.onrender.com",
    allowCredentials = "true",
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
public class RatingApiController {

    private static final Logger logger = LoggerFactory.getLogger(RatingApiController.class);

    @Autowired
    private RatingService ratingService;

    /**
     * GET /api/courses/{id}/rating/avg
     * Get average rating for a course
     */
    @GetMapping("/{id}/rating/avg")
    public ResponseEntity<AverageRatingDto> getAverageRating(@PathVariable Long id) {
        try {
            AverageRatingDto result = ratingService.getAverageRating(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AverageRatingDto(null, "Error: " + e.getMessage()));
        }
    }

    /**
     * GET /api/courses/{id}/rating/me
     * Get current user's rating for a course
     */
    @GetMapping("/{id}/rating/me")
    public ResponseEntity<RatingDto> getUserRating(@PathVariable Long id, Authentication authentication) {
        try {
            if (authentication == null || authentication.getName() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String userEmail = authentication.getName();
            RatingDto result = ratingService.getUserRating(id, userEmail);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RatingDto(null));
        }
    }

    /**
     * POST /api/courses/{id}/rating
     * Save or update user's rating for a course
     */
    @PostMapping("/{id}/rating")
    public ResponseEntity<?> saveRating(
            @PathVariable Long id,
            @RequestBody RatingDto ratingDto,
            Authentication authentication) {
        try {
            logger.info("Rating request received for course ID: {}", id);
            logger.info("Authentication object: {}", authentication);
            logger.info("Authentication name: {}", authentication != null ? authentication.getName() : "null");
            logger.info("Is authenticated: {}", authentication != null ? authentication.isAuthenticated() : "false");

            if (authentication == null || authentication.getName() == null) {
                logger.error("Authentication failed - authentication is null or name is null");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("User not authenticated. Please login again.");
            }

            if (ratingDto.getRating() == null) {
                logger.error("Rating value is null");
                return ResponseEntity.badRequest().body("Rating is required");
            }

            String userEmail = authentication.getName();
            logger.info("Processing rating for user: {}, course: {}, rating: {}", userEmail, id, ratingDto.getRating());

            RatingDto result = ratingService.saveRating(id, userEmail, ratingDto.getRating());
            logger.info("Rating saved successfully");
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            logger.error("Runtime exception: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error saving rating: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving rating: " + e.getMessage());
        }
    }
}

