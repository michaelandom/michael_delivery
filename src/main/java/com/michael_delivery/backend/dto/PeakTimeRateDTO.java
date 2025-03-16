package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;


public class PeakTimeRateDTO {

    private Long peakTimeRateId;

   @NotNull
    private Boolean isWeekend;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime endTime;

    @NotNull
    private Double rate;

    @NotNull
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

    public Long getPrevious() {
        return previous;
    }

    public void setPrevious(final Long previous) {
        this.previous = previous;
    }

}
