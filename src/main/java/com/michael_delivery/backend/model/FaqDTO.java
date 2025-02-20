package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;


public class FaqDTO {

    private Long id;

    @NotBlank(message = "question is required")
    private String question;

    @NotBlank(message = "answer is required")
    private String answer;

    private Boolean isForRider;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(final String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(final String answer) {
        this.answer = answer;
    }

    public Boolean getIsForRider() {
        return isForRider;
    }

    public void setIsForRider(final Boolean isForRider) {
        this.isForRider = isForRider;
    }

}
