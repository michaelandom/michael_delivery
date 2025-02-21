package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.EventsDTO;
import com.michael_delivery.backend.service.EventsService;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventsResource {

    private final EventsService eventsService;

    public EventsResource(final EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping
    public ResponseEntity<List<EventsDTO>> getAllEvents() {
        return ResponseEntity.ok(eventsService.findAll());
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
