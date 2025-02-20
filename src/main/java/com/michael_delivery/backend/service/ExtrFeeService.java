package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.ExtrFee;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.model.ExtrFeeDTO;
import com.michael_delivery.backend.repos.ExtrFeeRepository;
import com.michael_delivery.backend.repos.OrdersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExtrFeeService {

    private final ExtrFeeRepository extrFeeRepository;
    private final OrdersRepository ordersRepository;

    public ExtrFeeService(final ExtrFeeRepository extrFeeRepository,
            final OrdersRepository ordersRepository) {
        this.extrFeeRepository = extrFeeRepository;
        this.ordersRepository = ordersRepository;
    }

    public List<ExtrFeeDTO> findAll() {
        final List<ExtrFee> extrFees = extrFeeRepository.findAll(Sort.by("extrFeeId"));
        return extrFees.stream()
                .map(extrFee -> mapToDTO(extrFee, new ExtrFeeDTO()))
                .toList();
    }

    public ExtrFeeDTO get(final Long extrFeeId) {
        return extrFeeRepository.findById(extrFeeId)
                .map(extrFee -> mapToDTO(extrFee, new ExtrFeeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ExtrFeeDTO extrFeeDTO) {
        final ExtrFee extrFee = new ExtrFee();
        mapToEntity(extrFeeDTO, extrFee);
        return extrFeeRepository.save(extrFee).getExtrFeeId();
    }

    public void update(final Long extrFeeId, final ExtrFeeDTO extrFeeDTO) {
        final ExtrFee extrFee = extrFeeRepository.findById(extrFeeId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(extrFeeDTO, extrFee);
        extrFeeRepository.save(extrFee);
    }

    public void delete(final Long extrFeeId) {
        extrFeeRepository.deleteById(extrFeeId);
    }

    private ExtrFeeDTO mapToDTO(final ExtrFee extrFee, final ExtrFeeDTO extrFeeDTO) {
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

    private ExtrFee mapToEntity(final ExtrFeeDTO extrFeeDTO, final ExtrFee extrFee) {
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

}
