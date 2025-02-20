package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michael_delivery.backend.enums.OrderStatusType;
import com.michael_delivery.backend.enums.PaymentStatusType;
import com.michael_delivery.backend.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class OrdersDTO {

    private Long orderId;

    @NotBlank(message = "companyName is required")
    private String orderNumber;

    private String customerFullName;

    private String message;

    private String customerPhoneNumber;

    private Long couponId;

    private Double totalPrice;

    private Double totalDistance;

    private Double boostedBy;

    private Integer estimatedTotalTime;

    private Boolean ageLimit;

    private Long deliveryDetails;

    @NotBlank(message = "vehicleType is required")
    private VehicleType vehicleType;

    @NotBlank(message = "orderStatus is required")
    private OrderStatusType orderStatus;

    private OffsetDateTime refundAt;

    private Long reviewId;

    private String cardNumber;

    private OffsetDateTime completedAt;

    private PaymentStatusType paymentStatus;

    private OffsetDateTime paidAt;

    private OffsetDateTime boostedPaymentAt;

    private OffsetDateTime deliveredAt;

    private OffsetDateTime readyForAssignmentAt;

    private OffsetDateTime assignedAt;

    private Boolean isForcedAssignment;

    private Boolean boosted;

    private Boolean createdByAdmin;

    private OffsetDateTime canceledAt;

    private Long rider;

    private Long customer;

    private Long assignedBy;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(final String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(final String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(final String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(final Long couponId) {
        this.couponId = couponId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(final Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(final Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Double getBoostedBy() {
        return boostedBy;
    }

    public void setBoostedBy(final Double boostedBy) {
        this.boostedBy = boostedBy;
    }

    public Integer getEstimatedTotalTime() {
        return estimatedTotalTime;
    }

    public void setEstimatedTotalTime(final Integer estimatedTotalTime) {
        this.estimatedTotalTime = estimatedTotalTime;
    }

    public Boolean getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(final Boolean ageLimit) {
        this.ageLimit = ageLimit;
    }

    public Long getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(final Long deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(final VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public OrderStatusType getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(final OrderStatusType orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OffsetDateTime getRefundAt() {
        return refundAt;
    }

    public void setRefundAt(final OffsetDateTime refundAt) {
        this.refundAt = refundAt;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(final Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public OffsetDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(final OffsetDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public PaymentStatusType getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(final PaymentStatusType paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OffsetDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(final OffsetDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public OffsetDateTime getBoostedPaymentAt() {
        return boostedPaymentAt;
    }

    public void setBoostedPaymentAt(final OffsetDateTime boostedPaymentAt) {
        this.boostedPaymentAt = boostedPaymentAt;
    }

    public OffsetDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(final OffsetDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public OffsetDateTime getReadyForAssignmentAt() {
        return readyForAssignmentAt;
    }

    public void setReadyForAssignmentAt(final OffsetDateTime readyForAssignmentAt) {
        this.readyForAssignmentAt = readyForAssignmentAt;
    }

    public OffsetDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(final OffsetDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Boolean getIsForcedAssignment() {
        return isForcedAssignment;
    }

    public void setIsForcedAssignment(final Boolean isForcedAssignment) {
        this.isForcedAssignment = isForcedAssignment;
    }

    public Boolean getBoosted() {
        return boosted;
    }

    public void setBoosted(final Boolean boosted) {
        this.boosted = boosted;
    }

    public Boolean getCreatedByAdmin() {
        return createdByAdmin;
    }

    public void setCreatedByAdmin(final Boolean createdByAdmin) {
        this.createdByAdmin = createdByAdmin;
    }

    public OffsetDateTime getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(final OffsetDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    public Long getRider() {
        return rider;
    }

    public void setRider(final Long rider) {
        this.rider = rider;
    }

    public Long getCustomer() {
        return customer;
    }

    public void setCustomer(final Long customer) {
        this.customer = customer;
    }

    public Long getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(final Long assignedBy) {
        this.assignedBy = assignedBy;
    }

}
