package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "RiderCommissions")
@EntityListeners(AuditingEntityListener.class)
public class RiderCommission extends BaseModel<Long> {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long riderCommissionId;

    @Column(nullable = false)
    private Double basicCommission;

    @Column(nullable = false)
    private Double overtimeRate;

    @Column(nullable = false)
    private Double holidayRate;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isLatest;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_id")
    private RiderCommission previous;

    @JsonManagedReference
    @OneToMany(mappedBy = "previous")
    private Set<RiderCommission> previousRiderCommissions;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getRiderCommissionId() {
        return riderCommissionId;
    }

    public void setRiderCommissionId(final Long riderCommissionId) {
        this.riderCommissionId = riderCommissionId;
    }

    public Double getBasicCommission() {
        return basicCommission;
    }

    public void setBasicCommission(final Double basicCommission) {
        this.basicCommission = basicCommission;
    }

    public Double getOvertimeRate() {
        return overtimeRate;
    }

    public void setOvertimeRate(final Double overtimeRate) {
        this.overtimeRate = overtimeRate;
    }

    public Double getHolidayRate() {
        return holidayRate;
    }

    public void setHolidayRate(final Double holidayRate) {
        this.holidayRate = holidayRate;
    }

    public Boolean getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(final Boolean isLatest) {
        this.isLatest = isLatest;
    }


    public RiderCommission getPrevious() {
        return previous;
    }

    public void setPrevious(final RiderCommission previous) {
        this.previous = previous;
    }

    public Set<RiderCommission> getPreviousRiderCommissions() {
        return previousRiderCommissions;
    }

    public void setPreviousRiderCommissions(final Set<RiderCommission> previousRiderCommissions) {
        this.previousRiderCommissions = previousRiderCommissions;
    }

    @Override
    public Long getId() {
        return riderCommissionId;
    }
}
