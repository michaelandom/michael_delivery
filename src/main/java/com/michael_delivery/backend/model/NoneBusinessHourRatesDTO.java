package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class NoneBusinessHourRatesDTO {

    private Long noneBusinessHourRateId;

    @NotBlank(message = "startTime is required")
    private String startTime;

    @NotBlank(message = "endTime is required")
    private String endTime;

    @NotBlank(message = "rate is required")
    private Double rate;

    @NotNull
    private Boolean isLatest;

    private Long createdBy;

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


    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

}
