package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class ServiceAreaDTO {

    private Long serviceAreaId;

    @NotBlank(message = "name is required")
    private String name;

    private String code;

    private Boolean isActive;


    @NotNull
    private Long stateId;

    public Long getServiceAreaId() {
        return serviceAreaId;
    }

    public void setServiceAreaId(final Long serviceAreaId) {
        this.serviceAreaId = serviceAreaId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(final Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(final Long stateId) {
        this.stateId = stateId;
    }

}
