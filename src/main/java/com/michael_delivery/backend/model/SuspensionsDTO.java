package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michael_delivery.backend.enums.SuspensionReasonType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class SuspensionsDTO {

    private Long suspensionId;

    @NotBlank(message = "reason is required")
    private String reason;

    private Boolean isSystemSuspenstion;

    private SuspensionReasonType reasonType;

    @NotNull
    private OffsetDateTime startingFrom;

    private OffsetDateTime endingAt;

    private Boolean isActive;

    @NotNull
    private Long rider;

    private Long suspenedBy;

    public Long getSuspensionId() {
        return suspensionId;
    }

    public void setSuspensionId(final Long suspensionId) {
        this.suspensionId = suspensionId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    public Boolean getIsSystemSuspenstion() {
        return isSystemSuspenstion;
    }

    public void setIsSystemSuspenstion(final Boolean isSystemSuspenstion) {
        this.isSystemSuspenstion = isSystemSuspenstion;
    }

    public SuspensionReasonType getReasonType() {
        return reasonType;
    }

    public void setReasonType(final SuspensionReasonType reasonType) {
        this.reasonType = reasonType;
    }

    public OffsetDateTime getStartingFrom() {
        return startingFrom;
    }

    public void setStartingFrom(final OffsetDateTime startingFrom) {
        this.startingFrom = startingFrom;
    }

    public OffsetDateTime getEndingAt() {
        return endingAt;
    }

    public void setEndingAt(final OffsetDateTime endingAt) {
        this.endingAt = endingAt;
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

    public Long getSuspenedBy() {
        return suspenedBy;
    }

    public void setSuspenedBy(final Long suspenedBy) {
        this.suspenedBy = suspenedBy;
    }

}
