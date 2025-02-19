package com.michael_delivery.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;


@Entity
@Table(name = "ExtrFees")
@EntityListeners(AuditingEntityListener.class)
public class ExtrFee {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long extrFeeId;

    @Column(columnDefinition = "longtext")
    private String message;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String cardNumber;

    @Column
    private String paymentStatus;

    @Column
    private OffsetDateTime paidAt;

    @Column
    private OffsetDateTime sentAt;

    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getExtrFeeId() {
        return extrFeeId;
    }

    public void setExtrFeeId(final Long extrFeeId) {
        this.extrFeeId = extrFeeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(final String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OffsetDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(final OffsetDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public OffsetDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(final OffsetDateTime sentAt) {
        this.sentAt = sentAt;
    }


    public Orders getOrder() {
        return order;
    }

    public void setOrder(final Orders order) {
        this.order = order;
    }


}
