package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.DriverGuide;
import com.michael_delivery.backend.domain.Events;
import com.michael_delivery.backend.model.EventsDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.EventsDTO;
import com.michael_delivery.backend.service.EventsService;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventsResource {

    private final EventsService eventsService;

    public EventsResource(final EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventsDTO>> getAllEvents(
    ) {
        return ResponseEntity.ok(eventsService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EventsDTO>> searchEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "eventId:asc") String[] sortBy,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String contents,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            @RequestParam(required = false) boolean startDateIsAfter,
            @RequestParam(required = false) boolean endDateIsAfter
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Events> spec = new GenericSpecification<>();
        Specification<Events> titleSpec = spec.contains("title", title);
        Specification<Events> contentsSpec = spec.contains("contents", contents);
        Specification<Events> startDateSpec = startDateIsAfter ? spec.dateAfter("startDate", startDate) : spec.dateBefore("startDate", startDate);
        Specification<Events> endDateSpec = endDateIsAfter ? spec.dateAfter("endDate", endDate) : spec.dateBefore("endDate", endDate);
        Specification<Events> finalSpec = Specification.where(titleSpec).and(contentsSpec)
                .and(startDateSpec).and(endDateSpec);
        return ResponseEntity.ok(eventsService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    public ResponseEntity<Page<EventsDTO>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "eventId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(eventsService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventsDTO> getEvents(@PathVariable(name = "eventId") final Long eventId) {
        return ResponseEntity.ok(eventsService.get(eventId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEvents(@RequestBody @Valid final EventsDTO eventsDTO) {
        final Long createdEventId = eventsService.create(eventsDTO);
        return new ResponseEntity<>(createdEventId, HttpStatus.CREATED);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Long> updateEvents(@PathVariable(name = "eventId") final Long eventId,
            @RequestBody @Valid final EventsDTO eventsDTO) {
        eventsService.update(eventId, eventsDTO);
        return ResponseEntity.ok(eventId);
    }

    @DeleteMapping("/{eventId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEvents(@PathVariable(name = "eventId") final Long eventId) {
        final ReferencedWarning referencedWarning = eventsService.getReferencedWarning(eventId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        eventsService.delete(eventId);
        return ResponseEntity.noContent().build();
    }

}
