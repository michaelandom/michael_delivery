package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class PenalitiesDTO {

    private Long penalitieId;

    @NotBlank(message = "reason is required")
    private String reason;

    private Double deductedAmount;

    private String description;

    private String orderNumber;

    private Boolean isWarning;

    private Boolean isActive;


    @NotNull
    private Long rider;

    private Long admin;

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

   public Long getRider() {
        return rider;
    }

    public void setRider(final Long rider) {
        this.rider = rider;
    }

    public Long getAdmin() {
        return admin;
    }

    public void setAdmin(final Long admin) {
        this.admin = admin;
    }

}
