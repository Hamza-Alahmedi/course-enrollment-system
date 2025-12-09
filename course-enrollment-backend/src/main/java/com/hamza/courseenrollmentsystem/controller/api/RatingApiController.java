package com.hamza.courseenrollmentsystem.controller.api;

import com.hamza.courseenrollmentsystem.dto.AverageRatingDto;
import com.hamza.courseenrollmentsystem.dto.RatingDto;
import com.hamza.courseenrollmentsystem.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(
    originPatterns = "*",
    allowCredentials = "true",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
public class RatingApiController {

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
            if (authentication == null || authentication.getName() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("User not authenticated");
            }

            if (ratingDto.getRating() == null) {
                return ResponseEntity.badRequest().body("Rating is required");
            }

            String userEmail = authentication.getName();
            RatingDto result = ratingService.saveRating(id, userEmail, ratingDto.getRating());
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving rating: " + e.getMessage());
        }
    }
}

