package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.EventGroups;
import com.michael_delivery.backend.domain.Events;
import com.michael_delivery.backend.model.EventsDTO;
import com.michael_delivery.backend.repos.EventGroupsRepository;
import com.michael_delivery.backend.repos.EventsRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventsService {

    private final EventsRepository eventsRepository;
    private final EventGroupsRepository eventGroupsRepository;

    public EventsService(final EventsRepository eventsRepository,
            final EventGroupsRepository eventGroupsRepository) {
        this.eventsRepository = eventsRepository;
        this.eventGroupsRepository = eventGroupsRepository;
    }

    public List<EventsDTO> findAll() {
        final List<Events> events = eventsRepository.findAll(Sort.by("eventId"));
        return events.stream()
                .map(event -> mapToDTO(event, new EventsDTO()))
                .toList();
    }

    public EventsDTO get(final Long eventId) {
        return eventsRepository.findById(eventId)
                .map(events -> mapToDTO(events, new EventsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EventsDTO eventsDTO) {
        final Events events = new Events();
        mapToEntity(eventsDTO, events);
        return eventsRepository.save(events).getEventId();
    }

    public void update(final Long eventId, final EventsDTO eventsDTO) {
        final Events events = eventsRepository.findById(eventId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventsDTO, events);
        eventsRepository.save(events);
    }

    public void delete(final Long eventId) {
        eventsRepository.deleteById(eventId);
    }

    private EventsDTO mapToDTO(final Events events, final EventsDTO eventsDTO) {
        eventsDTO.setEventId(events.getEventId());
        eventsDTO.setTitle(events.getTitle());
        eventsDTO.setLink(events.getLink());
        eventsDTO.setContents(events.getContents());
        eventsDTO.setStartDate(events.getStartDate());
        eventsDTO.setEndDate(events.getEndDate());
        eventsDTO.setSendPushNotification(events.getSendPushNotification());
        eventsDTO.setBannerImageUrl(events.getBannerImageUrl());
        return eventsDTO;
    }

    private Events mapToEntity(final EventsDTO eventsDTO, final Events events) {
        events.setTitle(eventsDTO.getTitle());
        events.setLink(eventsDTO.getLink());
        events.setContents(eventsDTO.getContents());
        events.setStartDate(eventsDTO.getStartDate());
        events.setEndDate(eventsDTO.getEndDate());
        events.setSendPushNotification(eventsDTO.getSendPushNotification());
        events.setBannerImageUrl(eventsDTO.getBannerImageUrl());
        return events;
    }

    public ReferencedWarning getReferencedWarning(final Long eventId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Events events = eventsRepository.findById(eventId)
                .orElseThrow(NotFoundException::new);
        final EventGroups eventEventGroups = eventGroupsRepository.findFirstByEvent(events);
        if (eventEventGroups != null) {
            referencedWarning.setKey("events.eventGroups.event.referenced");
            referencedWarning.addParam(eventEventGroups.getEventGroupId());
            return referencedWarning;
        }
        return null;
    }

}
