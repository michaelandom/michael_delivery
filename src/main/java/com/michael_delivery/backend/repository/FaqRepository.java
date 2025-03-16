package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Faq;
import com.michael_delivery.backend.dto.FaqDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FaqRepository extends JpaRepository<Faq, Long>,BaseRepository<FaqDTO,Faq> {
}
