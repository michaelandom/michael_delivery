package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.CancellationRiderRequest;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import com.michael_delivery.backend.repos.CancellationRiderRequestRepository;
import com.michael_delivery.backend.repos.OrdersRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CancellationRiderRequestService {

    private final CancellationRiderRequestRepository cancellationRiderRequestRepository;
    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;

    public CancellationRiderRequestService(
            final CancellationRiderRequestRepository cancellationRiderRequestRepository,
            final OrdersRepository ordersRepository, final UsersRepository usersRepository) {
        this.cancellationRiderRequestRepository = cancellationRiderRequestRepository;
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
    }

    public List<CancellationRiderRequestDTO> findAll() {
        final List<CancellationRiderRequest> cancellationRiderRequests = cancellationRiderRequestRepository.findAll(Sort.by("cancellationRiderRequestId"));
        return cancellationRiderRequests.stream()
                .map(cancellationRiderRequest -> mapToDTO(cancellationRiderRequest, new CancellationRiderRequestDTO()))
                .toList();
    }

    public CancellationRiderRequestDTO get(final Long cancellationRiderRequestId) {
        return cancellationRiderRequestRepository.findById(cancellationRiderRequestId)
                .map(cancellationRiderRequest -> mapToDTO(cancellationRiderRequest, new CancellationRiderRequestDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CancellationRiderRequestDTO cancellationRiderRequestDTO) {
        final CancellationRiderRequest cancellationRiderRequest = new CancellationRiderRequest();
        mapToEntity(cancellationRiderRequestDTO, cancellationRiderRequest);
        return cancellationRiderRequestRepository.save(cancellationRiderRequest).getCancellationRiderRequestId();
    }

    public void update(final Long cancellationRiderRequestId,
            final CancellationRiderRequestDTO cancellationRiderRequestDTO) {
        final CancellationRiderRequest cancellationRiderRequest = cancellationRiderRequestRepository.findById(cancellationRiderRequestId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cancellationRiderRequestDTO, cancellationRiderRequest);
        cancellationRiderRequestRepository.save(cancellationRiderRequest);
    }

    public void delete(final Long cancellationRiderRequestId) {
        cancellationRiderRequestRepository.deleteById(cancellationRiderRequestId);
    }

    private CancellationRiderRequestDTO mapToDTO(
            final CancellationRiderRequest cancellationRiderRequest,
            final CancellationRiderRequestDTO cancellationRiderRequestDTO) {
        cancellationRiderRequestDTO.setCancellationRiderRequestId(cancellationRiderRequest.getCancellationRiderRequestId());
        cancellationRiderRequestDTO.setStatus(cancellationRiderRequest.getStatus());
        cancellationRiderRequestDTO.setReason(cancellationRiderRequest.getReason());
        cancellationRiderRequestDTO.setPhotoUrls(cancellationRiderRequest.getPhotoUrls());
        cancellationRiderRequestDTO.setRemark(cancellationRiderRequest.getRemark());
        cancellationRiderRequestDTO.setResponseAt(cancellationRiderRequest.getResponseAt());
        cancellationRiderRequestDTO.setResponseBy(cancellationRiderRequest.getResponseBy());
        cancellationRiderRequestDTO.setOrder(cancellationRiderRequest.getOrder() == null ? null : cancellationRiderRequest.getOrder().getOrderId());
        cancellationRiderRequestDTO.setCancelledBy(cancellationRiderRequest.getCancelledBy() == null ? null : cancellationRiderRequest.getCancelledBy().getUserId());
        return cancellationRiderRequestDTO;
    }

    private CancellationRiderRequest mapToEntity(
            final CancellationRiderRequestDTO cancellationRiderRequestDTO,
            final CancellationRiderRequest cancellationRiderRequest) {
        cancellationRiderRequest.setStatus(cancellationRiderRequestDTO.getStatus());
        cancellationRiderRequest.setReason(cancellationRiderRequestDTO.getReason());
        cancellationRiderRequest.setPhotoUrls(cancellationRiderRequestDTO.getPhotoUrls());
        cancellationRiderRequest.setRemark(cancellationRiderRequestDTO.getRemark());
        cancellationRiderRequest.setResponseAt(cancellationRiderRequestDTO.getResponseAt());
        cancellationRiderRequest.setResponseBy(cancellationRiderRequestDTO.getResponseBy());
        final Orders order = cancellationRiderRequestDTO.getOrder() == null ? null : ordersRepository.findById(cancellationRiderRequestDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        cancellationRiderRequest.setOrder(order);
        final Users cancelledBy = cancellationRiderRequestDTO.getCancelledBy() == null ? null : usersRepository.findById(cancellationRiderRequestDTO.getCancelledBy())
                .orElseThrow(() -> new NotFoundException("cancelledBy not found"));
        cancellationRiderRequest.setCancelledBy(cancelledBy);
        return cancellationRiderRequest;
    }

}
