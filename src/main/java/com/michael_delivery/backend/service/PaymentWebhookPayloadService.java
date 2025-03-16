package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.PaymentWebhookPayload;
import com.michael_delivery.backend.dto.PaymentWebhookPayloadDTO;
import com.michael_delivery.backend.repository.PaymentWebhookPayloadRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class PaymentWebhookPayloadService extends BaseService<PaymentWebhookPayload, PaymentWebhookPayloadDTO,Long, PaymentWebhookPayloadRepository> {

    private final PaymentWebhookPayloadRepository paymentWebhookPayloadRepository;

    public PaymentWebhookPayloadService(
            final PaymentWebhookPayloadRepository paymentWebhookPayloadRepository) {
        super(paymentWebhookPayloadRepository,"paymentWebhookPayloadId");

        this.paymentWebhookPayloadRepository = paymentWebhookPayloadRepository;
    }

    @Override
    public Page<PaymentWebhookPayloadDTO> search(Specification<PaymentWebhookPayload> query, Pageable pageable) {
        return this.paymentWebhookPayloadRepository.findAll(query, pageable);
    }

    @Override
    protected PaymentWebhookPayloadDTO mapToDTO(final PaymentWebhookPayload paymentWebhookPayload,
            final PaymentWebhookPayloadDTO paymentWebhookPayloadDTO) {
        paymentWebhookPayloadDTO.setPaymentWebhookPayloadId(paymentWebhookPayload.getPaymentWebhookPayloadId());
        paymentWebhookPayloadDTO.setPspReference(paymentWebhookPayload.getPspReference());
        paymentWebhookPayloadDTO.setMerchantReference(paymentWebhookPayload.getMerchantReference());
        paymentWebhookPayloadDTO.setOriginalReference(paymentWebhookPayload.getOriginalReference());
        paymentWebhookPayloadDTO.setEventCode(paymentWebhookPayload.getEventCode());
        paymentWebhookPayloadDTO.setReason(paymentWebhookPayload.getReason());
        paymentWebhookPayloadDTO.setPaymentMethod(paymentWebhookPayload.getPaymentMethod());
        paymentWebhookPayloadDTO.setAmount(paymentWebhookPayload.getAmount());
        paymentWebhookPayloadDTO.setSuccess(paymentWebhookPayload.getSuccess());
        paymentWebhookPayloadDTO.setPayload(paymentWebhookPayload.getPayload());
        paymentWebhookPayloadDTO.setPayload(paymentWebhookPayload.getPayload());
        return paymentWebhookPayloadDTO;
    }

    @Override
    protected PaymentWebhookPayload mapToEntity(
            final PaymentWebhookPayloadDTO paymentWebhookPayloadDTO,
            final PaymentWebhookPayload paymentWebhookPayload) {
        paymentWebhookPayload.setPspReference(paymentWebhookPayloadDTO.getPspReference());
        paymentWebhookPayload.setMerchantReference(paymentWebhookPayloadDTO.getMerchantReference());
        paymentWebhookPayload.setOriginalReference(paymentWebhookPayloadDTO.getOriginalReference());
        paymentWebhookPayload.setEventCode(paymentWebhookPayloadDTO.getEventCode());
        paymentWebhookPayload.setReason(paymentWebhookPayloadDTO.getReason());
        paymentWebhookPayload.setPaymentMethod(paymentWebhookPayloadDTO.getPaymentMethod());
        paymentWebhookPayload.setAmount(paymentWebhookPayloadDTO.getAmount());
        paymentWebhookPayload.setPayload(paymentWebhookPayloadDTO.getPayload());
        paymentWebhookPayload.setSuccess(paymentWebhookPayloadDTO.getSuccess());
        return paymentWebhookPayload;
    }

    @Override
    protected PaymentWebhookPayloadDTO createDTO() {
        return new PaymentWebhookPayloadDTO();
    }

    @Override
    protected PaymentWebhookPayload createEntity() {
        return new PaymentWebhookPayload();
    }

}
