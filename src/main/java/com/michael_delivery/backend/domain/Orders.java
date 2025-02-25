package com.michael_delivery.backend.domain;

import com.michael_delivery.backend.enums.OrderStatusType;
import com.michael_delivery.backend.enums.PaymentStatusType;
import com.michael_delivery.backend.enums.VehicleType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "Orders")
@EntityListeners(AuditingEntityListener.class)
public class Orders {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private String orderNumber;

    @Column
    private String customerFullName;

    @Column(columnDefinition = "longtext")
    private String message;

    @Column(length = 20)
    private String customerPhoneNumber;

    @Column
    private Long couponId;

    @Column
    private Double totalPrice;

    @Column
    private Double totalDistance;

    @Column
    private Double boostedBy;

    @Column
    private Integer estimatedTotalTime;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean ageLimit;

    @Column
    private Long deliveryDetails;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatusType orderStatus;

    @Column
    private OffsetDateTime refundAt;

    @Column
    private Long reviewId;

    @Column
    private String cardNumber;

    @Column
    private OffsetDateTime completedAt;

    @Enumerated(EnumType.STRING)
    @Column
    private PaymentStatusType paymentStatus;

    @Column
    private OffsetDateTime paidAt;

    @Column
    private OffsetDateTime boostedPaymentAt;

    @Column
    private OffsetDateTime deliveredAt;

    @Column
    private OffsetDateTime readyForAssignmentAt;

    @Column
    private OffsetDateTime assignedAt;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isForcedAssignment;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean boosted;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean createdByAdmin;

    @Column
    private OffsetDateTime canceledAt;

    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id")
    private Riders rider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Users customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by")
    private Users assignedBy;

    @OneToMany(mappedBy = "order")
    private Set<Reviews> orderReviewses;

    @OneToMany(mappedBy = "order")
    private Set<CancellationRequest> orderCancellationRequests;

    @OneToMany(mappedBy = "order")
    private Set<CancellationRiderRequest> orderCancellationRiderRequests;

    @OneToMany(mappedBy = "order")
    private Set<ExtrFee> orderExtrFees;

    @OneToMany(mappedBy = "order")
    private Set<Destination> orderDestinations;

    @OneToMany(mappedBy = "order")
    private Set<DeliveryDetail> orderDeliveryDetails;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

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


    public Riders getRider() {
        return rider;
    }

    public void setRider(final Riders rider) {
        this.rider = rider;
    }

    public Users getCustomer() {
        return customer;
    }

    public void setCustomer(final Users customer) {
        this.customer = customer;
    }

    public Users getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(final Users assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Set<Reviews> getOrderReviewses() {
        return orderReviewses;
    }

    public void setOrderReviewses(final Set<Reviews> orderReviewses) {
        this.orderReviewses = orderReviewses;
    }

    public Set<CancellationRequest> getOrderCancellationRequests() {
        return orderCancellationRequests;
    }

    public void setOrderCancellationRequests(
            final Set<CancellationRequest> orderCancellationRequests) {
        this.orderCancellationRequests = orderCancellationRequests;
    }

    public Set<CancellationRiderRequest> getOrderCancellationRiderRequests() {
        return orderCancellationRiderRequests;
    }

    public void setOrderCancellationRiderRequests(
            final Set<CancellationRiderRequest> orderCancellationRiderRequests) {
        this.orderCancellationRiderRequests = orderCancellationRiderRequests;
    }

    public Set<ExtrFee> getOrderExtrFees() {
        return orderExtrFees;
    }

    public void setOrderExtrFees(final Set<ExtrFee> orderExtrFees) {
        this.orderExtrFees = orderExtrFees;
    }

    public Set<Destination> getOrderDestinations() {
        return orderDestinations;
    }

    public void setOrderDestinations(final Set<Destination> orderDestinations) {
        this.orderDestinations = orderDestinations;
    }

    public Set<DeliveryDetail> getOrderDeliveryDetails() {
        return orderDeliveryDetails;
    }

    public void setOrderDeliveryDetails(final Set<DeliveryDetail> orderDeliveryDetails) {
        this.orderDeliveryDetails = orderDeliveryDetails;
    }


}
