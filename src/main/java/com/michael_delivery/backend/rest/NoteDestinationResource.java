package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.model.NoteDestinationDTO;
import com.michael_delivery.backend.repos.DestinationRepository;
import com.michael_delivery.backend.service.NoteDestinationService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/noteDestinations", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoteDestinationResource {

    private final NoteDestinationService noteDestinationService;
    private final DestinationRepository destinationRepository;

    public NoteDestinationResource(final NoteDestinationService noteDestinationService,
            final DestinationRepository destinationRepository) {
        this.noteDestinationService = noteDestinationService;
        this.destinationRepository = destinationRepository;
    }

    @GetMapping
    public ResponseEntity<List<NoteDestinationDTO>> getAllNoteDestinations() {
        return ResponseEntity.ok(noteDestinationService.findAll());
    }

    @GetMapping("/{noteDestinationId}")
    public ResponseEntity<NoteDestinationDTO> getNoteDestination(
            @PathVariable(name = "noteDestinationId") final Long noteDestinationId) {
        return ResponseEntity.ok(noteDestinationService.get(noteDestinationId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createNoteDestination(
            @RequestBody @Valid final NoteDestinationDTO noteDestinationDTO) {
        final Long createdNoteDestinationId = noteDestinationService.create(noteDestinationDTO);
        return new ResponseEntity<>(createdNoteDestinationId, HttpStatus.CREATED);
    }

    @PutMapping("/{noteDestinationId}")
    public ResponseEntity<Long> updateNoteDestination(
            @PathVariable(name = "noteDestinationId") final Long noteDestinationId,
            @RequestBody @Valid final NoteDestinationDTO noteDestinationDTO) {
        noteDestinationService.update(noteDestinationId, noteDestinationDTO);
        return ResponseEntity.ok(noteDestinationId);
    }

    @DeleteMapping("/{noteDestinationId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteNoteDestination(
            @PathVariable(name = "noteDestinationId") final Long noteDestinationId) {
        noteDestinationService.delete(noteDestinationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/destinationValues")
    public ResponseEntity<Map<Long, Long>> getDestinationValues() {
        return ResponseEntity.ok(destinationRepository.findAll(Sort.by("destinationId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Destination::getDestinationId, Destination::getDestinationId)));
    }

}
