package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.PaymentWebhookPayload;
import com.michael_delivery.backend.dto.PaymentWebhookPayloadDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentWebhookPayloadRepository extends JpaRepository<PaymentWebhookPayload, Long> ,BaseRepository<PaymentWebhookPayloadDTO,PaymentWebhookPayload> {
}
