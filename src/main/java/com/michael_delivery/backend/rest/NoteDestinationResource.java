package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.NoteDestination;
import com.michael_delivery.backend.model.NoteDestinationDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.model.NoteDestinationDTO;
import com.michael_delivery.backend.repos.DestinationRepository;
import com.michael_delivery.backend.service.NoteDestinationService;
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
@RequestMapping(value = "/api/noteDestinations", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoteDestinationResource {

    private final NoteDestinationService noteDestinationService;
    private final DestinationRepository destinationRepository;

    public NoteDestinationResource(final NoteDestinationService noteDestinationService,
            final DestinationRepository destinationRepository) {
        this.noteDestinationService = noteDestinationService;
        this.destinationRepository = destinationRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<NoteDestinationDTO>> getAllNoteDestination(
    ) {
        return ResponseEntity.ok(noteDestinationService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NoteDestinationDTO>> searchNoteDestination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "noteDestinationId:asc") String[] sortBy,
            @RequestParam(required = false) String note
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<NoteDestination> spec = new GenericSpecification<>();
        Specification<NoteDestination> noteSpec = spec.contains("note", note);
        Specification<NoteDestination> finalSpec = Specification.where(noteSpec);
        return ResponseEntity.ok(noteDestinationService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    public ResponseEntity<Page<NoteDestinationDTO>> getAllNoteDestination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "noteDestinationId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(noteDestinationService.findAll(pageable.getPageable()));
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
