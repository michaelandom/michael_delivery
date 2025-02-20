package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class UserFavoriteAddressDTO {

    private Long favoriteAddressId;

    @NotBlank(message = "longAddress is required")
    private String longAddress;

    @NotBlank(message = "shortAddress is required")
    private String shortAddress;

    private String customAddress;

    private String nickName;

    @NotBlank(message = "latitude is required")
    private Double latitude;

    @NotBlank(message = "longitude is required")
    private Double longitude;

    @NotBlank(message = "addressType is required")
    private String addressType;

    @NotNull
    private Long user;

    public Long getFavoriteAddressId() {
        return favoriteAddressId;
    }

    public void setFavoriteAddressId(final Long favoriteAddressId) {
        this.favoriteAddressId = favoriteAddressId;
    }

    public String getLongAddress() {
        return longAddress;
    }

    public void setLongAddress(final String longAddress) {
        this.longAddress = longAddress;
    }

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(final String shortAddress) {
        this.shortAddress = shortAddress;
    }

    public String getCustomAddress() {
        return customAddress;
    }

    public void setCustomAddress(final String customAddress) {
        this.customAddress = customAddress;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(final String nickName) {
        this.nickName = nickName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(final String addressType) {
        this.addressType = addressType;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(final Long user) {
        this.user = user;
    }

}
