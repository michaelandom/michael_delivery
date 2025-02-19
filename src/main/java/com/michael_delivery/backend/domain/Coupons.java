package com.michael_delivery.backend.domain;

import com.michael_delivery.backend.enums.DiscountType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "Coupons")
@EntityListeners(AuditingEntityListener.class)
public class Coupons {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column
    private Double discountAmount;

    @Column
    private Double discountPercentage;

    @Column
    private Double maximumDiscountAmount;

    @Column(nullable = false)
    private Double minimumPurchasePrice;

    @Column(nullable = false)
    private OffsetDateTime startDate;

    @Column(nullable = false)
    private OffsetDateTime endDate;

    @Column(nullable = false)
    private String issuedTo;

    @Column(nullable = false)
    private String howUserWasAdded;

    @Column
    private String code;

    @Column(nullable = false)
    private Long numberOfIssuedCoupons;

    @Column(nullable = false)
    private Long numberOfUsedCoupons;

    @Column(columnDefinition = "longtext")
    private String excelFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    private Users createdBy;

    @OneToMany(mappedBy = "coupon")
    private Set<UserCoupon> couponUserCoupons;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(final Long couponId) {
        this.couponId = couponId;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(final DiscountType discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(final Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(final Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Double getMaximumDiscountAmount() {
        return maximumDiscountAmount;
    }

    public void setMaximumDiscountAmount(final Double maximumDiscountAmount) {
        this.maximumDiscountAmount = maximumDiscountAmount;
    }

    public Double getMinimumPurchasePrice() {
        return minimumPurchasePrice;
    }

    public void setMinimumPurchasePrice(final Double minimumPurchasePrice) {
        this.minimumPurchasePrice = minimumPurchasePrice;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(final OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(final OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(final String issuedTo) {
        this.issuedTo = issuedTo;
    }

    public String getHowUserWasAdded() {
        return howUserWasAdded;
    }

    public void setHowUserWasAdded(final String howUserWasAdded) {
        this.howUserWasAdded = howUserWasAdded;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public Long getNumberOfIssuedCoupons() {
        return numberOfIssuedCoupons;
    }

    public void setNumberOfIssuedCoupons(final Long numberOfIssuedCoupons) {
        this.numberOfIssuedCoupons = numberOfIssuedCoupons;
    }

    public Long getNumberOfUsedCoupons() {
        return numberOfUsedCoupons;
    }

    public void setNumberOfUsedCoupons(final Long numberOfUsedCoupons) {
        this.numberOfUsedCoupons = numberOfUsedCoupons;
    }

    public String getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(final String excelFile) {
        this.excelFile = excelFile;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Users createdBy) {
        this.createdBy = createdBy;
    }

    public Set<UserCoupon> getCouponUserCoupons() {
        return couponUserCoupons;
    }

    public void setCouponUserCoupons(final Set<UserCoupon> couponUserCoupons) {
        this.couponUserCoupons = couponUserCoupons;
    }


}
