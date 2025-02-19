package com.michael_delivery.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "QuestionOptions")
@EntityListeners(AuditingEntityListener.class)
public class QuestionOptions {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionOptionId;

    @Column(nullable = false)
    private String option;

    @Column(columnDefinition = "longtext")
    private String description;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isCorrect;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Questions question;

    @OneToMany(mappedBy = "option")
    private Set<RiderAnswers> optionRiderAnswers;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

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


    public Questions getQuestion() {
        return question;
    }

    public void setQuestion(final Questions question) {
        this.question = question;
    }

    public Set<RiderAnswers> getOptionRiderAnswers() {
        return optionRiderAnswers;
    }

    public void setOptionRiderAnswers(final Set<RiderAnswers> optionRiderAnswers) {
        this.optionRiderAnswers = optionRiderAnswers;
    }


}
