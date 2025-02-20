package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class SizeAndWeightDescriptionsDTO {

    private Long sizeWeightDescriptionId;

    @NotBlank(message = "size is required")
    private String size;

    @NotBlank(message = "sizeDescription is required")
    private String sizeDescription;

    @NotBlank(message = "weight is required")
    private String weight;

    @NotBlank
    private Boolean isLatest;

    private Long previous;

    public Long getSizeWeightDescriptionId() {
        return sizeWeightDescriptionId;
    }

    public void setSizeWeightDescriptionId(final Long sizeWeightDescriptionId) {
        this.sizeWeightDescriptionId = sizeWeightDescriptionId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(final String size) {
        this.size = size;
    }

    public String getSizeDescription() {
        return sizeDescription;
    }

    public void setSizeDescription(final String sizeDescription) {
        this.sizeDescription = sizeDescription;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(final String weight) {
        this.weight = weight;
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
