package com.hamza.courseenrollmentsystem.dto;

public class AverageRatingDto {
    private Double averageRating;
    private String message;

    public AverageRatingDto() {}

    public AverageRatingDto(Double averageRating, String message) {
        this.averageRating = averageRating;
        this.message = message;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

