package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.EventGroups;
import com.michael_delivery.backend.model.Events;
import com.michael_delivery.backend.model.Groups;
import com.michael_delivery.backend.dto.EventGroupsDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventGroupsRepository extends JpaRepository<EventGroups, Long> ,BaseRepository<EventGroupsDTO,EventGroups> {

    EventGroups findFirstByGroup(Groups groups);

    EventGroups findFirstByEvent(Events events);

}
