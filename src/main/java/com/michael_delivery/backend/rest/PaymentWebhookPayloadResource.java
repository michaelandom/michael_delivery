package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.PaymentWebhookPayloadDTO;
import com.michael_delivery.backend.service.PaymentWebhookPayloadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/paymentWebhookPayloads", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentWebhookPayloadResource {

    private final PaymentWebhookPayloadService paymentWebhookPayloadService;

    public PaymentWebhookPayloadResource(
            final PaymentWebhookPayloadService paymentWebhookPayloadService) {
        this.paymentWebhookPayloadService = paymentWebhookPayloadService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentWebhookPayloadDTO>> getAllPaymentWebhookPayloads() {
        return ResponseEntity.ok(paymentWebhookPayloadService.findAll());
    }

    @GetMapping("/{paymentWebhookPayloadId}")
    public ResponseEntity<PaymentWebhookPayloadDTO> getPaymentWebhookPayload(
            @PathVariable(name = "paymentWebhookPayloadId") final Long paymentWebhookPayloadId) {
        return ResponseEntity.ok(paymentWebhookPayloadService.get(paymentWebhookPayloadId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPaymentWebhookPayload(
            @RequestBody @Valid final PaymentWebhookPayloadDTO paymentWebhookPayloadDTO) {
        final Long createdPaymentWebhookPayloadId = paymentWebhookPayloadService.create(paymentWebhookPayloadDTO);
        return new ResponseEntity<>(createdPaymentWebhookPayloadId, HttpStatus.CREATED);
    }

    @PutMapping("/{paymentWebhookPayloadId}")
    public ResponseEntity<Long> updatePaymentWebhookPayload(
            @PathVariable(name = "paymentWebhookPayloadId") final Long paymentWebhookPayloadId,
            @RequestBody @Valid final PaymentWebhookPayloadDTO paymentWebhookPayloadDTO) {
        paymentWebhookPayloadService.update(paymentWebhookPayloadId, paymentWebhookPayloadDTO);
        return ResponseEntity.ok(paymentWebhookPayloadId);
    }

    @DeleteMapping("/{paymentWebhookPayloadId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePaymentWebhookPayload(
            @PathVariable(name = "paymentWebhookPayloadId") final Long paymentWebhookPayloadId) {
        paymentWebhookPayloadService.delete(paymentWebhookPayloadId);
        return ResponseEntity.noContent().build();
    }

}
