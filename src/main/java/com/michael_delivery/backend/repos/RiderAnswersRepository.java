package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.QuestionOptions;
import com.michael_delivery.backend.domain.RiderAnswers;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.model.RiderAnswersDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RiderAnswersRepository extends JpaRepository<RiderAnswers, Long>  ,BaseRepository<RiderAnswersDTO,RiderAnswers>{

    RiderAnswers findFirstByRider(Riders riders);

    RiderAnswers findFirstByOption(QuestionOptions questionOptions);

}
