package com.michael_delivery.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "DeleteRequests")
@EntityListeners(AuditingEntityListener.class)
public class DeleteRequest {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deleteRequestId;

    @Column(nullable = false)
    private String reason;

    @Column(columnDefinition = "longtext")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getDeleteRequestId() {
        return deleteRequestId;
    }

    public void setDeleteRequestId(final Long deleteRequestId) {
        this.deleteRequestId = deleteRequestId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }


    public Users getUser() {
        return user;
    }

    public void setUser(final Users user) {
        this.user = user;
    }


}
