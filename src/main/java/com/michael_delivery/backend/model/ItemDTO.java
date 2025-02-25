package com.michael_delivery.backend.model;

import com.michael_delivery.backend.enums.ItemClassificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.List;


public class ItemDTO {

    private Long itemId;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotBlank(message = "itemClassification is required")
    private List<ItemClassificationType> itemClassification;

    @NotBlank(message = "photoUrs is required")
    private List<String> photoUrls;

    @NotNull
    private Long destination;

    @NotNull
    private Long sizeWeightDescription;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(final Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<ItemClassificationType>  getItemClassification() {
        return itemClassification;
    }

    public void setItemClassification(final List<ItemClassificationType> itemClassification) {
        this.itemClassification=itemClassification;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(final List<String> photoUrls) {
        this.photoUrls=photoUrls;
    }


    public Long getDestination() {
        return destination;
    }

    public void setDestination(final Long destination) {
        this.destination = destination;
    }

    public Long getSizeWeightDescription() {
        return sizeWeightDescription;
    }

    public void setSizeWeightDescription(final Long sizeWeightDescription) {
        this.sizeWeightDescription = sizeWeightDescription;
    }

}
