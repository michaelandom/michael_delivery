package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StateRepository extends JpaRepository<State, Long> {
}
