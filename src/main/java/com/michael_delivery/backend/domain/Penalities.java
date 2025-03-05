package com.michael_delivery.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "Penalities")
@EntityListeners(AuditingEntityListener.class)
public class Penalities extends BaseModel<Long>{

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long penalitieId;

    @Column(nullable = false)
    private String reason;

    @Column
    private Double deductedAmount;

    @Column(columnDefinition = "longtext")
    private String description;

    @Column
    private String orderNumber;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isWarning;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isActive;



    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id", nullable = false)
    private Riders rider;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Users admin;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getPenalitieId() {
        return penalitieId;
    }

    public void setPenalitieId(final Long penalitieId) {
        this.penalitieId = penalitieId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    public Double getDeductedAmount() {
        return deductedAmount;
    }

    public void setDeductedAmount(final Double deductedAmount) {
        this.deductedAmount = deductedAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(final String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Boolean getIsWarning() {
        return isWarning;
    }

    public void setIsWarning(final Boolean isWarning) {
        this.isWarning = isWarning;
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

    public Users getAdmin() {
        return admin;
    }

    public void setAdmin(final Users admin) {
        this.admin = admin;
    }

    @Override
    public Long getId() {
        return penalitieId;
    }

}
