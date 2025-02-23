package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class BillingAddressDTO {

    private Long billingAddressId;

    @Size( max = 255, message = "Billing email must be max of 255 characters")
    private String billingEmail;

    @Size( max = 255, message = "Billing street address 1 must be max of 255 characters")
    private String billingStreetAddress;

    @Size( max = 255, message = "Billing street address 2 must be max of 255 characters")
    private String billingStreetAddress2;

    @Size( max = 255, message = "Billing state must be max of 255 characters")
    private String billingState;

    @Size( max = 10, message = "Billing postcode must be max of 10 characters")
    private String billingPostcode;

    @Size( max = 255, message = "Billing suburb must be max of 255 characters")
    private String billingSuburb;

    @NotNull
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user) {
        this.userId = user;
    }

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

}
