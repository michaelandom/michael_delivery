package com.michael_delivery.backend.model;

import com.michael_delivery.backend.enums.CouponType;
import com.michael_delivery.backend.enums.DiscountType;
import com.michael_delivery.backend.enums.UserImportType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.OffsetDateTime;

import static com.michael_delivery.backend.util.ValidationConstants.URLs.URL_PATTERN;


public class CouponsDTO {

    private Long couponId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Discount Type is required")
    private DiscountType discountType;

    private Double discountAmount;

    private Double discountPercentage;

    private Double maximumDiscountAmount;

    @NotNull(message = "minimumPurchasePrice is required")
    private Double minimumPurchasePrice;

    @NotNull(message = "startDate is required")
    private OffsetDateTime startDate;

    @NotNull(message = "endDate is required")
    private OffsetDateTime endDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "issuedTo is required")
    private CouponType issuedTo;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "UserImportType is required")
    private UserImportType howUserWasAdded;

    @Size(max = 255)
    private String code;

    @NotNull(message = "numberOfIssuedCoupons is required")
    private Long numberOfIssuedCoupons;

    @NotNull(message = "numberOfUsedCoupons is required")
    private Long numberOfUsedCoupons;

    @URL(message = "Must be a valid URL")
    @Pattern(regexp = URL_PATTERN, message = "Invalid URL format")
    private String excelFileUrl;


    @NotNull(message = "createdBy is required")
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

    public String getExcelFileUrl() {
        return excelFileUrl;
    }

    public void setExcelFileUrl(final String excelFile) {
        this.excelFileUrl = excelFileUrl;
    }


    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

}
