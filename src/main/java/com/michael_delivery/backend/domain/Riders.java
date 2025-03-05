package com.michael_delivery.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.michael_delivery.backend.enums.RiderStatusType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "Riders")
@EntityListeners(AuditingEntityListener.class)
public class Riders extends BaseModel<Long> {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long riderId;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isOnline;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isDeleted;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isSuspend;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean passedQuiz;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean profileCompleted;

    @Enumerated(EnumType.STRING)
    @Column
    private RiderStatusType status;

    @Column
    private OffsetDateTime lastLocationTime;

    @Column(nullable = false)
    private String emergencyContactFirstName;

    @Column(nullable = false)
    private String emergencyContactLastName;

    @Column(nullable = false, length = 20)
    private String emergencyContactPhoneNumber;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false, length = 10)
    private String bsbNumber;

    @Column(nullable = false, length = 20)
    private String accountNumber;

    @Column
    private OffsetDateTime lastExamTakeTime;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isApproved;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean isRejected;

    @Column
    private OffsetDateTime visaValidFrom;

    @Column
    private OffsetDateTime visaValidTo;

    @Column(columnDefinition = "longtext")
    private String adminNote;

    @Column
    private OffsetDateTime rejectedAt;

    @Column
    private OffsetDateTime approvedAt;

    @Column(nullable = false, columnDefinition = "longtext")
    private String signature;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @JsonManagedReference
    @OneToMany(mappedBy = "rider")
    private Set<Vehicles> riderVehicleses;

    @JsonManagedReference
    @OneToMany(mappedBy = "rider")
    private Set<Penalities> riderPenalitieses;

    @JsonManagedReference
    @OneToMany(mappedBy = "rider")
    private Set<Suspensions> riderSuspensionses;

    @JsonManagedReference
    @OneToMany(mappedBy = "rider")
    private Set<RiderAnswers> riderRiderAnswers;

    @JsonManagedReference
    @OneToMany(mappedBy = "rider")
    private Set<Orders> riderOrderses;

    @JsonManagedReference
    @OneToMany(mappedBy = "rider")
    private Set<Reviews> riderReviewses;

    @JsonManagedReference
    @OneToMany(mappedBy = "deliveryBy")
    private Set<Destination> deliveryByDestinations;

    @JsonManagedReference
    @OneToMany(mappedBy = "rider")
    private Set<RiderPayments> riderRiderPaymentses;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getRiderId() {
        return riderId;
    }

    public void setRiderId(final Long riderId) {
        this.riderId = riderId;
    }

    public Double getLatitude() {
        return latitude;
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

    public RiderStatusType getStatus() {
        return status;
    }

    public void setStatus(final RiderStatusType status) {
        this.status = status;
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


    public Users getUser() {
        return user;
    }

    public void setUser(final Users user) {
        this.user = user;
    }

    public Set<Vehicles> getRiderVehicleses() {
        return riderVehicleses;
    }

    public void setRiderVehicleses(final Set<Vehicles> riderVehicleses) {
        this.riderVehicleses = riderVehicleses;
    }

    public Set<Penalities> getRiderPenalitieses() {
        return riderPenalitieses;
    }

    public void setRiderPenalitieses(final Set<Penalities> riderPenalitieses) {
        this.riderPenalitieses = riderPenalitieses;
    }

    public Set<Suspensions> getRiderSuspensionses() {
        return riderSuspensionses;
    }

    public void setRiderSuspensionses(final Set<Suspensions> riderSuspensionses) {
        this.riderSuspensionses = riderSuspensionses;
    }

    public Set<RiderAnswers> getRiderRiderAnswers() {
        return riderRiderAnswers;
    }

    public void setRiderRiderAnswers(final Set<RiderAnswers> riderRiderAnswers) {
        this.riderRiderAnswers = riderRiderAnswers;
    }

    public Set<Orders> getRiderOrderses() {
        return riderOrderses;
    }

    public void setRiderOrderses(final Set<Orders> riderOrderses) {
        this.riderOrderses = riderOrderses;
    }

    public Set<Reviews> getRiderReviewses() {
        return riderReviewses;
    }

    public void setRiderReviewses(final Set<Reviews> riderReviewses) {
        this.riderReviewses = riderReviewses;
    }

    public Set<Destination> getDeliveryByDestinations() {
        return deliveryByDestinations;
    }

    public void setDeliveryByDestinations(final Set<Destination> deliveryByDestinations) {
        this.deliveryByDestinations = deliveryByDestinations;
    }

    public Set<RiderPayments> getRiderRiderPaymentses() {
        return riderRiderPaymentses;
    }

    public void setRiderRiderPaymentses(final Set<RiderPayments> riderRiderPaymentses) {
        this.riderRiderPaymentses = riderRiderPaymentses;
    }

    @Override
    public Long getId() {
        return riderId;
    }

}
