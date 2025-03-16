package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.CancellationRequest;
import com.michael_delivery.backend.model.Orders;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.CancellationRequestDTO;
import com.michael_delivery.backend.repository.CancellationRequestRepository;
import com.michael_delivery.backend.repository.OrdersRepository;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class CancellationRequestService extends BaseService<CancellationRequest, CancellationRequestDTO,Long, CancellationRequestRepository> {

    private final CancellationRequestRepository cancellationRequestRepository;
    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;

    public CancellationRequestService(
            final CancellationRequestRepository cancellationRequestRepository,
            final OrdersRepository ordersRepository, final UsersRepository usersRepository) {
        super(cancellationRequestRepository,"cancellationRequestId");
        this.cancellationRequestRepository = cancellationRequestRepository;
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Page<CancellationRequestDTO> search(Specification<CancellationRequest> query, Pageable pageable) {
        return this.cancellationRequestRepository.findAll(query, pageable);
    }
    @Override
    protected CancellationRequestDTO createDTO() {
        return new CancellationRequestDTO();
    }

    @Override
    protected CancellationRequest createEntity() {
        return new CancellationRequest();
    }

    @Override
    protected CancellationRequestDTO mapToDTO(final CancellationRequest cancellationRequest,
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

    @Override
    protected CancellationRequest mapToEntity(final CancellationRequestDTO cancellationRequestDTO,
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
