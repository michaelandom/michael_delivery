package com.michael_delivery.backend.model;

import com.michael_delivery.backend.enums.CancellationStatusType;
import com.michael_delivery.backend.enums.CancellationType;
import com.michael_delivery.backend.enums.CancelledByType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.List;


public class CancellationRequestDTO {

    private Long cancellationRequestId;

    @NotBlank(message = "Type is required")
    private CancellationType type;

    @NotBlank(message = "Status is required")
    private CancellationStatusType status;

    private Double cancellationFee;

    private Double refundAmount;

    @NotBlank(message = "Reason is required")
    private String reason;


    private List<String> photoUrls;

    @NotNull
    private String remark;

    @NotBlank(message = "companyAbn is required")
    private CancelledByType cancelledByType;

    private OffsetDateTime paidAt;


    @NotNull
    private Long order;

    private Long cancelledBy;

    public Long getCancellationRequestId() {
        return cancellationRequestId;
    }

    public void setCancellationRequestId(final Long cancellationRequestId) {
        this.cancellationRequestId = cancellationRequestId;
    }

    public CancellationType getType() {
        return type;
    }

    public void setType(final CancellationType type) {
        this.type = type;
    }

    public CancellationStatusType getStatus() {
        return status;
    }

    public void setStatus(final CancellationStatusType status) {
        this.status = status;
    }

    public Double getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(final Double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(final Double refundAmount) {
        this.refundAmount = refundAmount;
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

    public void setPhotoUrls(final  List<String> photoUrls) {
        this.photoUrls.addAll(photoUrls);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public CancelledByType getCancelledByType() {
        return cancelledByType;
    }

    public void setCancelledByType(final CancelledByType cancelledByType) {
        this.cancelledByType = cancelledByType;
    }

    public OffsetDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(final OffsetDateTime paidAt) {
        this.paidAt = paidAt;
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
