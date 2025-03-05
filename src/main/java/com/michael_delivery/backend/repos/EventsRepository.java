package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Events;
import com.michael_delivery.backend.model.EventsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventsRepository extends JpaRepository<Events, Long> ,BaseRepository<EventsDTO,Events> {

}
