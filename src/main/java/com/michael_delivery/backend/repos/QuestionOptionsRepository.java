package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.QuestionOptions;
import com.michael_delivery.backend.domain.Questions;
import com.michael_delivery.backend.model.QuestionOptionsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionOptionsRepository extends JpaRepository<QuestionOptions, Long>  ,BaseRepository<QuestionOptionsDTO,QuestionOptions>{

    QuestionOptions findFirstByQuestion(Questions questions);

    public Page<QuestionOptionsDTO> findAll(Specification<QuestionOptions> spec, Pageable pageable);

}
