package com.michael_delivery.backend.model;

import com.michael_delivery.backend.enums.DestinationStatusType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class DestinationDTO {

    private Long destinationId;

    @NotNull(message = "destinationLatitude is required")
    private Double destinationLatitude;

    @NotNull(message = "destinationLongitude is required")
    private Double destinationLongitude;

    private String destinationAddressText;

    @NotNull(message = "sequence is required")
    private Integer sequence;

    private String recipientPhoneNumber;

    private Double price;

    private Integer estimatedTime;

    private String safeStorage;

    private String specificRecipient;

    private String recipientName;

    @Enumerated(EnumType.STRING)
    private DestinationStatusType status;


    @NotNull
    private Long order;

    private Long deliveryBy;

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(final Long destinationId) {
        this.destinationId = destinationId;
    }

    public Double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(final Double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public Double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(final Double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public String getDestinationAddressText() {
        return destinationAddressText;
    }

    public void setDestinationAddressText(final String destinationAddressText) {
        this.destinationAddressText = destinationAddressText;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(final Integer sequence) {
        this.sequence = sequence;
    }

    public String getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }

    public void setRecipientPhoneNumber(final String recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(final Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getSafeStorage() {
        return safeStorage;
    }

    public void setSafeStorage(final String safeStorage) {
        this.safeStorage = safeStorage;
    }

    public String getSpecificRecipient() {
        return specificRecipient;
    }

    public void setSpecificRecipient(final String specificRecipient) {
        this.specificRecipient = specificRecipient;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(final String recipientName) {
        this.recipientName = recipientName;
    }

    public DestinationStatusType getStatus() {
        return status;
    }

    public void setStatus(final DestinationStatusType status) {
        this.status = status;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(final Long order) {
        this.order = order;
    }

    public Long getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(final Long deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

}
