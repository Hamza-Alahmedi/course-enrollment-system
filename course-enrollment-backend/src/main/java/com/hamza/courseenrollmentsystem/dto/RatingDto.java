package com.hamza.courseenrollmentsystem.dto;

public class RatingDto {
    private Integer rating; // 1-5

    public RatingDto() {}

    public RatingDto(Integer rating) {
        this.rating = rating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}


