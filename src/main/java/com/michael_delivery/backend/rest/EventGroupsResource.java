package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.EventGroups;
import com.michael_delivery.backend.model.EventGroupsDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Events;
import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.model.EventGroupsDTO;
import com.michael_delivery.backend.repos.EventsRepository;
import com.michael_delivery.backend.repos.GroupsRepository;
import com.michael_delivery.backend.service.EventGroupsService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/eventGroups", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventGroupsResource {

    private final EventGroupsService eventGroupsService;
    private final GroupsRepository groupsRepository;
    private final EventsRepository eventsRepository;

    public EventGroupsResource(final EventGroupsService eventGroupsService,
            final GroupsRepository groupsRepository, final EventsRepository eventsRepository) {
        this.eventGroupsService = eventGroupsService;
        this.groupsRepository = groupsRepository;
        this.eventsRepository = eventsRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventGroupsDTO>> getAllEventGroups(
    ) {
        return ResponseEntity.ok(eventGroupsService.findAll());
    }


    @GetMapping
    public ResponseEntity<Page<EventGroupsDTO>> getAllEventGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "eventGroupId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(eventGroupsService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{eventGroupId}")
    public ResponseEntity<EventGroupsDTO> getEventGroups(
            @PathVariable(name = "eventGroupId") final Long eventGroupId) {
        return ResponseEntity.ok(eventGroupsService.get(eventGroupId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEventGroups(
            @RequestBody @Valid final EventGroupsDTO eventGroupsDTO) {
        final Long createdEventGroupId = eventGroupsService.create(eventGroupsDTO);
        return new ResponseEntity<>(createdEventGroupId, HttpStatus.CREATED);
    }

    @PutMapping("/{eventGroupId}")
    public ResponseEntity<Long> updateEventGroups(
            @PathVariable(name = "eventGroupId") final Long eventGroupId,
            @RequestBody @Valid final EventGroupsDTO eventGroupsDTO) {
        eventGroupsService.update(eventGroupId, eventGroupsDTO);
        return ResponseEntity.ok(eventGroupId);
    }

    @DeleteMapping("/{eventGroupId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEventGroups(
            @PathVariable(name = "eventGroupId") final Long eventGroupId) {
        eventGroupsService.delete(eventGroupId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/groupValues")
    public ResponseEntity<Map<Long, String>> getGroupValues() {
        return ResponseEntity.ok(groupsRepository.findAll(Sort.by("groupId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Groups::getGroupId, Groups::getName)));
    }

    @GetMapping("/eventValues")
    public ResponseEntity<Map<Long, Long>> getEventValues() {
        return ResponseEntity.ok(eventsRepository.findAll(Sort.by("eventId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Events::getEventId, Events::getEventId)));
    }

}
