package com.michael_delivery.backend.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "References")
@EntityListeners(AuditingEntityListener.class)
public class Reference {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long referenceId;

    @Column(nullable = false, columnDefinition = "longtext")
    private String orderIds;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String currency;

    @Column
    private String pspReference;

    @Column
    private String paymentMethod;

    @Lob
    @Column(nullable = false,columnDefinition = "JSON")
    @Type(JsonType.class)
    private Map resultJson;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(final Long referenceId) {
        this.referenceId = referenceId;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(final String orderIds) {
        this.orderIds = orderIds;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getPspReference() {
        return pspReference;
    }

    public void setPspReference(final String pspReference) {
        this.pspReference = pspReference;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(final String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Map getResultJson() {
        return resultJson;
    }




}
