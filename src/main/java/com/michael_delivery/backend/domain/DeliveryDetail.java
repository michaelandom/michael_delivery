package com.michael_delivery.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.michael_delivery.backend.enums.PickupTimeType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "DeliveryDetails")
@EntityListeners(AuditingEntityListener.class)
public class DeliveryDetail {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryDetailId;

    @Column(nullable = false)
    private Double pickupLatitude;

    @Column(nullable = false)
    private Double pickupLongitude;

    @Column
    private String pickupAddressText;

    @Column
    private Integer estimatedTime;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PickupTimeType pickupTime;

    @Column
    private OffsetDateTime pickupDateTime;

    @Column
    private OffsetDateTime pickedUpDateTime;

    @Column
    private OffsetDateTime desiredArrivalDateTime;

    @Column
    private Long pickedUpBy;

    @Column(columnDefinition = "longtext")
    private String pickedUpNotes;

    @Column
    private String recipientPhoneNumber;

    @Column
    private String recipientName;

    @Lob
    @Column(nullable = false,columnDefinition = "JSON")
    @Type(JsonType.class)
    private List<String> pickupPhotoUrls;



    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @JsonManagedReference
    @OneToMany(mappedBy = "deliveryDetail")
    private Set<NoteDeliveryDetail> deliveryDetailNoteDeliveryDetails;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getDeliveryDetailId() {
        return deliveryDetailId;
    }

    public void setDeliveryDetailId(final Long deliveryDetailId) {
        this.deliveryDetailId = deliveryDetailId;
    }

    public Double getPickupLatitude() {
        return pickupLatitude;
    }

    public void setPickupLatitude(final Double pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }

    public Double getPickupLongitude() {
        return pickupLongitude;
    }

    public void setPickupLongitude(final Double pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }

    public String getPickupAddressText() {
        return pickupAddressText;
    }

    public void setPickupAddressText(final String pickupAddressText) {
        this.pickupAddressText = pickupAddressText;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(final Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public PickupTimeType getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(final PickupTimeType pickupTime) {
        this.pickupTime = pickupTime;
    }

    public OffsetDateTime getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(final OffsetDateTime pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    public OffsetDateTime getPickedUpDateTime() {
        return pickedUpDateTime;
    }

    public void setPickedUpDateTime(final OffsetDateTime pickedUpDateTime) {
        this.pickedUpDateTime = pickedUpDateTime;
    }

    public OffsetDateTime getDesiredArrivalDateTime() {
        return desiredArrivalDateTime;
    }

    public void setDesiredArrivalDateTime(final OffsetDateTime desiredArrivalDateTime) {
        this.desiredArrivalDateTime = desiredArrivalDateTime;
    }

    public Long getPickedUpBy() {
        return pickedUpBy;
    }

    public void setPickedUpBy(final Long pickedUpBy) {
        this.pickedUpBy = pickedUpBy;
    }

    public String getPickedUpNotes() {
        return pickedUpNotes;
    }

    public void setPickedUpNotes(final String pickedUpNotes) {
        this.pickedUpNotes = pickedUpNotes;
    }

    public String getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }

    public void setRecipientPhoneNumber(final String recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(final String recipientName) {
        this.recipientName = recipientName;
    }

    public List<String> getPickupPhotoUrls() {
        return pickupPhotoUrls;
    }

    public void setPickupPhotoUrls(final List<String> pickupPhotoUrl) {
        this.pickupPhotoUrls=pickupPhotoUrl;
    }


    public Orders getOrder() {
        return order;
    }

    public void setOrder(final Orders order) {
        this.order = order;
    }

    public Set<NoteDeliveryDetail> getDeliveryDetailNoteDeliveryDetails() {
        return deliveryDetailNoteDeliveryDetails;
    }

    public void setDeliveryDetailNoteDeliveryDetails(
            final Set<NoteDeliveryDetail> deliveryDetailNoteDeliveryDetails) {
        this.deliveryDetailNoteDeliveryDetails = deliveryDetailNoteDeliveryDetails;
    }


}
