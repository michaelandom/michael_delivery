package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.Evidence;
import com.michael_delivery.backend.dto.EvidenceDTO;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Destination;
import com.michael_delivery.backend.repository.DestinationRepository;
import com.michael_delivery.backend.service.EvidenceService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/evidences", produces = MediaType.APPLICATION_JSON_VALUE)
public class EvidenceController {

    private final EvidenceService evidenceService;
    private final DestinationRepository destinationRepository;

    public EvidenceController(final EvidenceService evidenceService,
            final DestinationRepository destinationRepository) {
        this.evidenceService = evidenceService;
        this.destinationRepository = destinationRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_EVIDENCES','VIEW_EVIDENCE_MANY')")
    public ResponseEntity<List<EvidenceDTO>> getAllEvidence(
    ) {
        return ResponseEntity.ok(evidenceService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_EVIDENCES','VIEW_EVIDENCE_MANY')")
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
    @PreAuthorize("hasAnyAuthority('MANAGE_EVIDENCES','VIEW_EVIDENCE_MANY')")
    public ResponseEntity<Page<EvidenceDTO>> getAllEvidence(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "evidenceId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(evidenceService.findAll(pageable.getPageable()));
    }
    @GetMapping("/{evidenceId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_EVIDENCES','VIEW_EVIDENCE')")
    public ResponseEntity<EvidenceDTO> getEvidence(
            @PathVariable(name = "evidenceId") final Long evidenceId) {
        return ResponseEntity.ok(evidenceService.get(evidenceId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_EVIDENCES','UPDATE_EVIDENCE_ONE')")
    public ResponseEntity<Long> createEvidence(@RequestBody @Valid final EvidenceDTO evidenceDTO) {
        final Long createdEvidenceId = evidenceService.create(evidenceDTO);
        return new ResponseEntity<>(createdEvidenceId, HttpStatus.CREATED);
    }

    @PutMapping("/{evidenceId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_EVIDENCES','UPDATE_EVIDENCE_ONE')")
    public ResponseEntity<Long> updateEvidence(
            @PathVariable(name = "evidenceId") final Long evidenceId,
            @RequestBody @Valid final EvidenceDTO evidenceDTO) {
        evidenceService.update(evidenceId, evidenceDTO);
        return ResponseEntity.ok(evidenceId);
    }

    @DeleteMapping("/{evidenceId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_EVIDENCES','DELETE_EVIDENCE_ONE')")
    public ResponseEntity<Void> deleteEvidence(
            @PathVariable(name = "evidenceId") final Long evidenceId) {
        evidenceService.delete(evidenceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/destinationValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_EVIDENCES','VIEW_EVIDENCE_MANY')")
    public ResponseEntity<Map<Long, Long>> getDestinationValues() {
        return ResponseEntity.ok(destinationRepository.findAll(Sort.by("destinationId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Destination::getDestinationId, Destination::getDestinationId)));
    }

}
