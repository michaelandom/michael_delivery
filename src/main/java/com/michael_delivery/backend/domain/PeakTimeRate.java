package com.michael_delivery.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "PeakTimeRates")
@EntityListeners(AuditingEntityListener.class)
public class PeakTimeRate extends BaseModel<Long> {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long peakTimeRateId;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isWeekend;

    @Column(nullable = false)
    private OffsetDateTime startTime;

    @Column(nullable = false)
    private OffsetDateTime endTime;

    @Column(nullable = false)
    private Double rate;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isLatest;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isDeleted;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_id")
    private PeakTimeRate previous;

    @OneToMany(mappedBy = "previous")
    private Set<PeakTimeRate> previousPeakTimeRates;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getPeakTimeRateId() {
        return peakTimeRateId;
    }

    public void setPeakTimeRateId(final Long peakTimeRateId) {
        this.peakTimeRateId = peakTimeRateId;
    }

    public Boolean getIsWeekend() {
        return isWeekend;
    }

    public void setIsWeekend(final Boolean isWeekend) {
        this.isWeekend = isWeekend;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(final Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


    public PeakTimeRate getPrevious() {
        return previous;
    }

    public void setPrevious(final PeakTimeRate previous) {
        this.previous = previous;
    }

    public Set<PeakTimeRate> getPreviousPeakTimeRates() {
        return previousPeakTimeRates;
    }

    public void setPreviousPeakTimeRates(final Set<PeakTimeRate> previousPeakTimeRates) {
        this.previousPeakTimeRates = previousPeakTimeRates;
    }

    @Override
    public Long getId() {
        return peakTimeRateId;
    }

}
