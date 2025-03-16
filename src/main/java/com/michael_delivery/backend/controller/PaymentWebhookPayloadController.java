package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.PaymentWebhookPayload;
import com.michael_delivery.backend.dto.PaymentWebhookPayloadDTO;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.service.PaymentWebhookPayloadService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/paymentWebhookPayloads", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentWebhookPayloadController {

    private final PaymentWebhookPayloadService paymentWebhookPayloadService;

    public PaymentWebhookPayloadController(
            final PaymentWebhookPayloadService paymentWebhookPayloadService) {
        this.paymentWebhookPayloadService = paymentWebhookPayloadService;
    }
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_PAYMENT_WEBHOOK_PAYLOADS','VIEW_PAYMENT_WEBHOOK_PAYLOAD_MANY')")
    public ResponseEntity<List<PaymentWebhookPayloadDTO>> getAllPaymentWebhookPayload(
    ) {
        return ResponseEntity.ok(paymentWebhookPayloadService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_PAYMENT_WEBHOOK_PAYLOADS','VIEW_PAYMENT_WEBHOOK_PAYLOAD_MANY')")
    public ResponseEntity<Page<PaymentWebhookPayloadDTO>> searchPaymentWebhookPayload(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "paymentWebhookPayloadId:asc") String[] sortBy,
            @RequestParam(required = false) String pspReference,
            @RequestParam(required = false) String merchantReference,
            @RequestParam(required = false) String originalReference,
            @RequestParam(required = false) String eventCode,
            @RequestParam(required = false) String paymentMethod
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<PaymentWebhookPayload> spec = new GenericSpecification<>();
        Specification<PaymentWebhookPayload> pspReferenceSpec = spec.contains("pspReference", pspReference);
        Specification<PaymentWebhookPayload> merchantReferenceSpec = spec.contains("merchantReference", merchantReference);
        Specification<PaymentWebhookPayload> originalReferenceSpec = spec.contains("originalReference", originalReference);
        Specification<PaymentWebhookPayload> eventCodeSpec = spec.contains("eventCode", eventCode);
        Specification<PaymentWebhookPayload> paymentMethodSpec = spec.contains("paymentMethod", paymentMethod);
        Specification<PaymentWebhookPayload> finalSpec = Specification.where(pspReferenceSpec)
                .and(merchantReferenceSpec)
                .and(originalReferenceSpec)
                .and(eventCodeSpec)
                .and(paymentMethodSpec);
        return ResponseEntity.ok(paymentWebhookPayloadService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_PAYMENT_WEBHOOK_PAYLOADS','VIEW_PAYMENT_WEBHOOK_PAYLOAD_MANY')")
    public ResponseEntity<Page<PaymentWebhookPayloadDTO>> getAllPaymentWebhookPayload(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "paymentWebhookPayloadId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(paymentWebhookPayloadService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{paymentWebhookPayloadId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_PAYMENT_WEBHOOK_PAYLOADS','VIEW_PAYMENT_WEBHOOK_PAYLOAD')")
    public ResponseEntity<PaymentWebhookPayloadDTO> getPaymentWebhookPayload(
            @PathVariable(name = "paymentWebhookPayloadId") final Long paymentWebhookPayloadId) {
        return ResponseEntity.ok(paymentWebhookPayloadService.get(paymentWebhookPayloadId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_PAYMENT_WEBHOOK_PAYLOADS','UPDATE_PAYMENT_WEBHOOK_PAYLOAD_ONE')")
    public ResponseEntity<Long> createPaymentWebhookPayload(
            @RequestBody @Valid final PaymentWebhookPayloadDTO paymentWebhookPayloadDTO) {
        final Long createdPaymentWebhookPayloadId = paymentWebhookPayloadService.create(paymentWebhookPayloadDTO);
        return new ResponseEntity<>(createdPaymentWebhookPayloadId, HttpStatus.CREATED);
    }

    @PutMapping("/{paymentWebhookPayloadId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_PAYMENT_WEBHOOK_PAYLOADS','UPDATE_PAYMENT_WEBHOOK_PAYLOAD_ONE')")
    public ResponseEntity<Long> updatePaymentWebhookPayload(
            @PathVariable(name = "paymentWebhookPayloadId") final Long paymentWebhookPayloadId,
            @RequestBody @Valid final PaymentWebhookPayloadDTO paymentWebhookPayloadDTO) {
        paymentWebhookPayloadService.update(paymentWebhookPayloadId, paymentWebhookPayloadDTO);
        return ResponseEntity.ok(paymentWebhookPayloadId);
    }

    @DeleteMapping("/{paymentWebhookPayloadId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_PAYMENT_WEBHOOK_PAYLOADS','DELETE_PAYMENT_WEBHOOK_PAYLOAD_ONE')")
    public ResponseEntity<Void> deletePaymentWebhookPayload(
            @PathVariable(name = "paymentWebhookPayloadId") final Long paymentWebhookPayloadId) {
        paymentWebhookPayloadService.delete(paymentWebhookPayloadId);
        return ResponseEntity.noContent().build();
    }

}
