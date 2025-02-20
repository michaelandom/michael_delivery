package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.OffsetDateTime;


public class BussinessAccountDTO {

    private Long bussinessAccountId;

    @NotBlank(message = "companyAbn is required")
    @Size( max = 20, message = "Company Abn must be max of 20 characters")
    private String companyAbn;

    @NotBlank(message = "companyName is required")
    @Size( max = 255, message = "Company name must be max of 255 characters")
    private String companyName;

    @URL(message = "Must be a valid URL")
    @Pattern(regexp = "^(https?):\\/\\/[^\\s/$.?#].[^\\s]*$", message = "URL must start with http:// or https://")
    private String logoUrl;

    @NotNull
    private Boolean isActive;


    private Long billingAddress;

    public Long getBussinessAccountId() {
        return bussinessAccountId;
    }

    public void setBussinessAccountId(final Long bussinessAccountId) {
        this.bussinessAccountId = bussinessAccountId;
    }

    public String getCompanyAbn() {
        return companyAbn;
    }

    public void setCompanyAbn(final String companyAbn) {
        this.companyAbn = companyAbn;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(final String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(final Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(final Long billingAddress) {
        this.billingAddress = billingAddress;
    }

}
