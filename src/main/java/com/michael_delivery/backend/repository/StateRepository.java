package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.State;
import com.michael_delivery.backend.dto.StateDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StateRepository extends JpaRepository<State, Long>  ,BaseRepository<StateDTO,State>{
}
