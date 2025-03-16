package com.michael_delivery.backend.dto;

import com.michael_delivery.backend.enums.ItemClassificationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.util.List;

import static com.michael_delivery.backend.util.ValidationConstants.URLs.URL_PATTERN;


public class ItemDTO {

    private Long itemId;

    @NotNull
    @Size(max = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "itemClassification is required")
    private List<ItemClassificationType> itemClassification;

    @NotNull(message = "photoUrs is required")
    private List<@Pattern(regexp = URL_PATTERN,
            message = "Invalid URL format") String> photoUrls;

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
