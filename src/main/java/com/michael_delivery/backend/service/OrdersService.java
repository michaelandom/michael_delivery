package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.*;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.OrdersDTO;
import com.michael_delivery.backend.repos.*;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrdersService extends BaseService<Orders, OrdersDTO,Long, OrdersRepository>{

    private final OrdersRepository ordersRepository;
    private final RidersRepository ridersRepository;
    private final UsersRepository usersRepository;
    private final ReviewsRepository reviewsRepository;
    private final CancellationRequestRepository cancellationRequestRepository;
    private final CancellationRiderRequestRepository cancellationRiderRequestRepository;
    private final ExtrFeeRepository extrFeeRepository;
    private final DestinationRepository destinationRepository;
    private final DeliveryDetailRepository deliveryDetailRepository;

    public OrdersService(final OrdersRepository ordersRepository,
                         final RidersRepository ridersRepository, final UsersRepository usersRepository,
                         final ReviewsRepository reviewsRepository,
                         final CancellationRequestRepository cancellationRequestRepository,
                         final CancellationRiderRequestRepository cancellationRiderRequestRepository,
                         final ExtrFeeRepository extrFeeRepository,
                         final DestinationRepository destinationRepository,
                         final DeliveryDetailRepository deliveryDetailRepository) {
        super(ordersRepository,"orderId");
        this.ordersRepository = ordersRepository;
        this.ridersRepository = ridersRepository;
        this.usersRepository = usersRepository;
        this.reviewsRepository = reviewsRepository;
        this.cancellationRequestRepository = cancellationRequestRepository;
        this.cancellationRiderRequestRepository = cancellationRiderRequestRepository;
        this.extrFeeRepository = extrFeeRepository;
        this.destinationRepository = destinationRepository;
        this.deliveryDetailRepository = deliveryDetailRepository;
    }

    @Override
    public Page<OrdersDTO> search(Specification<Orders> query, Pageable pageable) {
        return this.ordersRepository.findAll(query, pageable);
    }

    @Override
    protected OrdersDTO mapToDTO(final Orders orders, final OrdersDTO ordersDTO) {
        ordersDTO.setOrderId(orders.getOrderId());
        ordersDTO.setOrderNumber(orders.getOrderNumber());
        ordersDTO.setCustomerFullName(orders.getCustomerFullName());
        ordersDTO.setMessage(orders.getMessage());
        ordersDTO.setCustomerPhoneNumber(orders.getCustomerPhoneNumber());
        ordersDTO.setCouponId(orders.getCouponId());
        ordersDTO.setTotalPrice(orders.getTotalPrice());
        ordersDTO.setTotalDistance(orders.getTotalDistance());
        ordersDTO.setBoostedBy(orders.getBoostedBy());
        ordersDTO.setEstimatedTotalTime(orders.getEstimatedTotalTime());
        ordersDTO.setAgeLimit(orders.getAgeLimit());
        ordersDTO.setDeliveryDetails(orders.getDeliveryDetails());
        ordersDTO.setVehicleType(orders.getVehicleType());
        ordersDTO.setOrderStatus(orders.getOrderStatus());
        ordersDTO.setRefundAt(orders.getRefundAt());
        ordersDTO.setReviewId(orders.getReviewId());
        ordersDTO.setCardNumber(orders.getCardNumber());
        ordersDTO.setCompletedAt(orders.getCompletedAt());
        ordersDTO.setPaymentStatus(orders.getPaymentStatus());
        ordersDTO.setPaidAt(orders.getPaidAt());
        ordersDTO.setBoostedPaymentAt(orders.getBoostedPaymentAt());
        ordersDTO.setDeliveredAt(orders.getDeliveredAt());
        ordersDTO.setReadyForAssignmentAt(orders.getReadyForAssignmentAt());
        ordersDTO.setAssignedAt(orders.getAssignedAt());
        ordersDTO.setIsForcedAssignment(orders.getIsForcedAssignment());
        ordersDTO.setBoosted(orders.getBoosted());
        ordersDTO.setCreatedByAdmin(orders.getCreatedByAdmin());
        ordersDTO.setCanceledAt(orders.getCanceledAt());
        ordersDTO.setRider(orders.getRider() == null ? null : orders.getRider().getRiderId());
        ordersDTO.setCustomer(orders.getCustomer() == null ? null : orders.getCustomer().getUserId());
        ordersDTO.setAssignedBy(orders.getAssignedBy() == null ? null : orders.getAssignedBy().getUserId());
        return ordersDTO;
    }

    @Override
    protected Orders mapToEntity(final OrdersDTO ordersDTO, final Orders orders) {
        orders.setOrderNumber(ordersDTO.getOrderNumber());
        orders.setCustomerFullName(ordersDTO.getCustomerFullName());
        orders.setMessage(ordersDTO.getMessage());
        orders.setCustomerPhoneNumber(ordersDTO.getCustomerPhoneNumber());
        orders.setCouponId(ordersDTO.getCouponId());
        orders.setTotalPrice(ordersDTO.getTotalPrice());
        orders.setTotalDistance(ordersDTO.getTotalDistance());
        orders.setBoostedBy(ordersDTO.getBoostedBy());
        orders.setEstimatedTotalTime(ordersDTO.getEstimatedTotalTime());
        orders.setAgeLimit(ordersDTO.getAgeLimit());
        orders.setDeliveryDetails(ordersDTO.getDeliveryDetails());
        orders.setVehicleType(ordersDTO.getVehicleType());
        orders.setOrderStatus(ordersDTO.getOrderStatus());
        orders.setRefundAt(ordersDTO.getRefundAt());
        orders.setReviewId(ordersDTO.getReviewId());
        orders.setCardNumber(ordersDTO.getCardNumber());
        orders.setCompletedAt(ordersDTO.getCompletedAt());
        orders.setPaymentStatus(ordersDTO.getPaymentStatus());
        orders.setPaidAt(ordersDTO.getPaidAt());
        orders.setBoostedPaymentAt(ordersDTO.getBoostedPaymentAt());
        orders.setDeliveredAt(ordersDTO.getDeliveredAt());
        orders.setReadyForAssignmentAt(ordersDTO.getReadyForAssignmentAt());
        orders.setAssignedAt(ordersDTO.getAssignedAt());
        orders.setIsForcedAssignment(ordersDTO.getIsForcedAssignment());
        orders.setBoosted(ordersDTO.getBoosted());
        orders.setCreatedByAdmin(ordersDTO.getCreatedByAdmin());
        orders.setCanceledAt(ordersDTO.getCanceledAt());
        final Riders rider = ordersDTO.getRider() == null ? null : ridersRepository.findById(ordersDTO.getRider())
                .orElseThrow(() -> new NotFoundException("rider not found"));
        orders.setRider(rider);
        final Users customer = ordersDTO.getCustomer() == null ? null : usersRepository.findById(ordersDTO.getCustomer())
                .orElseThrow(() -> new NotFoundException("customer not found"));
        orders.setCustomer(customer);
        final Users assignedBy = ordersDTO.getAssignedBy() == null ? null : usersRepository.findById(ordersDTO.getAssignedBy())
                .orElseThrow(() -> new NotFoundException("assignedBy not found"));
        orders.setAssignedBy(assignedBy);
        return orders;
    }

    @Override
    protected OrdersDTO createDTO() {
        return new OrdersDTO();
    }

    @Override
    protected Orders createEntity() {
        return new Orders();
    }

    public ReferencedWarning getReferencedWarning(final Long orderId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
        final Reviews orderReviews = reviewsRepository.findFirstByOrder(orders);
        if (orderReviews != null) {
            referencedWarning.setKey("orders.reviews.order.referenced");
            referencedWarning.addParam(orderReviews.getReviewId());
            return referencedWarning;
        }
        final CancellationRequest orderCancellationRequest = cancellationRequestRepository.findFirstByOrder(orders);
        if (orderCancellationRequest != null) {
            referencedWarning.setKey("orders.cancellationRequest.order.referenced");
            referencedWarning.addParam(orderCancellationRequest.getCancellationRequestId());
            return referencedWarning;
        }
        final CancellationRiderRequest orderCancellationRiderRequest = cancellationRiderRequestRepository.findFirstByOrder(orders);
        if (orderCancellationRiderRequest != null) {
            referencedWarning.setKey("orders.cancellationRiderRequest.order.referenced");
            referencedWarning.addParam(orderCancellationRiderRequest.getCancellationRiderRequestId());
            return referencedWarning;
        }
        final ExtrFee orderExtrFee = extrFeeRepository.findFirstByOrder(orders);
        if (orderExtrFee != null) {
            referencedWarning.setKey("orders.extrFee.order.referenced");
            referencedWarning.addParam(orderExtrFee.getExtrFeeId());
            return referencedWarning;
        }
        final Destination orderDestination = destinationRepository.findFirstByOrder(orders);
        if (orderDestination != null) {
            referencedWarning.setKey("orders.destination.order.referenced");
            referencedWarning.addParam(orderDestination.getDestinationId());
            return referencedWarning;
        }
        final DeliveryDetail orderDeliveryDetail = deliveryDetailRepository.findFirstByOrder(orders);
        if (orderDeliveryDetail != null) {
            referencedWarning.setKey("orders.deliveryDetail.order.referenced");
            referencedWarning.addParam(orderDeliveryDetail.getDeliveryDetailId());
            return referencedWarning;
        }
        return null;
    }

}
