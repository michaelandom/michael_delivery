package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.Map;


public class PaymentWebhookPayloadDTO {

    private Long paymentWebhookPayloadId;

    @NotBlank(message = "pspReference is required")
    private String pspReference;

    @NotBlank(message = "pspReference is required")
    private String merchantReference;

    @NotBlank(message = "pspReference is required")
    private String originalReference;

    @NotBlank(message = "pspReference is required")
    private String eventCode;

    private String reason;

    private String paymentMethod;

    @NotBlank(message = "pspReference is required")
    private String amount;

    @NotNull
    private Boolean success;

    private Map payload;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(final Boolean success) {
        this.success = success;
    }

    public Map getPayload() {
        return payload;
    }

    public void setPayload(final Map payload) {
        this.payload = payload;
    }


}
