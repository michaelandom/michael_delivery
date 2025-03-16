package com.michael_delivery.backend.dto;

import com.michael_delivery.backend.enums.QuizKeyType;
import jakarta.validation.constraints.NotNull;


public class RiderAnswersDTO {

    private Long riderAnswerId;

    private QuizKeyType quizKey;

    @NotNull
    private Boolean isCorrect;

    @NotNull
    private Long rider;

    @NotNull
    private Long option;

    public Long getRiderAnswerId() {
        return riderAnswerId;
    }

    public void setRiderAnswerId(final Long riderAnswerId) {
        this.riderAnswerId = riderAnswerId;
    }

    public QuizKeyType getQuizKey() {
        return quizKey;
    }

    public void setQuizKey(final QuizKeyType quizKey) {
        this.quizKey = quizKey;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(final Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Long getRider() {
        return rider;
    }

    public void setRider(final Long rider) {
        this.rider = rider;
    }

    public Long getOption() {
        return option;
    }

    public void setOption(final Long option) {
        this.option = option;
    }

}
