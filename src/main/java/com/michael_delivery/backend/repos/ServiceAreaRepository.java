package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.ServiceArea;
import com.michael_delivery.backend.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceAreaRepository extends JpaRepository<ServiceArea, Long> {

    ServiceArea findFirstByState(State state);

}
