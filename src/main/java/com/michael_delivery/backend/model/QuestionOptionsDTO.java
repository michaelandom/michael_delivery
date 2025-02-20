package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class QuestionOptionsDTO {

    private Long questionOptionId;

    @NotBlank(message = "option is required")
    private String option;

    private String description;

    @NotNull
    private Boolean isCorrect;


    @NotNull
    private Long question;

    public Long getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(final Long questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(final String option) {
        this.option = option;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(final Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Long getQuestion() {
        return question;
    }

    public void setQuestion(final Long question) {
        this.question = question;
    }

}
