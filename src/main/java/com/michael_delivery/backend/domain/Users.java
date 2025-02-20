package com.michael_delivery.backend.domain;

import com.michael_delivery.backend.enums.AccountStatusType;
import com.michael_delivery.backend.enums.AccountType;
import com.michael_delivery.backend.enums.GenderType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
public class Users {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private GenderType gender;

    @Column
    private String email;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean emailVerified;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean phoneVerified;

    @Column(nullable = false, columnDefinition = "longtext")
    private String passwordHash;

    @Column
    private OffsetDateTime lastLogin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column
    private OffsetDateTime requestForDeleteAt;

    @Column
    private OffsetDateTime deactivatedDate;

    @Column(columnDefinition = "longtext")
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatusType accountStatus;

    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sso_provider_id")
    private SsoProvider ssoProvider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_address_id")
    private BillingAddress billingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bussiness_account_id")
    private BussinessAccount bussinessAccount;

    @OneToMany(mappedBy = "user")
    private Set<GroupMembers> userGroupMembers;

    @OneToMany(mappedBy = "user")
    private Set<PasswordReset> userPasswordResets;

    @OneToMany(mappedBy = "reseatedBy")
    private Set<PasswordReset> reseatedByPasswordResets;

    @OneToMany(mappedBy = "user")
    private Set<DeleteRequest> userDeleteRequests;

    @OneToMany(mappedBy = "createdBy")
    private Set<Coupons> createdByCouponses;

    @OneToMany(mappedBy = "user")
    private Set<UserCoupon> userUserCoupons;

    @OneToMany(mappedBy = "user")
    private Set<Riders> userRiderses;

    @OneToMany(mappedBy = "admin")
    private Set<Penalities> adminPenalitieses;

    @OneToMany(mappedBy = "suspenedBy")
    private Set<Suspensions> suspenedBySuspensionses;

    @OneToMany(mappedBy = "customer")
    private Set<Orders> customerOrderses;

    @OneToMany(mappedBy = "assignedBy")
    private Set<Orders> assignedByOrderses;

    @OneToMany(mappedBy = "user")
    private Set<Reviews> userReviewses;

    @OneToMany(mappedBy = "cancelledBy")
    private Set<CancellationRequest> cancelledByCancellationRequests;

    @OneToMany(mappedBy = "cancelledBy")
    private Set<CancellationRiderRequest> cancelledByCancellationRiderRequests;

    @OneToMany(mappedBy = "user")
    private Set<UserFavoriteAddress> userUserFavoriteAddresses;

    @OneToMany(mappedBy = "createdBy")
    private Set<NoneBusinessHourRates> createdByNoneBusinessHourRateses;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

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

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(final Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(final Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(final String passwordHash) {
        this.passwordHash = passwordHash;
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


    public SsoProvider getSsoProvider() {
        return ssoProvider;
    }

    public void setSsoProvider(final SsoProvider ssoProvider) {
        this.ssoProvider = ssoProvider;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(final BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public BussinessAccount getBussinessAccount() {
        return bussinessAccount;
    }

    public void setBussinessAccount(final BussinessAccount bussinessAccount) {
        this.bussinessAccount = bussinessAccount;
    }

    public Set<GroupMembers> getUserGroupMembers() {
        return userGroupMembers;
    }

    public void setUserGroupMembers(final Set<GroupMembers> userGroupMembers) {
        this.userGroupMembers = userGroupMembers;
    }

    public Set<PasswordReset> getUserPasswordResets() {
        return userPasswordResets;
    }

    public void setUserPasswordResets(final Set<PasswordReset> userPasswordResets) {
        this.userPasswordResets = userPasswordResets;
    }

    public Set<PasswordReset> getReseatedByPasswordResets() {
        return reseatedByPasswordResets;
    }

    public void setReseatedByPasswordResets(final Set<PasswordReset> reseatedByPasswordResets) {
        this.reseatedByPasswordResets = reseatedByPasswordResets;
    }

    public Set<DeleteRequest> getUserDeleteRequests() {
        return userDeleteRequests;
    }

    public void setUserDeleteRequests(final Set<DeleteRequest> userDeleteRequests) {
        this.userDeleteRequests = userDeleteRequests;
    }

    public Set<Coupons> getCreatedByCouponses() {
        return createdByCouponses;
    }

    public void setCreatedByCouponses(final Set<Coupons> createdByCouponses) {
        this.createdByCouponses = createdByCouponses;
    }

    public Set<UserCoupon> getUserUserCoupons() {
        return userUserCoupons;
    }

    public void setUserUserCoupons(final Set<UserCoupon> userUserCoupons) {
        this.userUserCoupons = userUserCoupons;
    }

    public Set<Riders> getUserRiderses() {
        return userRiderses;
    }

    public void setUserRiderses(final Set<Riders> userRiderses) {
        this.userRiderses = userRiderses;
    }

    public Set<Penalities> getAdminPenalitieses() {
        return adminPenalitieses;
    }

    public void setAdminPenalitieses(final Set<Penalities> adminPenalitieses) {
        this.adminPenalitieses = adminPenalitieses;
    }

    public Set<Suspensions> getSuspenedBySuspensionses() {
        return suspenedBySuspensionses;
    }

    public void setSuspenedBySuspensionses(final Set<Suspensions> suspenedBySuspensionses) {
        this.suspenedBySuspensionses = suspenedBySuspensionses;
    }

    public Set<Orders> getCustomerOrderses() {
        return customerOrderses;
    }

    public void setCustomerOrderses(final Set<Orders> customerOrderses) {
        this.customerOrderses = customerOrderses;
    }

    public Set<Orders> getAssignedByOrderses() {
        return assignedByOrderses;
    }

    public void setAssignedByOrderses(final Set<Orders> assignedByOrderses) {
        this.assignedByOrderses = assignedByOrderses;
    }

    public Set<Reviews> getUserReviewses() {
        return userReviewses;
    }

    public void setUserReviewses(final Set<Reviews> userReviewses) {
        this.userReviewses = userReviewses;
    }

    public Set<CancellationRequest> getCancelledByCancellationRequests() {
        return cancelledByCancellationRequests;
    }

    public void setCancelledByCancellationRequests(
            final Set<CancellationRequest> cancelledByCancellationRequests) {
        this.cancelledByCancellationRequests = cancelledByCancellationRequests;
    }

    public Set<CancellationRiderRequest> getCancelledByCancellationRiderRequests() {
        return cancelledByCancellationRiderRequests;
    }

    public void setCancelledByCancellationRiderRequests(
            final Set<CancellationRiderRequest> cancelledByCancellationRiderRequests) {
        this.cancelledByCancellationRiderRequests = cancelledByCancellationRiderRequests;
    }

    public Set<UserFavoriteAddress> getUserUserFavoriteAddresses() {
        return userUserFavoriteAddresses;
    }

    public void setUserUserFavoriteAddresses(
            final Set<UserFavoriteAddress> userUserFavoriteAddresses) {
        this.userUserFavoriteAddresses = userUserFavoriteAddresses;
    }

    public Set<NoneBusinessHourRates> getCreatedByNoneBusinessHourRateses() {
        return createdByNoneBusinessHourRateses;
    }

    public void setCreatedByNoneBusinessHourRateses(
            final Set<NoneBusinessHourRates> createdByNoneBusinessHourRateses) {
        this.createdByNoneBusinessHourRateses = createdByNoneBusinessHourRateses;
    }


}
