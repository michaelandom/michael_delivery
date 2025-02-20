package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Events;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventsRepository extends JpaRepository<Events, Long> {
}
