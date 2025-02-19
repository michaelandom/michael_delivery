package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public class AdvertisementDTO {

    private Long advertisementId;

    @NotNull
    @Size
    private String title;

    @NotNull
    private String content;


    @NotNull
    private String imageUrl;

    public Long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Long advertisementId) {
        this.advertisementId = advertisementId;
    }

    public @NotNull @Size String getTitle() {
        return title;
    }

    public void setTitle(@NotNull @Size String title) {
        this.title = title;
    }

    public @NotNull String getContent() {
        return content;
    }

    public void setContent(@NotNull String content) {
        this.content = content;
    }

    public @NotNull String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NotNull String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
