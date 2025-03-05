package com.michael_delivery.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.michael_delivery.backend.enums.AddressType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "UserFavoriteAddress")
@EntityListeners(AuditingEntityListener.class)
public class UserFavoriteAddress extends BaseModel<Long> {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteAddressId;

    @Column(nullable = false, columnDefinition = "longtext")
    private String longAddress;

    @Column(nullable = false)
    private String shortAddress;

    @Column
    private String customAddress;

    @Column
    private String nickName;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AddressType addressType;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getFavoriteAddressId() {
        return favoriteAddressId;
    }

    public void setFavoriteAddressId(final Long favoriteAddressId) {
        this.favoriteAddressId = favoriteAddressId;
    }

    public String getLongAddress() {
        return longAddress;
    }

    public void setLongAddress(final String longAddress) {
        this.longAddress = longAddress;
    }

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(final String shortAddress) {
        this.shortAddress = shortAddress;
    }

    public String getCustomAddress() {
        return customAddress;
    }

    public void setCustomAddress(final String customAddress) {
        this.customAddress = customAddress;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(final String nickName) {
        this.nickName = nickName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(final AddressType addressType) {
        this.addressType = addressType;
    }


    public Users getUser() {
        return user;
    }

    public void setUser(final Users user) {
        this.user = user;
    }


    @Override
    public Long getId() {
        return favoriteAddressId;
    }
}
