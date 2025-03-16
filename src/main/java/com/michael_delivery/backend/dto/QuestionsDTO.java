package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;


public class QuestionsDTO {

    private Long questionId;


    @URL
    private String imageUrl;

    @NotBlank(message = "questionText is required")
    private String questionText;

    private String description;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(final Long questionId) {
        this.questionId = questionId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(final String questionText) {
        this.questionText = questionText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

}
