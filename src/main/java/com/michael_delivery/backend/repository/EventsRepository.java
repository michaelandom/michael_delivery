package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Events;
import com.michael_delivery.backend.dto.EventsDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventsRepository extends JpaRepository<Events, Long> ,BaseRepository<EventsDTO,Events> {

}
