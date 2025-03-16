package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.*;
import com.michael_delivery.backend.dto.CancellationRiderRequestDTO;
import com.michael_delivery.backend.repository.*;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class CancellationRiderRequestService extends BaseService<CancellationRiderRequest, CancellationRiderRequestDTO,Long, CancellationRiderRequestRepository> {

    private final CancellationRiderRequestRepository cancellationRiderRequestRepository;
    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final RidersRepository ridersRepository;

    public CancellationRiderRequestService(
            final CancellationRiderRequestRepository cancellationRiderRequestRepository,
            final RidersRepository ridersRepository,
            final OrdersRepository ordersRepository, final UsersRepository usersRepository) {
        super(cancellationRiderRequestRepository,"cancellationRiderRequestId");
        this.cancellationRiderRequestRepository = cancellationRiderRequestRepository;
        this.ordersRepository = ordersRepository;
        this.ridersRepository = ridersRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Page<CancellationRiderRequestDTO> search(Specification<CancellationRiderRequest> query, Pageable pageable) {
        return this.cancellationRiderRequestRepository.findAll(query, pageable);
    }
    @Override
    protected CancellationRiderRequestDTO mapToDTO(
            final CancellationRiderRequest cancellationRiderRequest,
            final CancellationRiderRequestDTO cancellationRiderRequestDTO) {
        cancellationRiderRequestDTO.setCancellationRiderRequestId(cancellationRiderRequest.getCancellationRiderRequestId());
        cancellationRiderRequestDTO.setStatus(cancellationRiderRequest.getStatus());
        cancellationRiderRequestDTO.setReason(cancellationRiderRequest.getReason());
        cancellationRiderRequestDTO.setPhotoUrls(cancellationRiderRequest.getPhotoUrls());
        cancellationRiderRequestDTO.setRemark(cancellationRiderRequest.getRemark());
        cancellationRiderRequestDTO.setResponseAt(cancellationRiderRequest.getResponseAt());
        cancellationRiderRequestDTO.setOrder(cancellationRiderRequest.getOrder() == null ? null : cancellationRiderRequest.getOrder().getOrderId());
        cancellationRiderRequestDTO.setCancelledBy(cancellationRiderRequest.getCancelledBy() == null ? null : cancellationRiderRequest.getCancelledBy().getRiderId());
        cancellationRiderRequestDTO.setResponseBy(cancellationRiderRequest.getResponseBy() == null ? null : cancellationRiderRequest.getResponseBy().getUserId());
        return cancellationRiderRequestDTO;
    }
    @Override
    protected CancellationRiderRequest mapToEntity(
            final CancellationRiderRequestDTO cancellationRiderRequestDTO,
            final CancellationRiderRequest cancellationRiderRequest) {
        cancellationRiderRequest.setStatus(cancellationRiderRequestDTO.getStatus());
        cancellationRiderRequest.setReason(cancellationRiderRequestDTO.getReason());
        cancellationRiderRequest.setPhotoUrls(cancellationRiderRequestDTO.getPhotoUrls());
        cancellationRiderRequest.setRemark(cancellationRiderRequestDTO.getRemark());
        cancellationRiderRequest.setResponseAt(cancellationRiderRequestDTO.getResponseAt());
        final Users responseBy = cancellationRiderRequestDTO.getCancelledBy() == null ? null : usersRepository.findById(cancellationRiderRequestDTO.getResponseBy())
                .orElseThrow(() -> new NotFoundException("responseBy not found"));
        cancellationRiderRequest.setResponseBy(responseBy);
        final Orders order = cancellationRiderRequestDTO.getOrder() == null ? null : ordersRepository.findById(cancellationRiderRequestDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        cancellationRiderRequest.setOrder(order);
        final Riders cancelledBy = cancellationRiderRequestDTO.getCancelledBy() == null ? null : ridersRepository.findById(cancellationRiderRequestDTO.getCancelledBy())
                .orElseThrow(() -> new NotFoundException("cancelledBy not found"));
        cancellationRiderRequest.setCancelledBy(cancelledBy);
        return cancellationRiderRequest;
    }

    @Override
    protected CancellationRiderRequestDTO createDTO() {
        return new CancellationRiderRequestDTO();
    }

    @Override
    protected CancellationRiderRequest createEntity() {
        return new CancellationRiderRequest();
    }

}
