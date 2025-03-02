package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.michael_delivery.backend.enums.AccountStatusType;
import com.michael_delivery.backend.enums.AccountType;
import com.michael_delivery.backend.enums.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static com.michael_delivery.backend.util.ValidationConstants.StrongPasswordValidator.STRONG_PASSWORD_REGEX;
import static com.michael_delivery.backend.util.ValidationConstants.URLs.URL_PATTERN;


public class UsersDTO {

    private Long userId;

    @NotBlank(message = "username is required")
    @Schema(example = "admin1")
    private String username;

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotNull(message = "dateOfBirth is required")
    private LocalDate dateOfBirth;

    @NotNull(message = "gender is required")
    private GenderType gender;

    @Schema(example = "admin1@yopmail.com")
    private String email;

    @Schema(example = "+1000000000000")
    private String phone;

    private OffsetDateTime lastLogin;

    @NotNull(message = "accountType is required")
    private AccountType accountType;

    private OffsetDateTime requestForDeleteAt;

    private OffsetDateTime deactivatedDate;

    @URL(message = "Must be a valid URL")
    @Pattern(regexp = URL_PATTERN, message = "Invalid URL format")
    @Schema(example = "https://profilePicture.png")
    private String profilePicture;

    @Pattern(regexp = STRONG_PASSWORD_REGEX, message = "Password must be at least 8 characters long, " +
            "contain one uppercase letter, one lowercase letter, " +
            "one digit, and one special character.")
    @Schema(example = "Pass123!")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "accountStatus is required")
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
