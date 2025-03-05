package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.EventGroups;
import com.michael_delivery.backend.domain.Events;
import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.model.EventGroupsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventGroupsRepository extends JpaRepository<EventGroups, Long> ,BaseRepository<EventGroupsDTO,EventGroups> {

    EventGroups findFirstByGroup(Groups groups);

    EventGroups findFirstByEvent(Events events);

}
