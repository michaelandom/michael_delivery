package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Questions;
import com.michael_delivery.backend.dto.QuestionsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionsRepository extends JpaRepository<Questions, Long>  ,BaseRepository<QuestionsDTO,Questions>{
    public Page<QuestionsDTO> findAll(Specification<Questions> spec, Pageable pageable);

}
