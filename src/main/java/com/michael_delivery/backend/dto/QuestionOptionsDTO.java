package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class QuestionOptionsDTO {

    private Long questionOptionId;

    @NotBlank(message = "questionOption is required")
    private String questionOption;

    private String description;


    @NotNull
    private Long question;

    public Long getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(final Long questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    public String getQuestionOption() {
        return questionOption;
    }

    public void setQuestionOption(final String questionOption) {
        this.questionOption = questionOption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Long getQuestion() {
        return question;
    }

    public void setQuestion(final Long question) {
        this.question = question;
    }

}
