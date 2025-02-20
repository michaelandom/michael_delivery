package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.QuestionOptions;
import com.michael_delivery.backend.domain.RiderAnswers;
import com.michael_delivery.backend.domain.Riders;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RiderAnswersRepository extends JpaRepository<RiderAnswers, Long> {

    RiderAnswers findFirstByRider(Riders riders);

    RiderAnswers findFirstByOption(QuestionOptions questionOptions);

}
