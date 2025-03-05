package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Questions;
import com.michael_delivery.backend.model.QuestionsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionsRepository extends JpaRepository<Questions, Long>  ,BaseRepository<QuestionsDTO,Questions>{
    public Page<QuestionsDTO> findAll(Specification<Questions> spec, Pageable pageable);

}
