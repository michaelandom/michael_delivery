package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.model.EvidenceDTO;
import com.michael_delivery.backend.repos.DestinationRepository;
import com.michael_delivery.backend.service.EvidenceService;
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
@RequestMapping(value = "/api/evidences", produces = MediaType.APPLICATION_JSON_VALUE)
public class EvidenceResource {

    private final EvidenceService evidenceService;
    private final DestinationRepository destinationRepository;

    public EvidenceResource(final EvidenceService evidenceService,
            final DestinationRepository destinationRepository) {
        this.evidenceService = evidenceService;
        this.destinationRepository = destinationRepository;
    }

    @GetMapping
    public ResponseEntity<List<EvidenceDTO>> getAllEvidences() {
        return ResponseEntity.ok(evidenceService.findAll());
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
