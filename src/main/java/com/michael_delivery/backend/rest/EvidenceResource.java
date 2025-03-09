package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Evidence;
import com.michael_delivery.backend.model.EvidenceDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.model.EvidenceDTO;
import com.michael_delivery.backend.repos.DestinationRepository;
import com.michael_delivery.backend.service.EvidenceService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/evidences", produces = MediaType.APPLICATION_JSON_VALUE)
public class EvidenceResource {

    private final EvidenceService evidenceService;
    private final DestinationRepository destinationRepository;

    public EvidenceResource(final EvidenceService evidenceService,
            final DestinationRepository destinationRepository) {
        this.evidenceService = evidenceService;
        this.destinationRepository = destinationRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EvidenceDTO>> getAllEvidence(
    ) {
        return ResponseEntity.ok(evidenceService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EvidenceDTO>> searchEvidence(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "evidenceId:asc") String[] sortBy,
            @RequestParam(required = false) String recipientName,
            @RequestParam(required = false) Date recipientDob
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Evidence> spec = new GenericSpecification<>();
        Specification<Evidence> recipientNameSpec = spec.contains("recipientName", recipientName);
        Specification<Evidence> recipientDobSpec = spec.equals("recipientDob", recipientDob);
        Specification<Evidence> finalSpec = Specification.where(recipientNameSpec).and(recipientDobSpec);
        return ResponseEntity.ok(evidenceService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    public ResponseEntity<Page<EvidenceDTO>> getAllEvidence(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "evidenceId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(evidenceService.findAll(pageable.getPageable()));
    }
    @GetMapping("/{evidenceId}")
    public ResponseEntity<EvidenceDTO> getEvidence(
            @PathVariable(name = "evidenceId") final Long evidenceId) {
        return ResponseEntity.ok(evidenceService.get(evidenceId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEvidence(@RequestBody @Valid final EvidenceDTO evidenceDTO) {
        final Long createdEvidenceId = evidenceService.create(evidenceDTO);
        return new ResponseEntity<>(createdEvidenceId, HttpStatus.CREATED);
    }

    @PutMapping("/{evidenceId}")
    public ResponseEntity<Long> updateEvidence(
            @PathVariable(name = "evidenceId") final Long evidenceId,
            @RequestBody @Valid final EvidenceDTO evidenceDTO) {
        evidenceService.update(evidenceId, evidenceDTO);
        return ResponseEntity.ok(evidenceId);
    }

    @DeleteMapping("/{evidenceId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEvidence(
            @PathVariable(name = "evidenceId") final Long evidenceId) {
        evidenceService.delete(evidenceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/destinationValues")
    public ResponseEntity<Map<Long, Long>> getDestinationValues() {
        return ResponseEntity.ok(destinationRepository.findAll(Sort.by("destinationId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Destination::getDestinationId, Destination::getDestinationId)));
    }

}
