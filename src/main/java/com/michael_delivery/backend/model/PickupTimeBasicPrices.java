package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.michael_delivery.backend.enums.PickupTimeType;
import com.michael_delivery.backend.enums.VehicleType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "PickupTimeBasicPrices")
@EntityListeners(AuditingEntityListener.class)
public class PickupTimeBasicPrices extends BaseModel<Long> {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pickupTimeBasicPriceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PickupTimeType pickupTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private Double price;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isLatest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_id")
    private PickupTimeBasicPrices previous;

    @JsonManagedReference
    @OneToMany(mappedBy = "previous")
    private Set<PickupTimeBasicPrices> previousPickupTimeBasicPrices;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getPickupTimeBasicPriceId() {
        return pickupTimeBasicPriceId;
    }

    public void setPickupTimeBasicPriceId(final Long pickupTimeBasicPriceId) {
        this.pickupTimeBasicPriceId = pickupTimeBasicPriceId;
    }

    public PickupTimeType getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(final PickupTimeType pickupTime) {
        this.pickupTime = pickupTime;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(final VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Boolean getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(final Boolean isLatest) {
        this.isLatest = isLatest;
    }

    public PickupTimeBasicPrices getPrevious() {
        return previous;
    }

    public void setPrevious(final PickupTimeBasicPrices previous) {
        this.previous = previous;
    }

    public Set<PickupTimeBasicPrices> getPreviousPickupTimeBasicPrices() {
        return previousPickupTimeBasicPrices;
    }

    public void setPreviousPickupTimeBasicPrices(
            final Set<PickupTimeBasicPrices> previousPickupTimeBasicPrices) {
        this.previousPickupTimeBasicPrices = previousPickupTimeBasicPrices;
    }


    @Override
    public Long getId() {
        return pickupTimeBasicPriceId;
    }
}
