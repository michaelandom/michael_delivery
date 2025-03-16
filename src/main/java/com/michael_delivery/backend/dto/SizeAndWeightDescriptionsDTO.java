package com.michael_delivery.backend.dto;

import com.michael_delivery.backend.enums.SizeAndWeightType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class SizeAndWeightDescriptionsDTO {

    private Long sizeWeightDescriptionId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "size is required")
    private SizeAndWeightType size;

    @NotBlank(message = "sizeDescription is required")
    private String sizeDescription;

    @NotBlank(message = "weight is required")
    private String weight;

    @NotNull
    private Boolean isLatest;

    private Long previous;

    public Long getSizeWeightDescriptionId() {
        return sizeWeightDescriptionId;
    }

    public void setSizeWeightDescriptionId(final Long sizeWeightDescriptionId) {
        this.sizeWeightDescriptionId = sizeWeightDescriptionId;
    }

    public SizeAndWeightType getSize() {
        return size;
    }

    public void setSize(final SizeAndWeightType size) {
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
