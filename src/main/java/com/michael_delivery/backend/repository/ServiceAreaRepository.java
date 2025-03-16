package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.ServiceArea;
import com.michael_delivery.backend.model.State;
import com.michael_delivery.backend.dto.ServiceAreaDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceAreaRepository extends JpaRepository<ServiceArea, Long>  ,BaseRepository<ServiceAreaDTO,ServiceArea> {

    ServiceArea findFirstByState(State state);


}
