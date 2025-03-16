package com.michael_delivery.backend.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Map;


@Entity
@Table(name = "PaymentWebhookPayloads")
@EntityListeners(AuditingEntityListener.class)
public class PaymentWebhookPayload extends BaseModel<Long> {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentWebhookPayloadId;

    @Column(nullable = false)
    private String pspReference;

    @Column(nullable = false)
    private String merchantReference;

    @Column
    private String originalReference;

    @Column(nullable = false)
    private String eventCode;

    @Column(columnDefinition = "longtext")
    private String reason;

    @Column
    private String paymentMethod;

    @Column(nullable = false, columnDefinition = "json")
    @Type(JsonType.class)
    private Map<String, Object> amount;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean success;


    @Column(nullable = false, columnDefinition = "json")
    @Type(JsonType.class)
    private Map<String, Object> payload;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getPaymentWebhookPayloadId() {
        return paymentWebhookPayloadId;
    }

    public void setPaymentWebhookPayloadId(final Long paymentWebhookPayloadId) {
        this.paymentWebhookPayloadId = paymentWebhookPayloadId;
    }

    public String getPspReference() {
        return pspReference;
    }

    public void setPspReference(final String pspReference) {
        this.pspReference = pspReference;
    }

    public String getMerchantReference() {
        return merchantReference;
    }

    public void setMerchantReference(final String merchantReference) {
        this.merchantReference = merchantReference;
    }

    public String getOriginalReference() {
        return originalReference;
    }

    public void setOriginalReference(final String originalReference) {
        this.originalReference = originalReference;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(final String eventCode) {
        this.eventCode = eventCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(final String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Map<String, Object> getAmount() {
        return amount;
    }

    public void setAmount(final Map<String, Object> amount) {
        this.amount = amount;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(final Boolean success) {
        this.success = success;
    }

    public Map<String, Object> getPayload() {
        return this.payload;
    }

    public void setPayload(final Map<String, Object> payload) {
        this.payload = payload;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public Long getId() {
        return paymentWebhookPayloadId;
    }
}
