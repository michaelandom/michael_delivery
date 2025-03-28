package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.EventGroups;
import com.michael_delivery.backend.model.Events;
import com.michael_delivery.backend.model.Groups;
import com.michael_delivery.backend.dto.EventGroupsDTO;
import com.michael_delivery.backend.repository.EventGroupsRepository;
import com.michael_delivery.backend.repository.EventsRepository;
import com.michael_delivery.backend.repository.GroupsRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class EventGroupsService extends BaseService<EventGroups, EventGroupsDTO,Long, EventGroupsRepository> {

    private final EventGroupsRepository eventGroupsRepository;
    private final GroupsRepository groupsRepository;
    private final EventsRepository eventsRepository;

    public EventGroupsService(final EventGroupsRepository eventGroupsRepository,
            final GroupsRepository groupsRepository, final EventsRepository eventsRepository) {
        super(eventGroupsRepository,"eventGroupId");
        this.eventGroupsRepository = eventGroupsRepository;
        this.groupsRepository = groupsRepository;
        this.eventsRepository = eventsRepository;
    }

    @Override
    public Page<EventGroupsDTO> search(Specification<EventGroups> query, Pageable pageable) {
        return this.eventGroupsRepository.findAll(query, pageable);
    }

    @Override
    protected EventGroupsDTO mapToDTO(final EventGroups eventGroups,
            final EventGroupsDTO eventGroupsDTO) {
        eventGroupsDTO.setEventGroupId(eventGroups.getEventGroupId());
        eventGroupsDTO.setGroup(eventGroups.getGroup() == null ? null : eventGroups.getGroup().getGroupId());
        eventGroupsDTO.setEvent(eventGroups.getEvent() == null ? null : eventGroups.getEvent().getEventId());
        return eventGroupsDTO;
    }

    @Override
    protected EventGroups mapToEntity(final EventGroupsDTO eventGroupsDTO,
            final EventGroups eventGroups) {
        final Groups group = eventGroupsDTO.getGroup() == null ? null : groupsRepository.findById(eventGroupsDTO.getGroup())
                .orElseThrow(() -> new NotFoundException("group not found"));
        eventGroups.setGroup(group);
        final Events event = eventGroupsDTO.getEvent() == null ? null : eventsRepository.findById(eventGroupsDTO.getEvent())
                .orElseThrow(() -> new NotFoundException("event not found"));
        eventGroups.setEvent(event);
        return eventGroups;
    }

    @Override
    protected EventGroupsDTO createDTO() {
        return new EventGroupsDTO();
    }

    @Override
    protected EventGroups createEntity() {
        return new EventGroups();
    }

}
