package com.michael_delivery.backend.domain;

import com.michael_delivery.backend.enums.CancellationStatusType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.List;


@Entity
@Table(name = "CancellationRiderRequests")
@EntityListeners(AuditingEntityListener.class)
public class CancellationRiderRequest {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cancellationRiderRequestId;

    @Enumerated(EnumType.STRING)
    @Column
    private CancellationStatusType status = CancellationStatusType.PENDING;

    @Column(nullable = false)
    private String reason;

    @Lob
    @Column(columnDefinition = "JSON")
    @Type(JsonType.class)
    private List<String> photoUrls;

    @Column(columnDefinition = "longtext")
    private String remark;

    @Column
    private OffsetDateTime responseAt;

    @Column
    private String responseBy;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancelled_by")
    private Users cancelledBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

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

    public String getResponseBy() {
        return responseBy;
    }

    public void setResponseBy(final String responseBy) {
        this.responseBy = responseBy;
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
