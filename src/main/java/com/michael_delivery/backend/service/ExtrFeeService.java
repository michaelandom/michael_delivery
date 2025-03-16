package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.ExtrFee;
import com.michael_delivery.backend.model.Orders;
import com.michael_delivery.backend.dto.ExtrFeeDTO;
import com.michael_delivery.backend.repository.ExtrFeeRepository;
import com.michael_delivery.backend.repository.OrdersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class ExtrFeeService extends BaseService<ExtrFee, ExtrFeeDTO,Long, ExtrFeeRepository>{

    private final ExtrFeeRepository extrFeeRepository;
    private final OrdersRepository ordersRepository;

    public ExtrFeeService(final ExtrFeeRepository extrFeeRepository,
            final OrdersRepository ordersRepository) {
        super(extrFeeRepository,"extrFeeId");
        this.extrFeeRepository = extrFeeRepository;
        this.ordersRepository = ordersRepository;
    }

    @Override
    public Page<ExtrFeeDTO> search(Specification<ExtrFee> query, Pageable pageable) {
        return this.extrFeeRepository.findAll(query, pageable);
    }

    protected ExtrFeeDTO mapToDTO(final ExtrFee extrFee, final ExtrFeeDTO extrFeeDTO) {
        extrFeeDTO.setExtrFeeId(extrFee.getExtrFeeId());
        extrFeeDTO.setMessage(extrFee.getMessage());
        extrFeeDTO.setAmount(extrFee.getAmount());
        extrFeeDTO.setCardNumber(extrFee.getCardNumber());
        extrFeeDTO.setPaymentStatus(extrFee.getPaymentStatus());
        extrFeeDTO.setPaidAt(extrFee.getPaidAt());
        extrFeeDTO.setSentAt(extrFee.getSentAt());
        extrFeeDTO.setOrder(extrFee.getOrder() == null ? null : extrFee.getOrder().getOrderId());
        return extrFeeDTO;
    }

    protected ExtrFee mapToEntity(final ExtrFeeDTO extrFeeDTO, final ExtrFee extrFee) {
        extrFee.setMessage(extrFeeDTO.getMessage());
        extrFee.setAmount(extrFeeDTO.getAmount());
        extrFee.setCardNumber(extrFeeDTO.getCardNumber());
        extrFee.setPaymentStatus(extrFeeDTO.getPaymentStatus());
        extrFee.setPaidAt(extrFeeDTO.getPaidAt());
        extrFee.setSentAt(extrFeeDTO.getSentAt());
        final Orders order = extrFeeDTO.getOrder() == null ? null : ordersRepository.findById(extrFeeDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        extrFee.setOrder(order);
        return extrFee;
    }

    @Override
    protected ExtrFeeDTO createDTO() {
        return new ExtrFeeDTO();
    }

    @Override
    protected ExtrFee createEntity() {
        return new ExtrFee();
    }

}
