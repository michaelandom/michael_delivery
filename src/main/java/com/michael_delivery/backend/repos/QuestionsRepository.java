package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Questions;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionsRepository extends JpaRepository<Questions, Long> {
}
