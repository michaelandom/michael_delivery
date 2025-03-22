package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "NoneBusinessHourRates")
@EntityListeners(AuditingEntityListener.class)
public class NoneBusinessHourRates extends BaseModel<Long> {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noneBusinessHourRateId;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @Column(nullable = false)
    private Double rate;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isLatest;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_id")
    private NoneBusinessHourRates previous;

    @JsonManagedReference
    @OneToMany(mappedBy = "previous")
    private Set<NoneBusinessHourRates> previousNoneBusinessHourRates;


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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(final String endTime) {
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

    public NoneBusinessHourRates getPrevious() {
        return previous;
    }

    public void setPrevious(final NoneBusinessHourRates previous) {
        this.previous = previous;
    }

    public Set<NoneBusinessHourRates> getNoneBusinessHourRates() {
        return previousNoneBusinessHourRates;
    }

    public void setNoneBusinessHourRates(
            final Set<NoneBusinessHourRates> previousNoneBusinessHourRates) {
        this.previousNoneBusinessHourRates = previousNoneBusinessHourRates;
    }
    @Override
    public Long getId() {
        return noneBusinessHourRateId;
    }
}
