package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.michael_delivery.backend.enums.VehicleType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "TransportBasicPrices")
@EntityListeners(AuditingEntityListener.class)
public class TransportBasicPrices extends BaseModel<Long> {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transportBasicPriceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private Double basicPrice;

    @Column
    private Double previousBasicPrice;

    @Column(nullable = false)
    private Double pricePerMinute;

    @Column(nullable = false,name = "pickuptime_asap_price")
    private Double pickuptimeAsapPrice;

    @Column(nullable = false,name = "pickuptime_2hours_price")
    private Double pickuptime2hoursPrice;

    @Column(nullable = false,name = "pickuptime_today_price")
    private Double pickuptimeTodayPrice;

    @Column(nullable = false,name = "pickuptime_otherday_price")
    private Double pickuptimeOtherdayPrice;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isLatest;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_id")
    private TransportBasicPrices previous;

    @JsonManagedReference
    @OneToMany(mappedBy = "previous")
    private Set<TransportBasicPrices> previousTransportBasicPrices;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getTransportBasicPriceId() {
        return transportBasicPriceId;
    }

    public void setTransportBasicPriceId(final Long transportBasicPriceId) {
        this.transportBasicPriceId = transportBasicPriceId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(final VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Double getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(final Double basicPrice) {
        this.basicPrice = basicPrice;
    }

    public Double getPreviousBasicPrice() {
        return previousBasicPrice;
    }

    public void setPreviousBasicPrice(final Double previousBasicPrice) {
        this.previousBasicPrice = previousBasicPrice;
    }

    public Double getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(final Double pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

    public Double getPickuptimeAsapPrice() {
        return pickuptimeAsapPrice;
    }

    public void setPickuptimeAsapPrice(final Double pickuptimeAsapPrice) {
        this.pickuptimeAsapPrice = pickuptimeAsapPrice;
    }

    public Double getPickuptime2hoursPrice() {
        return pickuptime2hoursPrice;
    }

    public void setPickuptime2hoursPrice(final Double pickuptime2hoursPrice) {
        this.pickuptime2hoursPrice = pickuptime2hoursPrice;
    }

    public Double getPickuptimeTodayPrice() {
        return pickuptimeTodayPrice;
    }

    public void setPickuptimeTodayPrice(final Double pickuptimeTodayPrice) {
        this.pickuptimeTodayPrice = pickuptimeTodayPrice;
    }

    public Double getPickuptimeOtherdayPrice() {
        return pickuptimeOtherdayPrice;
    }

    public void setPickuptimeOtherdayPrice(final Double pickuptimeOtherdayPrice) {
        this.pickuptimeOtherdayPrice = pickuptimeOtherdayPrice;
    }

    public Boolean getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(final Boolean isLatest) {
        this.isLatest = isLatest;
    }



    public TransportBasicPrices getPrevious() {
        return previous;
    }

    public void setPrevious(final TransportBasicPrices previous) {
        this.previous = previous;
    }

    public Set<TransportBasicPrices> getPreviousTransportBasicPrices() {
        return previousTransportBasicPrices;
    }

    public void setPreviousTransportBasicPrices(
            final Set<TransportBasicPrices> previousTransportBasicPrices) {
        this.previousTransportBasicPrices = previousTransportBasicPrices;
    }


    @Override
    public Long getId() {
        return transportBasicPriceId;
    }
}
