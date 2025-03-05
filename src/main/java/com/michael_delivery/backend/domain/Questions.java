package com.michael_delivery.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "Questions")
@EntityListeners(AuditingEntityListener.class)
public class Questions extends BaseModel<Long> {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private String questionText;

    @Column(columnDefinition = "longtext")
    private String description;

    @OneToMany(mappedBy = "question")
    private Set<QuestionOptions> questionQuestionOptions;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

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


    public Set<QuestionOptions> getQuestionQuestionOptions() {
        return questionQuestionOptions;
    }

    public void setQuestionQuestionOptions(final Set<QuestionOptions> questionQuestionOptions) {
        this.questionQuestionOptions = questionQuestionOptions;
    }

    @Override
    public Long getId() {
        return questionId;
    }
}
