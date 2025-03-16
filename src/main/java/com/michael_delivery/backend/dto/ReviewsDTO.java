package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotNull;


public class ReviewsDTO {

    private Long reviewId;

    private String review;

    @NotNull(message = "rate is required")
    private Integer rate;

    @NotNull
    private Long rider;

    @NotNull
    private Long user;

    @NotNull
    private Long order;

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(final Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(final String review) {
        this.review = review;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(final Integer rate) {
        this.rate = rate;
    }

    public Long getRider() {
        return rider;
    }

    public void setRider(final Long rider) {
        this.rider = rider;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(final Long user) {
        this.user = user;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(final Long order) {
        this.order = order;
    }

}
