package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import static com.michael_delivery.backend.util.ValidationConstants.TIME.TIME_PATTERN;


public class NoneBusinessHourRatesDTO {

    private Long noneBusinessHourRateId;

    @NotNull(message = "startTime is required")
    @Pattern(regexp = TIME_PATTERN, message = "startTime must be in HH:mm format")
    private String startTime;


    @NotNull(message = "endTime is required")
    @Pattern(regexp = TIME_PATTERN, message = "endTime must be in HH:mm format")
    private String endTime;

    @NotNull(message = "rate is required")
    private Double rate;

    @NotNull
    private Boolean isLatest;

    private Long previous;

    private Long createdBy;

    public Long getNoneBusinessHourRateId() {
        return noneBusinessHourRateId;
    }

    public void setNoneBusinessHourRateId(final Long noneBusinessHourRateId) {
        this.noneBusinessHourRateId = noneBusinessHourRateId;
    }

    public Long getPrevious() {
        return previous;
    }

    public void setPrevious(Long previous) {
        this.previous = previous;
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
