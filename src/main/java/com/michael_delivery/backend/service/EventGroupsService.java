package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.EventGroups;
import com.michael_delivery.backend.domain.Events;
import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.model.EventGroupsDTO;
import com.michael_delivery.backend.repos.EventGroupsRepository;
import com.michael_delivery.backend.repos.EventsRepository;
import com.michael_delivery.backend.repos.GroupsRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventGroupsService {

    private final EventGroupsRepository eventGroupsRepository;
    private final GroupsRepository groupsRepository;
    private final EventsRepository eventsRepository;

    public EventGroupsService(final EventGroupsRepository eventGroupsRepository,
            final GroupsRepository groupsRepository, final EventsRepository eventsRepository) {
        this.eventGroupsRepository = eventGroupsRepository;
        this.groupsRepository = groupsRepository;
        this.eventsRepository = eventsRepository;
    }

    public List<EventGroupsDTO> findAll() {
        final List<EventGroups> eventGroupses = eventGroupsRepository.findAll(Sort.by("eventGroupId"));
        return eventGroupses.stream()
                .map(eventGroups -> mapToDTO(eventGroups, new EventGroupsDTO()))
                .toList();
    }

    public EventGroupsDTO get(final Long eventGroupId) {
        return eventGroupsRepository.findById(eventGroupId)
                .map(eventGroups -> mapToDTO(eventGroups, new EventGroupsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EventGroupsDTO eventGroupsDTO) {
        final EventGroups eventGroups = new EventGroups();
        mapToEntity(eventGroupsDTO, eventGroups);
        return eventGroupsRepository.save(eventGroups).getEventGroupId();
    }

    public void update(final Long eventGroupId, final EventGroupsDTO eventGroupsDTO) {
        final EventGroups eventGroups = eventGroupsRepository.findById(eventGroupId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventGroupsDTO, eventGroups);
        eventGroupsRepository.save(eventGroups);
    }

    public void delete(final Long eventGroupId) {
        eventGroupsRepository.deleteById(eventGroupId);
    }

    private EventGroupsDTO mapToDTO(final EventGroups eventGroups,
            final EventGroupsDTO eventGroupsDTO) {
        eventGroupsDTO.setEventGroupId(eventGroups.getEventGroupId());
        eventGroupsDTO.setGroup(eventGroups.getGroup() == null ? null : eventGroups.getGroup().getGroupId());
        eventGroupsDTO.setEvent(eventGroups.getEvent() == null ? null : eventGroups.getEvent().getEventId());
        return eventGroupsDTO;
    }

    private EventGroups mapToEntity(final EventGroupsDTO eventGroupsDTO,
            final EventGroups eventGroups) {
        final Groups group = eventGroupsDTO.getGroup() == null ? null : groupsRepository.findById(eventGroupsDTO.getGroup())
                .orElseThrow(() -> new NotFoundException("group not found"));
        eventGroups.setGroup(group);
        final Events event = eventGroupsDTO.getEvent() == null ? null : eventsRepository.findById(eventGroupsDTO.getEvent())
                .orElseThrow(() -> new NotFoundException("event not found"));
        eventGroups.setEvent(event);
        return eventGroups;
    }

}
