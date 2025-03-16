package com.michael_delivery.backend.dto;

import com.michael_delivery.backend.enums.CancellationStatusType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.OffsetDateTime;
import java.util.List;

import static com.michael_delivery.backend.util.ValidationConstants.URLs.URL_PATTERN;


public class CancellationRiderRequestDTO {

    private Long cancellationRiderRequestId;

    private CancellationStatusType status;

    @NotBlank(message = "Reason is required")
    private String reason;

    private List<@Pattern(regexp = URL_PATTERN, message = "Invalid URL format") String> photoUrls;

    private String remark;

    private OffsetDateTime responseAt;

    private Long responseBy;


    @NotNull
    private Long order;

    private Long cancelledBy;

    public Long getCancellationRiderRequestId() {
        return cancellationRiderRequestId;
    }

    public void setCancellationRiderRequestId(final Long cancellationRiderRequestId) {
        this.cancellationRiderRequestId = cancellationRiderRequestId;
    }

    public CancellationStatusType getStatus() {
        return status;
    }

    public void setStatus(final CancellationStatusType status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(final List<String> photoUrl) {
        this.photoUrls=photoUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public OffsetDateTime getResponseAt() {
        return responseAt;
    }

    public void setResponseAt(final OffsetDateTime responseAt) {
        this.responseAt = responseAt;
    }

    public Long getResponseBy() {
        return responseBy;
    }

    public void setResponseBy(final Long responseBy) {
        this.responseBy = responseBy;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(final Long order) {
        this.order = order;
    }

    public Long getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(final Long cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

}
