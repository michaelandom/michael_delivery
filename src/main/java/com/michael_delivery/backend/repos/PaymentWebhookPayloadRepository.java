package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.PaymentWebhookPayload;
import com.michael_delivery.backend.model.PaymentWebhookPayloadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentWebhookPayloadRepository extends JpaRepository<PaymentWebhookPayload, Long> ,BaseRepository<PaymentWebhookPayloadDTO,PaymentWebhookPayload> {
}
