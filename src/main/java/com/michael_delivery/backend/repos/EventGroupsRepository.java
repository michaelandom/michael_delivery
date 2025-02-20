package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.EventGroups;
import com.michael_delivery.backend.domain.Events;
import com.michael_delivery.backend.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventGroupsRepository extends JpaRepository<EventGroups, Long> {

    EventGroups findFirstByGroup(Groups groups);

    EventGroups findFirstByEvent(Events events);

}
