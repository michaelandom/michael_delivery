package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.QuestionOptions;
import com.michael_delivery.backend.model.RiderAnswers;
import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.dto.RiderAnswersDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RiderAnswersRepository extends JpaRepository<RiderAnswers, Long>  ,BaseRepository<RiderAnswersDTO,RiderAnswers>{

    RiderAnswers findFirstByRider(Riders riders);

    RiderAnswers findFirstByOption(QuestionOptions questionOptions);

}
