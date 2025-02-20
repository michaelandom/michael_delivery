package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.PaymentWebhookPayload;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentWebhookPayloadRepository extends JpaRepository<PaymentWebhookPayload, Long> {
}
