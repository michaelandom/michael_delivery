package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "NoneBusinessHourRates")
@EntityListeners(AuditingEntityListener.class)
public class NoneBusinessHourRates extends BaseModel<Long> {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noneBusinessHourRateId;

    @Column(nullable = false)
    private OffsetDateTime startTime;

    @Column(nullable = false)
    private OffsetDateTime endTime;

    @Column(nullable = false)
    private Double rate;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isLatest;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private Users createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getNoneBusinessHourRateId() {
        return noneBusinessHourRateId;
    }

    public void setNoneBusinessHourRateId(final Long noneBusinessHourRateId) {
        this.noneBusinessHourRateId = noneBusinessHourRateId;
    }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(final OffsetDateTime startTime) {
        this.startTime = startTime;
    }

    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(final OffsetDateTime endTime) {
        this.endTime = endTime;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(final Double rate) {
        this.rate = rate;
    }

    public Boolean getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(final Boolean isLatest) {
        this.isLatest = isLatest;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Users createdBy) {
        this.createdBy = createdBy;
    }


    @Override
    public Long getId() {
        return noneBusinessHourRateId;
    }
}
