package com.michael_delivery.backend.model;

import com.michael_delivery.backend.enums.CouponType;
import com.michael_delivery.backend.enums.DiscountType;
import com.michael_delivery.backend.enums.UserImportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class CouponsDTO {

    private Long couponId;

    @NotBlank(message = "Discount Type is required")
    private DiscountType discountType;

    private Double discountAmount;

    private Double discountPercentage;

    private Double maximumDiscountAmount;

    @NotBlank(message = "minimumPurchasePrice is required")
    private Double minimumPurchasePrice;

    @NotBlank(message = "startDate is required")
    private OffsetDateTime startDate;

    @NotBlank(message = "endDate is required")
    private OffsetDateTime endDate;

    @NotBlank(message = "issuedTo is required")
    @Size(max = 255)
    private CouponType issuedTo;

    @NotBlank(message = "UserImportType is required")
    private UserImportType howUserWasAdded;

    @Size(max = 255)
    private String code;

    @NotBlank(message = "numberOfIssuedCoupons is required")
    private Long numberOfIssuedCoupons;

    @NotBlank(message = "numberOfUsedCoupons is required")
    private Long numberOfUsedCoupons;

    private String excelFile;


    @NotBlank(message = "createdBy is required")
    private Long createdBy;

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


    public CouponType getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(final CouponType issuedTo) {
        this.issuedTo = issuedTo;
    }

    public UserImportType getHowUserWasAdded() {
        return howUserWasAdded;
    }

    public void setHowUserWasAdded(final UserImportType howUserWasAdded) {
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


    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

}
