package com.michael_delivery.backend.dto;

import com.michael_delivery.backend.enums.RiderStatusType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;


public class RidersDTO {

    private Long riderId;

    private Double latitude;

    private Double longitude;


    private Boolean isOnline;

    private Boolean isDeleted;

    private Boolean isSuspend;

    private Boolean passedQuiz;

    private Boolean profileCompleted;

    @Enumerated(EnumType.STRING)
    private RiderStatusType status;

    private OffsetDateTime lastLocationTime;

    @NotNull
    private String emergencyContactFirstName;

    @NotNull
    private String emergencyContactLastName;

    @NotNull
    private String emergencyContactPhoneNumber;

    @NotNull
    private String bankName;

    @NotNull
    @Size(max = 10)
    private String bsbNumber;

    @NotNull
    @Size(max = 20)
    private String accountNumber;

    private OffsetDateTime lastExamTakeTime;

    private Boolean isApproved;

    private Boolean isRejected;

    private OffsetDateTime visaValidFrom;

    private OffsetDateTime visaValidTo;

    private String adminNote;

    private OffsetDateTime rejectedAt;

    private OffsetDateTime approvedAt;

    @NotNull
    private String signature;

    @NotNull
    private Long user;

    public Long getRiderId() {
        return riderId;
    }

    public void setRiderId(final Long riderId) {
        this.riderId = riderId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public RiderStatusType getStatus() {
        return status;
    }

    public void setStatus(RiderStatusType status) {
        this.status = status;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(final Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(final Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsSuspend() {
        return isSuspend;
    }

    public void setIsSuspend(final Boolean isSuspend) {
        this.isSuspend = isSuspend;
    }

    public Boolean getPassedQuiz() {
        return passedQuiz;
    }

    public void setPassedQuiz(final Boolean passedQuiz) {
        this.passedQuiz = passedQuiz;
    }

    public Boolean getProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(final Boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }


    public OffsetDateTime getLastLocationTime() {
        return lastLocationTime;
    }

    public void setLastLocationTime(final OffsetDateTime lastLocationTime) {
        this.lastLocationTime = lastLocationTime;
    }

    public String getEmergencyContactFirstName() {
        return emergencyContactFirstName;
    }

    public void setEmergencyContactFirstName(final String emergencyContactFirstName) {
        this.emergencyContactFirstName = emergencyContactFirstName;
    }

    public String getEmergencyContactLastName() {
        return emergencyContactLastName;
    }

    public void setEmergencyContactLastName(final String emergencyContactLastName) {
        this.emergencyContactLastName = emergencyContactLastName;
    }

    public String getEmergencyContactPhoneNumber() {
        return emergencyContactPhoneNumber;
    }

    public void setEmergencyContactPhoneNumber(final String emergencyContactPhoneNumber) {
        this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }

    public String getBsbNumber() {
        return bsbNumber;
    }

    public void setBsbNumber(final String bsbNumber) {
        this.bsbNumber = bsbNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(final String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public OffsetDateTime getLastExamTakeTime() {
        return lastExamTakeTime;
    }

    public void setLastExamTakeTime(final OffsetDateTime lastExamTakeTime) {
        this.lastExamTakeTime = lastExamTakeTime;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(final Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(final Boolean isRejected) {
        this.isRejected = isRejected;
    }

    public OffsetDateTime getVisaValidFrom() {
        return visaValidFrom;
    }

    public void setVisaValidFrom(final OffsetDateTime visaValidFrom) {
        this.visaValidFrom = visaValidFrom;
    }

    public OffsetDateTime getVisaValidTo() {
        return visaValidTo;
    }

    public void setVisaValidTo(final OffsetDateTime visaValidTo) {
        this.visaValidTo = visaValidTo;
    }

    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(final String adminNote) {
        this.adminNote = adminNote;
    }

    public OffsetDateTime getRejectedAt() {
        return rejectedAt;
    }

    public void setRejectedAt(final OffsetDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public OffsetDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(final OffsetDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(final String signature) {
        this.signature = signature;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(final Long user) {
        this.user = user;
    }

}
