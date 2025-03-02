package com.michael_delivery.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.michael_delivery.backend.enums.DestinationStatusType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "Destinations")
@EntityListeners(AuditingEntityListener.class)
public class Destination {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long destinationId;

    @Column(nullable = false)
    private Double destinationLatitude;

    @Column(nullable = false)
    private Double destinationLongitude;

    @Column
    private String destinationAddressText;

    @Column(nullable = false)
    private Integer sequence;

    @Column
    private String recipientPhoneNumber;

    @Column
    private Double price;

    @Column
    private Integer estimatedTime;

    @Column
    private String safeStorage;

    @Column
    private String specificRecipient;

    @Column
    private String recipientName;

    @Enumerated(EnumType.STRING)
    @Column
    private DestinationStatusType status;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_by")
    private Riders deliveryBy;

    @JsonManagedReference
    @OneToMany(mappedBy = "destination")
    private Set<Evidence> destinationEvidences;

    @JsonManagedReference
    @OneToMany(mappedBy = "destination")
    private Set<Item> destinationItems;

    @JsonManagedReference
    @OneToMany(mappedBy = "destination")
    private Set<NoteDestination> destinationNoteDestinations;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(final Long destinationId) {
        this.destinationId = destinationId;
    }

    public Double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(final Double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public Double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(final Double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public String getDestinationAddressText() {
        return destinationAddressText;
    }

    public void setDestinationAddressText(final String destinationAddressText) {
        this.destinationAddressText = destinationAddressText;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(final Integer sequence) {
        this.sequence = sequence;
    }

    public String getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }

    public void setRecipientPhoneNumber(final String recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(final Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getSafeStorage() {
        return safeStorage;
    }

    public void setSafeStorage(final String safeStorage) {
        this.safeStorage = safeStorage;
    }

    public String getSpecificRecipient() {
        return specificRecipient;
    }

    public void setSpecificRecipient(final String specificRecipient) {
        this.specificRecipient = specificRecipient;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(final String recipientName) {
        this.recipientName = recipientName;
    }

    public DestinationStatusType getStatus() {
        return status;
    }

    public void setStatus(final DestinationStatusType status) {
        this.status = status;
    }


    public Orders getOrder() {
        return order;
    }

    public void setOrder(final Orders order) {
        this.order = order;
    }

    public Riders getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(final Riders deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

    public Set<Evidence> getDestinationEvidences() {
        return destinationEvidences;
    }

    public void setDestinationEvidences(final Set<Evidence> destinationEvidences) {
        this.destinationEvidences = destinationEvidences;
    }

    public Set<Item> getDestinationItems() {
        return destinationItems;
    }

    public void setDestinationItems(final Set<Item> destinationItems) {
        this.destinationItems = destinationItems;
    }

    public Set<NoteDestination> getDestinationNoteDestinations() {
        return destinationNoteDestinations;
    }

    public void setDestinationNoteDestinations(
            final Set<NoteDestination> destinationNoteDestinations) {
        this.destinationNoteDestinations = destinationNoteDestinations;
    }


}
