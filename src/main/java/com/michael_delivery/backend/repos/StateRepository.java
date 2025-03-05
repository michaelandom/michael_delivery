package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.State;
import com.michael_delivery.backend.model.StateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StateRepository extends JpaRepository<State, Long>  ,BaseRepository<StateDTO,State>{
}
