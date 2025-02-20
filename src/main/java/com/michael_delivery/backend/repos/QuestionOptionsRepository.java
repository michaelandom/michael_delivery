package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.QuestionOptions;
import com.michael_delivery.backend.domain.Questions;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionOptionsRepository extends JpaRepository<QuestionOptions, Long> {

    QuestionOptions findFirstByQuestion(Questions questions);

}
