package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class RiderCommissionDTO {

    private Long riderCommissionId;

    @NotNull(message = "basicCommission is required")
    private Double basicCommission;

    @NotNull(message = "overtimeRate is required")
    private Double overtimeRate;

    @NotNull(message = "holidayRate is required")
    private Double holidayRate;

    @NotNull
    private Boolean isLatest;

    private Long previous;

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

    public Long getPrevious() {
        return previous;
    }

    public void setPrevious(final Long previous) {
        this.previous = previous;
    }

}
