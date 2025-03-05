package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Faq;
import com.michael_delivery.backend.model.FaqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FaqRepository extends JpaRepository<Faq, Long>,BaseRepository<FaqDTO,Faq> {
}
