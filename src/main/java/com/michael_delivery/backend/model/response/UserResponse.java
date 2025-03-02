package com.michael_delivery.backend.model.response;

import com.michael_delivery.backend.enums.AccountStatusType;
import com.michael_delivery.backend.enums.AccountType;
import com.michael_delivery.backend.enums.GenderType;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;

public class UserResponse {
    private Long userId;

    private String username;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private GenderType gender;

    private String email;

    private Boolean emailVerified = false;

    private String phone;

    private Boolean phoneVerified = false;

    private OffsetDateTime lastLogin;

    private AccountType accountType;

    private OffsetDateTime requestForDeleteAt;

    private OffsetDateTime deactivatedDate;

    private String profilePicture;

    private String accessToken;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private AccountStatusType accountStatus;

    private Set<String> permissions;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public OffsetDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(OffsetDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public OffsetDateTime getRequestForDeleteAt() {
        return requestForDeleteAt;
    }

    public void setRequestForDeleteAt(OffsetDateTime requestForDeleteAt) {
        this.requestForDeleteAt = requestForDeleteAt;
    }

    public OffsetDateTime getDeactivatedDate() {
        return deactivatedDate;
    }

    public void setDeactivatedDate(OffsetDateTime deactivatedDate) {
        this.deactivatedDate = deactivatedDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public AccountStatusType getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatusType accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


}
