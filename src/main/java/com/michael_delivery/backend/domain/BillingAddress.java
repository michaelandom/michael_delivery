package com.michael_delivery.backend.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "BillingAddress")
@EntityListeners(AuditingEntityListener.class)
public class BillingAddress {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billingAddressId;

    @Column
    private String billingEmail;

    @Column(columnDefinition = "longtext")
    private String billingStreetAddress;

    @Column(columnDefinition = "longtext")
    private String billingStreetAddress2;

    @Column
    private String billingState;

    @Column(length = 10)
    private String billingPostcode;

    @Column
    private String billingSuburb;

    

    @OneToMany(mappedBy = "billingAddress")
    private Set<BussinessAccount> billingAddressBussinessAccounts;

    @OneToMany(mappedBy = "billingAddress")
    private Set<Users> billingAddressUsers;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(final Long billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(final String billingEmail) {
        this.billingEmail = billingEmail;
    }

    public String getBillingStreetAddress() {
        return billingStreetAddress;
    }

    public void setBillingStreetAddress(final String billingStreetAddress) {
        this.billingStreetAddress = billingStreetAddress;
    }

    public String getBillingStreetAddress2() {
        return billingStreetAddress2;
    }

    public void setBillingStreetAddress2(final String billingStreetAddress2) {
        this.billingStreetAddress2 = billingStreetAddress2;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(final String billingState) {
        this.billingState = billingState;
    }

    public String getBillingPostcode() {
        return billingPostcode;
    }

    public void setBillingPostcode(final String billingPostcode) {
        this.billingPostcode = billingPostcode;
    }

    public String getBillingSuburb() {
        return billingSuburb;
    }

    public void setBillingSuburb(final String billingSuburb) {
        this.billingSuburb = billingSuburb;
    }


    public Set<BussinessAccount> getBillingAddressBussinessAccounts() {
        return billingAddressBussinessAccounts;
    }

    public void setBillingAddressBussinessAccounts(
            final Set<BussinessAccount> billingAddressBussinessAccounts) {
        this.billingAddressBussinessAccounts = billingAddressBussinessAccounts;
    }

    public Set<Users> getBillingAddressUsers() {
        return billingAddressUsers;
    }

    public void setBillingAddressUsers(final Set<Users> billingAddressUsers) {
        this.billingAddressUsers = billingAddressUsers;
    }


}
