package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michael_delivery.backend.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class VehicleBasicPricesDTO {

    private Long vehicleBasicPriceId;

    @NotBlank(message = "vehicleType is required")
    private VehicleType vehicleType;

    @NotNull
    private Double price;

    @NotNull
    private Boolean isLatest;

    private Long previous;

    public Long getVehicleBasicPriceId() {
        return vehicleBasicPriceId;
    }

    public void setVehicleBasicPriceId(final Long vehicleBasicPriceId) {
        this.vehicleBasicPriceId = vehicleBasicPriceId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(final VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
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
