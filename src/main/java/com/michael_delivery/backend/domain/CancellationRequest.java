package com.michael_delivery.backend.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.michael_delivery.backend.enums.CancellationStatusType;
import com.michael_delivery.backend.enums.CancellationType;
import com.michael_delivery.backend.enums.CancelledByType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.Lob;

import java.time.OffsetDateTime;
import java.util.List;


@Entity
@Table(name = "CancellationRequests")
@EntityListeners(AuditingEntityListener.class)
public class CancellationRequest {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cancellationRequestId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CancellationType type;

    @Enumerated(EnumType.STRING)
    @Column
    private CancellationStatusType status = CancellationStatusType.PENDING;

    @Column
    private Double cancellationFee;

    @Column
    private Double refundAmount;

    @Column(nullable = false)
    private String reason;

    @Lob
    @Column(columnDefinition = "JSON")
    @Type(JsonType.class)
    private List<String> photoUrls;


    @Column(nullable = false, columnDefinition = "longtext")
    private String remark;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CancelledByType cancelledByType;

    @Column
    private OffsetDateTime paidAt;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancelled_by")
    private Users cancelledBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

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

    public void setPhotoUrls(final List<String> photoUrl) {
        this.photoUrls=photoUrl;
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


    public Orders getOrder() {
        return order;
    }

    public void setOrder(final Orders order) {
        this.order = order;
    }

    public Users getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(final Users cancelledBy) {
        this.cancelledBy = cancelledBy;
    }


}
