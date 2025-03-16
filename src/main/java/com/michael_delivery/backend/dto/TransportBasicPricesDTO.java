package com.michael_delivery.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michael_delivery.backend.enums.VehicleType;
import jakarta.validation.constraints.NotNull;


public class TransportBasicPricesDTO {

    private Long transportBasicPriceId;

    @NotNull
    private VehicleType vehicleType;

    @NotNull
    private Double basicPrice;

    private Double previousBasicPrice;

    @NotNull
    private Double pricePerMinute;

    @NotNull
    private Double pickuptimeAsapPrice;

    @NotNull
    private Double pickuptime2hoursPrice;

    @NotNull
    private Double pickuptimeTodayPrice;

    @NotNull
    private Double pickuptimeOtherdayPrice;

    @JsonProperty("isLatest")
    private Boolean isLatest;

    private Long previous;

    public Long getTransportBasicPriceId() {
        return transportBasicPriceId;
    }

    public void setTransportBasicPriceId(final Long transportBasicPriceId) {
        this.transportBasicPriceId = transportBasicPriceId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(final VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Double getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(final Double basicPrice) {
        this.basicPrice = basicPrice;
    }

    public Double getPreviousBasicPrice() {
        return previousBasicPrice;
    }

    public void setPreviousBasicPrice(final Double previousBasicPrice) {
        this.previousBasicPrice = previousBasicPrice;
    }

    public Double getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(final Double pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

    public Double getPickuptimeAsapPrice() {
        return pickuptimeAsapPrice;
    }

    public void setPickuptimeAsapPrice(final Double pickuptimeAsapPrice) {
        this.pickuptimeAsapPrice = pickuptimeAsapPrice;
    }

    public Double getPickuptime2hoursPrice() {
        return pickuptime2hoursPrice;
    }

    public void setPickuptime2hoursPrice(final Double pickuptime2hoursPrice) {
        this.pickuptime2hoursPrice = pickuptime2hoursPrice;
    }

    public Double getPickuptimeTodayPrice() {
        return pickuptimeTodayPrice;
    }

    public void setPickuptimeTodayPrice(final Double pickuptimeTodayPrice) {
        this.pickuptimeTodayPrice = pickuptimeTodayPrice;
    }

    public Double getPickuptimeOtherdayPrice() {
        return pickuptimeOtherdayPrice;
    }

    public void setPickuptimeOtherdayPrice(final Double pickuptimeOtherdayPrice) {
        this.pickuptimeOtherdayPrice = pickuptimeOtherdayPrice;
    }

    public Boolean getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(final Boolean isLatest) {
        this.isLatest = isLatest;
    }

    public Long getPrevious() {
        return previous;
    }

    public void setPrevious(final Long previous) {
        this.previous = previous;
    }

}
