package com.michael_delivery.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "SizeAndWeightDescriptions")
@EntityListeners(AuditingEntityListener.class)
public class SizeAndWeightDescriptions {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sizeWeightDescriptionId;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false, columnDefinition = "longtext")
    private String sizeDescription;

    @Column(nullable = false)
    private String weight;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isLatest;
    

    @Column(unique = true, length = 50)
    private String uniqueSizeCheck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_id")
    private SizeAndWeightDescriptions previous;

    @OneToMany(mappedBy = "previous")
    private Set<SizeAndWeightDescriptions> previousSizeAndWeightDescriptions;

    @OneToMany(mappedBy = "sizeWeightDescription")
    private Set<Item> sizeWeightDescriptionItems;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getSizeWeightDescriptionId() {
        return sizeWeightDescriptionId;
    }

    public void setSizeWeightDescriptionId(final Long sizeWeightDescriptionId) {
        this.sizeWeightDescriptionId = sizeWeightDescriptionId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(final String size) {
        this.size = size;
    }

    public String getSizeDescription() {
        return sizeDescription;
    }

    public void setSizeDescription(final String sizeDescription) {
        this.sizeDescription = sizeDescription;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(final String weight) {
        this.weight = weight;
    }

    public Boolean getIsLatest() {
        return isLatest;
    }

    public void setIsLatest(final Boolean isLatest) {
        this.isLatest = isLatest;
    }


    public String getUniqueSizeCheck() {
        return uniqueSizeCheck;
    }

    public void setUniqueSizeCheck(final String uniqueSizeCheck) {
        this.uniqueSizeCheck = uniqueSizeCheck;
    }

    public SizeAndWeightDescriptions getPrevious() {
        return previous;
    }

    public void setPrevious(final SizeAndWeightDescriptions previous) {
        this.previous = previous;
    }

    public Set<SizeAndWeightDescriptions> getPreviousSizeAndWeightDescriptions() {
        return previousSizeAndWeightDescriptions;
    }

    public void setPreviousSizeAndWeightDescriptions(
            final Set<SizeAndWeightDescriptions> previousSizeAndWeightDescriptions) {
        this.previousSizeAndWeightDescriptions = previousSizeAndWeightDescriptions;
    }

    public Set<Item> getSizeWeightDescriptionItems() {
        return sizeWeightDescriptionItems;
    }

    public void setSizeWeightDescriptionItems(final Set<Item> sizeWeightDescriptionItems) {
        this.sizeWeightDescriptionItems = sizeWeightDescriptionItems;
    }


}
