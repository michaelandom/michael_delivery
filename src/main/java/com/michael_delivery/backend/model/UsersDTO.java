package com.michael_delivery.backend.model;

import com.michael_delivery.backend.enums.AccountStatusType;
import com.michael_delivery.backend.enums.AccountType;
import com.michael_delivery.backend.enums.GenderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.OffsetDateTime;


public class UsersDTO {

    private Long userId;

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank(message = "dateOfBirth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "gender is required")
    private GenderType gender;

    private String email;

    private String phone;

    private OffsetDateTime lastLogin;

    @NotBlank(message = "accountType is required")
    private AccountType accountType;

    private OffsetDateTime requestForDeleteAt;

    private OffsetDateTime deactivatedDate;

    private String profilePicture;

    @NotBlank(message = "accountStatus is required")
    private AccountStatusType accountStatus;

    private Long ssoProvider;

    private Long billingAddress;

    private Long bussinessAccount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(final GenderType gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }


    public OffsetDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(final OffsetDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(final AccountType accountType) {
        this.accountType = accountType;
    }

    public OffsetDateTime getRequestForDeleteAt() {
        return requestForDeleteAt;
    }

    public void setRequestForDeleteAt(final OffsetDateTime requestForDeleteAt) {
        this.requestForDeleteAt = requestForDeleteAt;
    }

    public OffsetDateTime getDeactivatedDate() {
        return deactivatedDate;
    }

    public void setDeactivatedDate(final OffsetDateTime deactivatedDate) {
        this.deactivatedDate = deactivatedDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(final String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public AccountStatusType getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(final AccountStatusType accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Long getSsoProvider() {
        return ssoProvider;
    }

    public void setSsoProvider(final Long ssoProvider) {
        this.ssoProvider = ssoProvider;
    }

    public Long getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(final Long billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Long getBussinessAccount() {
        return bussinessAccount;
    }

    public void setBussinessAccount(final Long bussinessAccount) {
        this.bussinessAccount = bussinessAccount;
    }

}
