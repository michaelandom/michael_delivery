package com.michael_delivery.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "PasswordResets")
@EntityListeners(AuditingEntityListener.class)
public class PasswordReset {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passwordResetId;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean isActive;

    @Column(nullable = false, length = 50)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reseated_by_id")
    private Users reseatedBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getPasswordResetId() {
        return passwordResetId;
    }

    public void setPasswordResetId(final Long passwordResetId) {
        this.passwordResetId = passwordResetId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(final Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }


    public Users getUser() {
        return user;
    }

    public void setUser(final Users user) {
        this.user = user;
    }

    public Users getReseatedBy() {
        return reseatedBy;
    }

    public void setReseatedBy(final Users reseatedBy) {
        this.reseatedBy = reseatedBy;
    }


}
