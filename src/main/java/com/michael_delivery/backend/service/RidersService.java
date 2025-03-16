package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.*;
import com.michael_delivery.backend.dto.RidersDTO;
import com.michael_delivery.backend.repository.*;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class RidersService extends BaseService<Riders, RidersDTO,Long, RidersRepository> {

    private final RidersRepository ridersRepository;
    private final UsersRepository usersRepository;
    private final VehiclesRepository vehiclesRepository;
    private final PenalitiesRepository penalitiesRepository;
    private final SuspensionsRepository suspensionsRepository;
    private final RiderAnswersRepository riderAnswersRepository;
    private final OrdersRepository ordersRepository;
    private final ReviewsRepository reviewsRepository;
    private final DestinationRepository destinationRepository;
    private final RiderPaymentsRepository riderPaymentsRepository;

    public RidersService(final RidersRepository ridersRepository,
            final UsersRepository usersRepository, final VehiclesRepository vehiclesRepository,
            final PenalitiesRepository penalitiesRepository,
            final SuspensionsRepository suspensionsRepository,
            final RiderAnswersRepository riderAnswersRepository,
            final OrdersRepository ordersRepository, final ReviewsRepository reviewsRepository,
            final DestinationRepository destinationRepository,
            final RiderPaymentsRepository riderPaymentsRepository) {
        super(ridersRepository,"riderId");

        this.ridersRepository = ridersRepository;
        this.usersRepository = usersRepository;
        this.vehiclesRepository = vehiclesRepository;
        this.penalitiesRepository = penalitiesRepository;
        this.suspensionsRepository = suspensionsRepository;
        this.riderAnswersRepository = riderAnswersRepository;
        this.ordersRepository = ordersRepository;
        this.reviewsRepository = reviewsRepository;
        this.destinationRepository = destinationRepository;
        this.riderPaymentsRepository = riderPaymentsRepository;
    }


    @Override
    public Page<RidersDTO> search(Specification<Riders> query, Pageable pageable) {
        return this.ridersRepository.findAll(query, pageable);
    }

    @Override
    protected RidersDTO mapToDTO(final Riders riders, final RidersDTO ridersDTO) {
        ridersDTO.setRiderId(riders.getRiderId());
        ridersDTO.setLatitude(riders.getLatitude());
        ridersDTO.setLongitude(riders.getLongitude());
        ridersDTO.setIsOnline(riders.getIsOnline());
        ridersDTO.setIsDeleted(riders.getIsDeleted());
        ridersDTO.setIsSuspend(riders.getIsSuspend());
        ridersDTO.setPassedQuiz(riders.getPassedQuiz());
        ridersDTO.setProfileCompleted(riders.getProfileCompleted());
        ridersDTO.setStatus(riders.getStatus());
        ridersDTO.setLastLocationTime(riders.getLastLocationTime());
        ridersDTO.setEmergencyContactFirstName(riders.getEmergencyContactFirstName());
        ridersDTO.setEmergencyContactLastName(riders.getEmergencyContactLastName());
        ridersDTO.setEmergencyContactPhoneNumber(riders.getEmergencyContactPhoneNumber());
        ridersDTO.setBankName(riders.getBankName());
        ridersDTO.setBsbNumber(riders.getBsbNumber());
        ridersDTO.setAccountNumber(riders.getAccountNumber());
        ridersDTO.setLastExamTakeTime(riders.getLastExamTakeTime());
        ridersDTO.setIsApproved(riders.getIsApproved());
        ridersDTO.setIsRejected(riders.getIsRejected());
        ridersDTO.setVisaValidFrom(riders.getVisaValidFrom());
        ridersDTO.setVisaValidTo(riders.getVisaValidTo());
        ridersDTO.setAdminNote(riders.getAdminNote());
        ridersDTO.setRejectedAt(riders.getRejectedAt());
        ridersDTO.setApprovedAt(riders.getApprovedAt());
        ridersDTO.setSignature(riders.getSignature());
        ridersDTO.setUser(riders.getUser() == null ? null : riders.getUser().getUserId());
        return ridersDTO;
    }

    @Override
    protected Riders mapToEntity(final RidersDTO ridersDTO, final Riders riders) {
        riders.setLatitude(ridersDTO.getLatitude());
        riders.setLongitude(ridersDTO.getLongitude());
        riders.setIsOnline(ridersDTO.getIsOnline());
        riders.setIsDeleted(ridersDTO.getIsDeleted());
        riders.setIsSuspend(ridersDTO.getIsSuspend());
        riders.setPassedQuiz(ridersDTO.getPassedQuiz());
        riders.setProfileCompleted(ridersDTO.getProfileCompleted());
        riders.setStatus(ridersDTO.getStatus());
        riders.setLastLocationTime(ridersDTO.getLastLocationTime());
        riders.setEmergencyContactFirstName(ridersDTO.getEmergencyContactFirstName());
        riders.setEmergencyContactLastName(ridersDTO.getEmergencyContactLastName());
        riders.setEmergencyContactPhoneNumber(ridersDTO.getEmergencyContactPhoneNumber());
        riders.setBankName(ridersDTO.getBankName());
        riders.setBsbNumber(ridersDTO.getBsbNumber());
        riders.setAccountNumber(ridersDTO.getAccountNumber());
        riders.setLastExamTakeTime(ridersDTO.getLastExamTakeTime());
        riders.setIsApproved(ridersDTO.getIsApproved());
        riders.setIsRejected(ridersDTO.getIsRejected());
        riders.setVisaValidFrom(ridersDTO.getVisaValidFrom());
        riders.setVisaValidTo(ridersDTO.getVisaValidTo());
        riders.setAdminNote(ridersDTO.getAdminNote());
        riders.setRejectedAt(ridersDTO.getRejectedAt());
        riders.setApprovedAt(ridersDTO.getApprovedAt());
        riders.setSignature(ridersDTO.getSignature());
        final Users user = ridersDTO.getUser() == null ? null : usersRepository.findById(ridersDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        riders.setUser(user);
        return riders;
    }

    @Override
    protected RidersDTO createDTO() {
        return new RidersDTO();
    }

    @Override
    protected Riders createEntity() {
        return new Riders();
    }

    public ReferencedWarning getReferencedWarning(final Long riderId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Riders riders = ridersRepository.findById(riderId)
                .orElseThrow(NotFoundException::new);
        final Vehicles riderVehicles = vehiclesRepository.findFirstByRider(riders);
        if (riderVehicles != null) {
            referencedWarning.setKey("riders.vehicles.rider.referenced");
            referencedWarning.addParam(riderVehicles.getVehicleId());
            return referencedWarning;
        }
        final Penalities riderPenalities = penalitiesRepository.findFirstByRider(riders);
        if (riderPenalities != null) {
            referencedWarning.setKey("riders.penalities.rider.referenced");
            referencedWarning.addParam(riderPenalities.getPenalitieId());
            return referencedWarning;
        }
        final Suspensions riderSuspensions = suspensionsRepository.findFirstByRider(riders);
        if (riderSuspensions != null) {
            referencedWarning.setKey("riders.suspensions.rider.referenced");
            referencedWarning.addParam(riderSuspensions.getSuspensionId());
            return referencedWarning;
        }
        final RiderAnswers riderRiderAnswers = riderAnswersRepository.findFirstByRider(riders);
        if (riderRiderAnswers != null) {
            referencedWarning.setKey("riders.riderAnswers.rider.referenced");
            referencedWarning.addParam(riderRiderAnswers.getRiderAnswerId());
            return referencedWarning;
        }
        final Orders riderOrders = ordersRepository.findFirstByRider(riders);
        if (riderOrders != null) {
            referencedWarning.setKey("riders.orders.rider.referenced");
            referencedWarning.addParam(riderOrders.getOrderId());
            return referencedWarning;
        }
        final Reviews riderReviews = reviewsRepository.findFirstByRider(riders);
        if (riderReviews != null) {
            referencedWarning.setKey("riders.reviews.rider.referenced");
            referencedWarning.addParam(riderReviews.getReviewId());
            return referencedWarning;
        }
        final Destination deliveryByDestination = destinationRepository.findFirstByDeliveryBy(riders);
        if (deliveryByDestination != null) {
            referencedWarning.setKey("riders.destination.deliveryBy.referenced");
            referencedWarning.addParam(deliveryByDestination.getDestinationId());
            return referencedWarning;
        }
        final RiderPayments riderRiderPayments = riderPaymentsRepository.findFirstByRider(riders);
        if (riderRiderPayments != null) {
            referencedWarning.setKey("riders.riderPayments.rider.referenced");
            referencedWarning.addParam(riderRiderPayments.getRiderPaymentId());
            return referencedWarning;
        }
        return null;
    }

}
