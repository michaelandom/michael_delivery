package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class ExtrFeeDTO {

    private Long extrFeeId;

    private String message;

    @NotNull
    private Double amount;

    @NotBlank(message = "cardNumber is required")
    private String cardNumber;

    private String paymentStatus;

    private OffsetDateTime paidAt;

    private OffsetDateTime sentAt;


    @NotNull
    private Long order;

    public Long getExtrFeeId() {
        return extrFeeId;
    }

    public void setExtrFeeId(final Long extrFeeId) {
        this.extrFeeId = extrFeeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(final String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OffsetDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(final OffsetDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public OffsetDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(final OffsetDateTime sentAt) {
        this.sentAt = sentAt;
    }


    public Long getOrder() {
        return order;
    }

    public void setOrder(final Long order) {
        this.order = order;
    }

}
