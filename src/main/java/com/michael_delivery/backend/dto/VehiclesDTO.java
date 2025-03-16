package com.michael_delivery.backend.dto;

import com.michael_delivery.backend.enums.VehicleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;


public class VehiclesDTO {

    private Long vehicleId;

    private Boolean isCurrentVehicle;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "vehicleType is required")
    private VehicleType vehicleType;

    @Size(max = 4)
    private String modelYear;

    private String manufacturer;

    @NotBlank(message = "transportPhoto is required")
    private String transportPhoto;

    private String driverLicense;

    private String insurancePolicy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime driverLicenseValidFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime driverLicenseValidTo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime insurancePolicyValidFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime insurancePolicyValidTo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime expiryDate;

    @NotNull
    private Long rider;

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(final Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Boolean getIsCurrentVehicle() {
        return isCurrentVehicle;
    }

    public void setIsCurrentVehicle(final Boolean isCurrentVehicle) {
        this.isCurrentVehicle = isCurrentVehicle;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(final VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(final String modelYear) {
        this.modelYear = modelYear;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(final String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getTransportPhoto() {
        return transportPhoto;
    }

    public void setTransportPhoto(final String transportPhoto) {
        this.transportPhoto = transportPhoto;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(final String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getInsurancePolicy() {
        return insurancePolicy;
    }

    public void setInsurancePolicy(final String insurancePolicy) {
        this.insurancePolicy = insurancePolicy;
    }

    public OffsetDateTime getDriverLicenseValidFrom() {
        return driverLicenseValidFrom;
    }

    public void setDriverLicenseValidFrom(final OffsetDateTime driverLicenseValidFrom) {
        this.driverLicenseValidFrom = driverLicenseValidFrom;
    }

    public OffsetDateTime getDriverLicenseValidTo() {
        return driverLicenseValidTo;
    }

    public void setDriverLicenseValidTo(final OffsetDateTime driverLicenseValidTo) {
        this.driverLicenseValidTo = driverLicenseValidTo;
    }

    public OffsetDateTime getInsurancePolicyValidFrom() {
        return insurancePolicyValidFrom;
    }

    public void setInsurancePolicyValidFrom(final OffsetDateTime insurancePolicyValidFrom) {
        this.insurancePolicyValidFrom = insurancePolicyValidFrom;
    }

    public OffsetDateTime getInsurancePolicyValidTo() {
        return insurancePolicyValidTo;
    }

    public void setInsurancePolicyValidTo(final OffsetDateTime insurancePolicyValidTo) {
        this.insurancePolicyValidTo = insurancePolicyValidTo;
    }

    public OffsetDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(final OffsetDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }


    public Long getRider() {
        return rider;
    }

    public void setRider(final Long rider) {
        this.rider = rider;
    }

}
