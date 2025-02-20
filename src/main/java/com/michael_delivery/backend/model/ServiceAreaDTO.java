package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class ServiceAreaDTO {

    private Long serviceAreaId;

    @NotBlank(message = "name is required")
    private String name;

    private String code;

    private Boolean isActive;


    @NotNull
    private Long stateName;

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

    public Long getStateName() {
        return stateName;
    }

    public void setStateName(final Long stateName) {
        this.stateName = stateName;
    }

}
