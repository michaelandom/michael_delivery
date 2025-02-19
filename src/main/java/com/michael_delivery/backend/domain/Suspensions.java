package com.michael_delivery.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "Suspensions")
@EntityListeners(AuditingEntityListener.class)
public class Suspensions {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long suspensionId;

    @Column(nullable = false)
    private String reason;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isSystemSuspenstion;

    @Column
    private String reasonType;

    @Column(nullable = false)
    private OffsetDateTime startingFrom;

    @Column
    private OffsetDateTime endingAt;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isActive;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id", nullable = false)
    private Riders rider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suspened_by_id")
    private Users suspenedBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getSuspensionId() {
        return suspensionId;
    }

    public void setSuspensionId(final Long suspensionId) {
        this.suspensionId = suspensionId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    public Boolean getIsSystemSuspenstion() {
        return isSystemSuspenstion;
    }

    public void setIsSystemSuspenstion(final Boolean isSystemSuspenstion) {
        this.isSystemSuspenstion = isSystemSuspenstion;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(final String reasonType) {
        this.reasonType = reasonType;
    }

    public OffsetDateTime getStartingFrom() {
        return startingFrom;
    }

    public void setStartingFrom(final OffsetDateTime startingFrom) {
        this.startingFrom = startingFrom;
    }

    public OffsetDateTime getEndingAt() {
        return endingAt;
    }

    public void setEndingAt(final OffsetDateTime endingAt) {
        this.endingAt = endingAt;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(final Boolean isActive) {
        this.isActive = isActive;
    }


    public Riders getRider() {
        return rider;
    }

    public void setRider(final Riders rider) {
        this.rider = rider;
    }

    public Users getSuspenedBy() {
        return suspenedBy;
    }

    public void setSuspenedBy(final Users suspenedBy) {
        this.suspenedBy = suspenedBy;
    }


}
