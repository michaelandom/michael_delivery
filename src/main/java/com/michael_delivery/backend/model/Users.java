package com.michael_delivery.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.michael_delivery.backend.enums.AccountStatusType;
import com.michael_delivery.backend.enums.AccountType;
import com.michael_delivery.backend.enums.GenderType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
public class Users extends BaseModel<Long> implements UserDetails  {

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenderType gender;

    @Column
    private String email;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean emailVerified = false;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean phoneVerified = false;

    @Column(nullable = false, columnDefinition = "longtext")
    private String passwordHash = "";

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



    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sso_provider_id")
    private SsoProvider ssoProvider;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bussiness_account_id")
    private BussinessAccount bussinessAccount;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<BillingAddress> userBillingAddresses;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private Set<GroupMembers> userGroupMembers;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<PasswordReset> userPasswordResets;

    @JsonManagedReference
    @OneToMany(mappedBy = "reseatedBy",fetch = FetchType.LAZY)
    private Set<PasswordReset> reseatedByPasswordResets;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<DeleteRequest> userDeleteRequests;

    @JsonManagedReference
    @OneToMany(mappedBy = "createdBy",fetch = FetchType.LAZY)
    private Set<Coupons> createdByCouponses;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<UserCoupon> userUserCoupons;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Riders> userRiderses;

    @JsonManagedReference
    @OneToMany(mappedBy = "admin",fetch = FetchType.LAZY)
    private Set<Penalities> adminPenalitieses;

    @JsonManagedReference
    @OneToMany(mappedBy = "suspenedBy",fetch = FetchType.LAZY)
    private Set<Suspensions> suspenedBySuspensionses;

    @JsonManagedReference
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private Set<Orders> customerOrderses;

    @JsonManagedReference
    @OneToMany(mappedBy = "assignedBy",fetch = FetchType.LAZY)
    private Set<Orders> assignedByOrderses;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Reviews> userReviewses;

    @JsonManagedReference
    @OneToMany(mappedBy = "cancelledBy",fetch = FetchType.LAZY)
    private Set<CancellationRequest> cancelledByCancellationRequests;

    @JsonManagedReference
    @OneToMany(mappedBy = "responseBy",fetch = FetchType.LAZY)
    private Set<CancellationRiderRequest> responseBy;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<UserFavoriteAddress> userUserFavoriteAddresses;

    @JsonManagedReference
    @OneToMany(mappedBy = "createdBy",fetch = FetchType.LAZY)
    private Set<NoneBusinessHourRates> createdByNoneBusinessHourRateses;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        System.out.println(userGroupMembers.toString());
        return List.of();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
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

    public Set<BillingAddress> getUserBillingAddresses() {
        return userBillingAddresses;
    }

    public void setUserBillingAddresses(Set<BillingAddress> userBillingAddresses) {
        this.userBillingAddresses = userBillingAddresses;
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

    public Set<CancellationRiderRequest> getResponseBy() {
        return responseBy;
    }

    public void setResponseBy(
            final Set<CancellationRiderRequest> cancelledByCancellationRiderRequests) {
        this.responseBy = cancelledByCancellationRiderRequests;
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


    @Override
    public Long getId() {
        return userId;
    }
}
