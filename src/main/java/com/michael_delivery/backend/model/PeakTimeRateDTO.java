package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class PeakTimeRateDTO {

    private Long peakTimeRateId;

   @NotNull
    private Boolean isWeekend;

    @NotBlank(message = "startTime is required")
    private String startTime;

    @NotBlank(message = "endTime is required")
    private String endTime;

    @NotBlank(message = "rate is required")
    private Double rate;

    @NotBlank(message = "isLatest is required")
    private Boolean isLatest;

    private Boolean isDeleted;

    private Long previous;

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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(final Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getPrevious() {
        return previous;
    }

    public void setPrevious(final Long previous) {
        this.previous = previous;
    }

}
