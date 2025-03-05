package com.michael_delivery.backend.domain;

import com.michael_delivery.backend.enums.VehicleType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "VehicleBasicPrices")
@EntityListeners(AuditingEntityListener.class)
public class VehicleBasicPrices extends BaseModel<Long>{

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleBasicPriceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private Double price;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isLatest;

    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_id")
    private VehicleBasicPrices previous;

    @OneToMany(mappedBy = "previous")
    private Set<VehicleBasicPrices> previousVehicleBasicPrices;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getVehicleBasicPriceId() {
        return vehicleBasicPriceId;
    }

    public void setVehicleBasicPriceId(final Long vehicleBasicPriceId) {
        this.vehicleBasicPriceId = vehicleBasicPriceId;
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


    public VehicleBasicPrices getPrevious() {
        return previous;
    }

    public void setPrevious(final VehicleBasicPrices previous) {
        this.previous = previous;
    }

    public Set<VehicleBasicPrices> getPreviousVehicleBasicPrices() {
        return previousVehicleBasicPrices;
    }

    public void setPreviousVehicleBasicPrices(
            final Set<VehicleBasicPrices> previousVehicleBasicPrices) {
        this.previousVehicleBasicPrices = previousVehicleBasicPrices;
    }

    @Override
    public Long getId() {
        return vehicleBasicPriceId;
    }

}
