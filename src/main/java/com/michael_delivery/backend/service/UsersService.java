package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.*;
import com.michael_delivery.backend.model.UsersDTO;
import com.michael_delivery.backend.repos.*;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final SsoProviderRepository ssoProviderRepository;
    private final BussinessAccountRepository bussinessAccountRepository;
    private final GroupMembersRepository groupMembersRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final DeleteRequestRepository deleteRequestRepository;
    private final CouponsRepository couponsRepository;
    private final UserCouponRepository userCouponRepository;
    private final RidersRepository ridersRepository;
    private final PenalitiesRepository penalitiesRepository;
    private final SuspensionsRepository suspensionsRepository;
    private final OrdersRepository ordersRepository;
    private final ReviewsRepository reviewsRepository;
    private final CancellationRequestRepository cancellationRequestRepository;
    private final CancellationRiderRequestRepository cancellationRiderRequestRepository;
    private final UserFavoriteAddressRepository userFavoriteAddressRepository;
    private final NoneBusinessHourRatesRepository noneBusinessHourRatesRepository;
    private final BillingAddressRepository billingAddressRepository;

    public UsersService(final UsersRepository usersRepository,
            final SsoProviderRepository ssoProviderRepository,
            final BussinessAccountRepository bussinessAccountRepository,
            final GroupMembersRepository groupMembersRepository,
            final PasswordResetRepository passwordResetRepository,
            final DeleteRequestRepository deleteRequestRepository,
            final CouponsRepository couponsRepository,
            final UserCouponRepository userCouponRepository,
            final RidersRepository ridersRepository,
            final PenalitiesRepository penalitiesRepository,
            final SuspensionsRepository suspensionsRepository,
                        final BillingAddressRepository billingAddressRepository,
            final OrdersRepository ordersRepository, final ReviewsRepository reviewsRepository,
            final CancellationRequestRepository cancellationRequestRepository,
            final CancellationRiderRequestRepository cancellationRiderRequestRepository,
            final UserFavoriteAddressRepository userFavoriteAddressRepository,
            final NoneBusinessHourRatesRepository noneBusinessHourRatesRepository) {
        this.usersRepository = usersRepository;
        this.ssoProviderRepository = ssoProviderRepository;
        this.bussinessAccountRepository = bussinessAccountRepository;
        this.billingAddressRepository = billingAddressRepository;
        this.groupMembersRepository = groupMembersRepository;
        this.passwordResetRepository = passwordResetRepository;
        this.deleteRequestRepository = deleteRequestRepository;
        this.couponsRepository = couponsRepository;
        this.userCouponRepository = userCouponRepository;
        this.ridersRepository = ridersRepository;
        this.penalitiesRepository = penalitiesRepository;
        this.suspensionsRepository = suspensionsRepository;
        this.ordersRepository = ordersRepository;
        this.reviewsRepository = reviewsRepository;
        this.cancellationRequestRepository = cancellationRequestRepository;
        this.cancellationRiderRequestRepository = cancellationRiderRequestRepository;
        this.userFavoriteAddressRepository = userFavoriteAddressRepository;
        this.noneBusinessHourRatesRepository = noneBusinessHourRatesRepository;
    }

    public List<UsersDTO> findAll() {
        final List<Users> userses = usersRepository.findAll(Sort.by("userId"));
        return userses.stream()
                .map(users -> mapToDTO(users, new UsersDTO()))
                .toList();
    }

    public UsersDTO get(final Long userId) {
        return usersRepository.findById(userId)
                .map(users -> mapToDTO(users, new UsersDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UsersDTO usersDTO) {
        final Users users = new Users();
        mapToEntity(usersDTO, users);
        return usersRepository.save(users).getUserId();
    }

    public void update(final Long userId, final UsersDTO usersDTO) {
        final Users users = usersRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usersDTO, users);
        usersRepository.save(users);
    }

    public void delete(final Long userId) {
        usersRepository.deleteById(userId);
    }

    private UsersDTO mapToDTO(final Users users, final UsersDTO usersDTO) {
        usersDTO.setUserId(users.getUserId());
        usersDTO.setUsername(users.getUsername());
        usersDTO.setFirstName(users.getFirstName());
        usersDTO.setLastName(users.getLastName());
        usersDTO.setDateOfBirth(users.getDateOfBirth());
        usersDTO.setGender(users.getGender());
        usersDTO.setEmail(users.getEmail());
        usersDTO.setPhone(users.getPhone());
        usersDTO.setLastLogin(users.getLastLogin());
        usersDTO.setAccountType(users.getAccountType());
        usersDTO.setRequestForDeleteAt(users.getRequestForDeleteAt());
        usersDTO.setDeactivatedDate(users.getDeactivatedDate());
        usersDTO.setProfilePicture(users.getProfilePicture());
        usersDTO.setAccountStatus(users.getAccountStatus());
        usersDTO.setSsoProvider(users.getSsoProvider() == null ? null : users.getSsoProvider().getSsoProviderId());
        usersDTO.setBussinessAccount(users.getBussinessAccount() == null ? null : users.getBussinessAccount().getBussinessAccountId());
        return usersDTO;
    }

    private Users mapToEntity(final UsersDTO usersDTO, final Users users) {
        users.setUsername(usersDTO.getUsername());
        users.setFirstName(usersDTO.getFirstName());
        users.setLastName(usersDTO.getLastName());
        users.setDateOfBirth(usersDTO.getDateOfBirth());
        users.setGender(usersDTO.getGender());
        users.setEmail(usersDTO.getEmail());
        users.setPhone(usersDTO.getPhone());
        users.setLastLogin(usersDTO.getLastLogin());
        users.setAccountType(usersDTO.getAccountType());
        users.setRequestForDeleteAt(usersDTO.getRequestForDeleteAt());
        users.setDeactivatedDate(usersDTO.getDeactivatedDate());
        users.setProfilePicture(usersDTO.getProfilePicture());
        users.setAccountStatus(usersDTO.getAccountStatus());
        final SsoProvider ssoProvider = usersDTO.getSsoProvider() == null ? null : ssoProviderRepository.findById(usersDTO.getSsoProvider())
                .orElseThrow(() -> new NotFoundException("ssoProvider not found"));
        users.setSsoProvider(ssoProvider);
        final BussinessAccount bussinessAccount = usersDTO.getBussinessAccount() == null ? null : bussinessAccountRepository.findById(usersDTO.getBussinessAccount())
                .orElseThrow(() -> new NotFoundException("bussinessAccount not found"));
        users.setBussinessAccount(bussinessAccount);
        return users;
    }

    public ReferencedWarning getReferencedWarning(final Long userId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Users users = usersRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        final GroupMembers userGroupMembers = groupMembersRepository.findFirstByUser(users);
        if (userGroupMembers != null) {
            referencedWarning.setKey("users.groupMembers.user.referenced");
            referencedWarning.addParam(userGroupMembers.getGroupMemberId());
            return referencedWarning;
        }
        final PasswordReset userPasswordReset = passwordResetRepository.findFirstByUser(users);
        if (userPasswordReset != null) {
            referencedWarning.setKey("users.passwordReset.user.referenced");
            referencedWarning.addParam(userPasswordReset.getPasswordResetId());
            return referencedWarning;
        }
        final PasswordReset reseatedByPasswordReset = passwordResetRepository.findFirstByReseatedBy(users);
        if (reseatedByPasswordReset != null) {
            referencedWarning.setKey("users.passwordReset.reseatedBy.referenced");
            referencedWarning.addParam(reseatedByPasswordReset.getPasswordResetId());
            return referencedWarning;
        }
        final DeleteRequest userDeleteRequest = deleteRequestRepository.findFirstByUser(users);
        if (userDeleteRequest != null) {
            referencedWarning.setKey("users.deleteRequest.user.referenced");
            referencedWarning.addParam(userDeleteRequest.getDeleteRequestId());
            return referencedWarning;
        }
        final Coupons createdByCoupons = couponsRepository.findFirstByCreatedBy(users);
        if (createdByCoupons != null) {
            referencedWarning.setKey("users.coupons.createdBy.referenced");
            referencedWarning.addParam(createdByCoupons.getCouponId());
            return referencedWarning;
        }
        final BillingAddress userBillingAddress = billingAddressRepository.findFirstByUser(users);
        if (userBillingAddress != null) {
            referencedWarning.setKey("users.billingAddress.user.referenced");
            referencedWarning.addParam(userBillingAddress.getBillingAddressId());
            return referencedWarning;
        }
        final UserCoupon userUserCoupon = userCouponRepository.findFirstByUser(users);
        if (userUserCoupon != null) {
            referencedWarning.setKey("users.userCoupon.user.referenced");
            referencedWarning.addParam(userUserCoupon.getUserCouponId());
            return referencedWarning;
        }
        final Riders userRiders = ridersRepository.findFirstByUser(users);
        if (userRiders != null) {
            referencedWarning.setKey("users.riders.user.referenced");
            referencedWarning.addParam(userRiders.getRiderId());
            return referencedWarning;
        }
        final Penalities adminPenalities = penalitiesRepository.findFirstByAdmin(users);
        if (adminPenalities != null) {
            referencedWarning.setKey("users.penalities.admin.referenced");
            referencedWarning.addParam(adminPenalities.getPenalitieId());
            return referencedWarning;
        }
        final Suspensions suspenedBySuspensions = suspensionsRepository.findFirstBySuspenedBy(users);
        if (suspenedBySuspensions != null) {
            referencedWarning.setKey("users.suspensions.suspenedBy.referenced");
            referencedWarning.addParam(suspenedBySuspensions.getSuspensionId());
            return referencedWarning;
        }
        final Orders customerOrders = ordersRepository.findFirstByCustomer(users);
        if (customerOrders != null) {
            referencedWarning.setKey("users.orders.customer.referenced");
            referencedWarning.addParam(customerOrders.getOrderId());
            return referencedWarning;
        }
        final Orders assignedByOrders = ordersRepository.findFirstByAssignedBy(users);
        if (assignedByOrders != null) {
            referencedWarning.setKey("users.orders.assignedBy.referenced");
            referencedWarning.addParam(assignedByOrders.getOrderId());
            return referencedWarning;
        }
        final Reviews userReviews = reviewsRepository.findFirstByUser(users);
        if (userReviews != null) {
            referencedWarning.setKey("users.reviews.user.referenced");
            referencedWarning.addParam(userReviews.getReviewId());
            return referencedWarning;
        }
        final CancellationRequest cancelledByCancellationRequest = cancellationRequestRepository.findFirstByCancelledBy(users);
        if (cancelledByCancellationRequest != null) {
            referencedWarning.setKey("users.cancellationRequest.cancelledBy.referenced");
            referencedWarning.addParam(cancelledByCancellationRequest.getCancellationRequestId());
            return referencedWarning;
        }
        final CancellationRiderRequest cancelledByCancellationRiderRequest = cancellationRiderRequestRepository.findFirstByCancelledBy(users);
        if (cancelledByCancellationRiderRequest != null) {
            referencedWarning.setKey("users.cancellationRiderRequest.cancelledBy.referenced");
            referencedWarning.addParam(cancelledByCancellationRiderRequest.getCancellationRiderRequestId());
            return referencedWarning;
        }
        final UserFavoriteAddress userUserFavoriteAddress = userFavoriteAddressRepository.findFirstByUser(users);
        if (userUserFavoriteAddress != null) {
            referencedWarning.setKey("users.userFavoriteAddress.user.referenced");
            referencedWarning.addParam(userUserFavoriteAddress.getFavoriteAddressId());
            return referencedWarning;
        }
        final NoneBusinessHourRates createdByNoneBusinessHourRates = noneBusinessHourRatesRepository.findFirstByCreatedBy(users);
        if (createdByNoneBusinessHourRates != null) {
            referencedWarning.setKey("users.noneBusinessHourRates.createdBy.referenced");
            referencedWarning.addParam(createdByNoneBusinessHourRates.getNoneBusinessHourRateId());
            return referencedWarning;
        }
        return null;
    }

}
