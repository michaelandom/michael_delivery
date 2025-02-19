package com.michael_delivery.backend.domain;

import com.michael_delivery.backend.enums.ItemClassificationType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.List;


@Entity
@Table(name = "Items")
@EntityListeners(AuditingEntityListener.class)
public class Item {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false, columnDefinition = "json")
    @Type(JsonType.class)
    private List<ItemClassificationType> itemClassification;

    @Lob
    @Column(nullable = false, columnDefinition = "json")
    @Type(JsonType.class)
    private List<String> photoUrls;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_weight_description_id", nullable = false)
    private SizeAndWeightDescriptions sizeWeightDescription;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(final Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<ItemClassificationType> getItemClassification() {
        return itemClassification;
    }

    public void setItemClassification(final ItemClassificationType itemClassification) {
        this.itemClassification.add(itemClassification);
    }

    public List<String> getPhotos() {
        return photoUrls;
    }

    public void setPhotos(final String photoUrl) {
        this.photoUrls.add(photoUrl);
    }


    public Destination getDestination() {
        return destination;
    }

    public void setDestination(final Destination destination) {
        this.destination = destination;
    }

    public SizeAndWeightDescriptions getSizeWeightDescription() {
        return sizeWeightDescription;
    }

    public void setSizeWeightDescription(final SizeAndWeightDescriptions sizeWeightDescription) {
        this.sizeWeightDescription = sizeWeightDescription;
    }


}
