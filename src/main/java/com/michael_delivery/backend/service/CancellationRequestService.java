package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.CancellationRequest;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.CancellationRequestDTO;
import com.michael_delivery.backend.repos.CancellationRequestRepository;
import com.michael_delivery.backend.repos.OrdersRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CancellationRequestService {

    private final CancellationRequestRepository cancellationRequestRepository;
    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;

    public CancellationRequestService(
            final CancellationRequestRepository cancellationRequestRepository,
            final OrdersRepository ordersRepository, final UsersRepository usersRepository) {
        this.cancellationRequestRepository = cancellationRequestRepository;
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
    }

    public List<CancellationRequestDTO> findAll() {
        final List<CancellationRequest> cancellationRequests = cancellationRequestRepository.findAll(Sort.by("cancellationRequestId"));
        return cancellationRequests.stream()
                .map(cancellationRequest -> mapToDTO(cancellationRequest, new CancellationRequestDTO()))
                .toList();
    }

    public CancellationRequestDTO get(final Long cancellationRequestId) {
        return cancellationRequestRepository.findById(cancellationRequestId)
                .map(cancellationRequest -> mapToDTO(cancellationRequest, new CancellationRequestDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CancellationRequestDTO cancellationRequestDTO) {
        final CancellationRequest cancellationRequest = new CancellationRequest();
        mapToEntity(cancellationRequestDTO, cancellationRequest);
        return cancellationRequestRepository.save(cancellationRequest).getCancellationRequestId();
    }

    public void update(final Long cancellationRequestId,
            final CancellationRequestDTO cancellationRequestDTO) {
        final CancellationRequest cancellationRequest = cancellationRequestRepository.findById(cancellationRequestId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cancellationRequestDTO, cancellationRequest);
        cancellationRequestRepository.save(cancellationRequest);
    }

    public void delete(final Long cancellationRequestId) {
        cancellationRequestRepository.deleteById(cancellationRequestId);
    }

    private CancellationRequestDTO mapToDTO(final CancellationRequest cancellationRequest,
            final CancellationRequestDTO cancellationRequestDTO) {
        cancellationRequestDTO.setCancellationRequestId(cancellationRequest.getCancellationRequestId());
        cancellationRequestDTO.setType(cancellationRequest.getType());
        cancellationRequestDTO.setStatus(cancellationRequest.getStatus());
        cancellationRequestDTO.setCancellationFee(cancellationRequest.getCancellationFee());
        cancellationRequestDTO.setRefundAmount(cancellationRequest.getRefundAmount());
        cancellationRequestDTO.setReason(cancellationRequest.getReason());
        cancellationRequestDTO.setPhotoUrls(cancellationRequest.getPhotoUrls());
        cancellationRequestDTO.setRemark(cancellationRequest.getRemark());
        cancellationRequestDTO.setCancelledByType(cancellationRequest.getCancelledByType());
        cancellationRequestDTO.setPaidAt(cancellationRequest.getPaidAt());
        cancellationRequestDTO.setOrder(cancellationRequest.getOrder() == null ? null : cancellationRequest.getOrder().getOrderId());
        cancellationRequestDTO.setCancelledBy(cancellationRequest.getCancelledBy() == null ? null : cancellationRequest.getCancelledBy().getUserId());
        return cancellationRequestDTO;
    }

    private CancellationRequest mapToEntity(final CancellationRequestDTO cancellationRequestDTO,
            final CancellationRequest cancellationRequest) {
        cancellationRequest.setType(cancellationRequestDTO.getType());
        cancellationRequest.setStatus(cancellationRequestDTO.getStatus());
        cancellationRequest.setCancellationFee(cancellationRequestDTO.getCancellationFee());
        cancellationRequest.setRefundAmount(cancellationRequestDTO.getRefundAmount());
        cancellationRequest.setReason(cancellationRequestDTO.getReason());
        cancellationRequest.setPhotoUrls(cancellationRequestDTO.getPhotoUrls());
        cancellationRequest.setRemark(cancellationRequestDTO.getRemark());
        cancellationRequest.setCancelledByType(cancellationRequestDTO.getCancelledByType());
        cancellationRequest.setPaidAt(cancellationRequestDTO.getPaidAt());
        final Orders order = cancellationRequestDTO.getOrder() == null ? null : ordersRepository.findById(cancellationRequestDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        cancellationRequest.setOrder(order);
        final Users cancelledBy = cancellationRequestDTO.getCancelledBy() == null ? null : usersRepository.findById(cancellationRequestDTO.getCancelledBy())
                .orElseThrow(() -> new NotFoundException("cancelledBy not found"));
        cancellationRequest.setCancelledBy(cancelledBy);
        return cancellationRequest;
    }

}
