package com.michael_delivery.backend.model;

import com.michael_delivery.backend.enums.PickupTimeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.List;


public class DeliveryDetailDTO {

    private Long deliveryDetailId;

    @NotBlank(message = "pickupLatitude is required")
    private Double pickupLatitude;

    @NotBlank(message = "pickupLongitude is required")
    private Double pickupLongitude;

    @NotBlank(message = "pickupAddressText is required")
    private String pickupAddressText;

    private Integer estimatedTime;

    @NotBlank(message = "pickupTime is required")
    private PickupTimeType pickupTime;

    private OffsetDateTime pickupDateTime;

    private OffsetDateTime pickedUpDateTime;

    private OffsetDateTime desiredArrivalDateTime;

    private String pickedUpBy;

    private String pickedUpNotes;

    private String recipientPhoneNumber;

    private String recipientName;

    private List<String> pickupPhotos;


    @NotNull
    private Long order;

    public Long getDeliveryDetailId() {
        return deliveryDetailId;
    }

    public void setDeliveryDetailId(final Long deliveryDetailId) {
        this.deliveryDetailId = deliveryDetailId;
    }

    public Double getPickupLatitude() {
        return pickupLatitude;
    }

    public void setPickupLatitude(final Double pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }

    public Double getPickupLongitude() {
        return pickupLongitude;
    }

    public void setPickupLongitude(final Double pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }

    public String getPickupAddressText() {
        return pickupAddressText;
    }

    public void setPickupAddressText(final String pickupAddressText) {
        this.pickupAddressText = pickupAddressText;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(final Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public PickupTimeType getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(final PickupTimeType pickupTime) {
        this.pickupTime = pickupTime;
    }

    public OffsetDateTime getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(final OffsetDateTime pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    public OffsetDateTime getPickedUpDateTime() {
        return pickedUpDateTime;
    }

    public void setPickedUpDateTime(final OffsetDateTime pickedUpDateTime) {
        this.pickedUpDateTime = pickedUpDateTime;
    }

    public OffsetDateTime getDesiredArrivalDateTime() {
        return desiredArrivalDateTime;
    }

    public void setDesiredArrivalDateTime(final OffsetDateTime desiredArrivalDateTime) {
        this.desiredArrivalDateTime = desiredArrivalDateTime;
    }

    public String getPickedUpBy() {
        return pickedUpBy;
    }

    public void setPickedUpBy(final String pickedUpBy) {
        this.pickedUpBy = pickedUpBy;
    }

    public String getPickedUpNotes() {
        return pickedUpNotes;
    }

    public void setPickedUpNotes(final String pickedUpNotes) {
        this.pickedUpNotes = pickedUpNotes;
    }

    public String getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }

    public void setRecipientPhoneNumber(final String recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(final String recipientName) {
        this.recipientName = recipientName;
    }

    public List<String> getPickupPhotos() {
        return pickupPhotos;
    }

    public void setPickupPhotos(final List<String> pickupPhoto) {
        this.pickupPhotos.addAll(pickupPhoto);
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(final Long order) {
        this.order = order;
    }

}
