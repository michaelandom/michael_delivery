package com.michael_delivery.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "RiderPayments")
@EntityListeners(AuditingEntityListener.class)
public class RiderPayments {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long riderPaymentId;

    @Column(nullable = false)
    private Double distance;

    @Column(nullable = false)
    private Double price;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isExported;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isPaid;

    @Column(nullable = false)
    private OffsetDateTime paymentCycle;

    @Column
    private OffsetDateTime exportedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id", nullable = false)
    private Riders rider;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getRiderPaymentId() {
        return riderPaymentId;
    }

    public void setRiderPaymentId(final Long riderPaymentId) {
        this.riderPaymentId = riderPaymentId;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(final Double distance) {
        this.distance = distance;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Boolean getIsExported() {
        return isExported;
    }

    public void setIsExported(final Boolean isExported) {
        this.isExported = isExported;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(final Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public OffsetDateTime getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(final OffsetDateTime paymentCycle) {
        this.paymentCycle = paymentCycle;
    }

    public OffsetDateTime getExportedAt() {
        return exportedAt;
    }

    public void setExportedAt(final OffsetDateTime exportedAt) {
        this.exportedAt = exportedAt;
    }

    public Riders getRider() {
        return rider;
    }

    public void setRider(final Riders rider) {
        this.rider = rider;
    }


}
