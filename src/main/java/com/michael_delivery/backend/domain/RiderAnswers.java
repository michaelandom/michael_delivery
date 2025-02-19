package com.michael_delivery.backend.domain;

import com.michael_delivery.backend.enums.QuizKeyType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "RiderAnswers")
@EntityListeners(AuditingEntityListener.class)
public class RiderAnswers {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long riderAnswerId;


    @Enumerated(EnumType.STRING)
    @Column
    private QuizKeyType quizKey=QuizKeyType.INITIAL_QUIZ;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id", nullable = false)
    private Riders rider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private QuestionOptions option;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

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


    public Riders getRider() {
        return rider;
    }

    public void setRider(final Riders rider) {
        this.rider = rider;
    }

    public QuestionOptions getOption() {
        return option;
    }

    public void setOption(final QuestionOptions option) {
        this.option = option;
    }


}
