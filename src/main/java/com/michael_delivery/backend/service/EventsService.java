package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.EventGroups;
import com.michael_delivery.backend.domain.Events;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.EventGroupsDTO;
import com.michael_delivery.backend.model.EventsDTO;
import com.michael_delivery.backend.repos.EventGroupsRepository;
import com.michael_delivery.backend.repos.EventsRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventsService extends BaseService<Events, EventsDTO,Long, EventsRepository>  {

    private final EventsRepository eventsRepository;
    private final EventGroupsRepository eventGroupsRepository;

    public EventsService(final EventsRepository eventsRepository,
            final EventGroupsRepository eventGroupsRepository) {
        super(eventsRepository,"eventId");
        this.eventsRepository = eventsRepository;
        this.eventGroupsRepository = eventGroupsRepository;
    }

    @Override
    public Page<EventsDTO> search(Specification<Events> query, Pageable pageable) {
        return this.eventsRepository.findAll(query, pageable);
    }
    @Override
    protected EventsDTO mapToDTO(final Events events, final EventsDTO eventsDTO) {
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

    @Override
    protected Events mapToEntity(final EventsDTO eventsDTO, final Events events) {
        events.setTitle(eventsDTO.getTitle());
        events.setLink(eventsDTO.getLink());
        events.setContents(eventsDTO.getContents());
        events.setStartDate(eventsDTO.getStartDate());
        events.setEndDate(eventsDTO.getEndDate());
        events.setSendPushNotification(eventsDTO.getSendPushNotification());
        events.setBannerImageUrl(eventsDTO.getBannerImageUrl());
        return events;
    }

    @Override
    protected EventsDTO createDTO() {
        return new EventsDTO();
    }

    @Override
    protected Events createEntity() {
        return new Events();
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
