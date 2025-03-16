package com.michael_delivery.backend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "Faq")
@EntityListeners(AuditingEntityListener.class)
public class Faq extends BaseModel<Long>{

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "longtext")
    private String question;

    @Column(nullable = false, columnDefinition = "longtext")
    private String answer;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isForRider;

    

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @Override
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
