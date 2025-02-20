package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;


public class ReferenceDTO {

    private Long referenceId;

    @NotNull
    private List<String> orderIds;

    @NotNull
    private Double amount;

    @NotBlank(message = "currency is required")
    private String currency;

    private String pspReference;

    private String paymentMethod;

    private Map resultJson;

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(final Long referenceId) {
        this.referenceId = referenceId;
    }

    public List<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(final List<String> orderIds) {
        this.orderIds = orderIds;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getPspReference() {
        return pspReference;
    }

    public void setPspReference(final String pspReference) {
        this.pspReference = pspReference;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(final String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Map getResultJson() {
        return resultJson;
    }

    public void setResultJson(final Map resultJson) {
        this.resultJson = resultJson;
    }
}
