package com.michael_delivery.backend.domain;

import com.michael_delivery.backend.enums.VehicleType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "Vehicles")
@EntityListeners(AuditingEntityListener.class)
public class Vehicles extends BaseModel<Long> {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isCurrentVehicle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Column(length = 4)
    private String modelYear;

    @Column
    private String manufacturer;

    @Column(nullable = false)
    private String transportPhoto;

    @Column
    private String driverLicense;

    @Column
    private String insurancePolicy;

    @Column
    private OffsetDateTime driverLicenseValidFrom;

    @Column
    private OffsetDateTime driverLicenseValidTo;

    @Column
    private OffsetDateTime insurancePolicyValidFrom;

    @Column
    private OffsetDateTime insurancePolicyValidTo;

    @Column
    private OffsetDateTime expiryDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id", nullable = false)
    private Riders rider;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

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



    public Riders getRider() {
        return rider;
    }

    public void setRider(final Riders rider) {
        this.rider = rider;
    }


    @Override
    public Long getId() {
        return vehicleId;
    }
}
