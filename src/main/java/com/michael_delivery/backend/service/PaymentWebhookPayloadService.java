package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.PaymentWebhookPayload;
import com.michael_delivery.backend.model.PaymentWebhookPayloadDTO;
import com.michael_delivery.backend.repos.PaymentWebhookPayloadRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PaymentWebhookPayloadService {

    private final PaymentWebhookPayloadRepository paymentWebhookPayloadRepository;

    public PaymentWebhookPayloadService(
            final PaymentWebhookPayloadRepository paymentWebhookPayloadRepository) {
        this.paymentWebhookPayloadRepository = paymentWebhookPayloadRepository;
    }

    public List<PaymentWebhookPayloadDTO> findAll() {
        final List<PaymentWebhookPayload> paymentWebhookPayloads = paymentWebhookPayloadRepository.findAll(Sort.by("paymentWebhookPayloadId"));
        return paymentWebhookPayloads.stream()
                .map(paymentWebhookPayload -> mapToDTO(paymentWebhookPayload, new PaymentWebhookPayloadDTO()))
                .toList();
    }

    public PaymentWebhookPayloadDTO get(final Long paymentWebhookPayloadId) {
        return paymentWebhookPayloadRepository.findById(paymentWebhookPayloadId)
                .map(paymentWebhookPayload -> mapToDTO(paymentWebhookPayload, new PaymentWebhookPayloadDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PaymentWebhookPayloadDTO paymentWebhookPayloadDTO) {
        final PaymentWebhookPayload paymentWebhookPayload = new PaymentWebhookPayload();
        mapToEntity(paymentWebhookPayloadDTO, paymentWebhookPayload);
        return paymentWebhookPayloadRepository.save(paymentWebhookPayload).getPaymentWebhookPayloadId();
    }

    public void update(final Long paymentWebhookPayloadId,
            final PaymentWebhookPayloadDTO paymentWebhookPayloadDTO) {
        final PaymentWebhookPayload paymentWebhookPayload = paymentWebhookPayloadRepository.findById(paymentWebhookPayloadId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(paymentWebhookPayloadDTO, paymentWebhookPayload);
        paymentWebhookPayloadRepository.save(paymentWebhookPayload);
    }

    public void delete(final Long paymentWebhookPayloadId) {
        paymentWebhookPayloadRepository.deleteById(paymentWebhookPayloadId);
    }

    private PaymentWebhookPayloadDTO mapToDTO(final PaymentWebhookPayload paymentWebhookPayload,
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
        return paymentWebhookPayloadDTO;
    }

    private PaymentWebhookPayload mapToEntity(
            final PaymentWebhookPayloadDTO paymentWebhookPayloadDTO,
            final PaymentWebhookPayload paymentWebhookPayload) {
        paymentWebhookPayload.setPspReference(paymentWebhookPayloadDTO.getPspReference());
        paymentWebhookPayload.setMerchantReference(paymentWebhookPayloadDTO.getMerchantReference());
        paymentWebhookPayload.setOriginalReference(paymentWebhookPayloadDTO.getOriginalReference());
        paymentWebhookPayload.setEventCode(paymentWebhookPayloadDTO.getEventCode());
        paymentWebhookPayload.setReason(paymentWebhookPayloadDTO.getReason());
        paymentWebhookPayload.setPaymentMethod(paymentWebhookPayloadDTO.getPaymentMethod());
        paymentWebhookPayload.setAmount(paymentWebhookPayloadDTO.getAmount());
        paymentWebhookPayload.setSuccess(paymentWebhookPayloadDTO.getSuccess());
        return paymentWebhookPayload;
    }

}
