package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;


public class RiderPaymentsDTO {

    private Long riderPaymentId;

    @NotBlank(message = "distance is required")
    private Double distance;

    @NotBlank(message = "price is required")
    private Double price;

    private Boolean isExported;

    private Boolean isPaid;

    @NotBlank(message = "pspReference is required")
    private OffsetDateTime paymentCycle;

    private OffsetDateTime exportedAt;

    @NotNull
    private Long rider;

    public Long getRiderPaymentId() {
        return riderPaymentId;
    }

    public void setRiderPaymentId(final Long riderPaymentId) {
        this.riderPaymentId = riderPaymentId;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(final Double distance) {
        this.distance = distance;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Boolean getIsExported() {
        return isExported;
    }

    public void setIsExported(final Boolean isExported) {
        this.isExported = isExported;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(final Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public OffsetDateTime getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(final OffsetDateTime paymentCycle) {
        this.paymentCycle = paymentCycle;
    }

    public OffsetDateTime getExportedAt() {
        return exportedAt;
    }

    public void setExportedAt(final OffsetDateTime exportedAt) {
        this.exportedAt = exportedAt;
    }

    public Long getRider() {
        return rider;
    }

    public void setRider(final Long rider) {
        this.rider = rider;
    }

}
