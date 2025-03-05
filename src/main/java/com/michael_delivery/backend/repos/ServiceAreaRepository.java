package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.ServiceArea;
import com.michael_delivery.backend.domain.State;
import com.michael_delivery.backend.model.ServiceAreaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceAreaRepository extends JpaRepository<ServiceArea, Long>  ,BaseRepository<ServiceAreaDTO,ServiceArea> {

    ServiceArea findFirstByState(State state);


}
