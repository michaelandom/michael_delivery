package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Faq;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FaqRepository extends JpaRepository<Faq, Long> {
}
