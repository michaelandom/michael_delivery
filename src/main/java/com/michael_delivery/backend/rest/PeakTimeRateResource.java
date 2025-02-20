package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.PeakTimeRate;
import com.michael_delivery.backend.model.PeakTimeRateDTO;
import com.michael_delivery.backend.repos.PeakTimeRateRepository;
import com.michael_delivery.backend.service.PeakTimeRateService;
import com.michael_delivery.backend.util.CustomCollectors;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/peakTimeRates", produces = MediaType.APPLICATION_JSON_VALUE)
public class PeakTimeRateResource {

    private final PeakTimeRateService peakTimeRateService;
    private final PeakTimeRateRepository peakTimeRateRepository;

    public PeakTimeRateResource(final PeakTimeRateService peakTimeRateService,
            final PeakTimeRateRepository peakTimeRateRepository) {
        this.peakTimeRateService = peakTimeRateService;
        this.peakTimeRateRepository = peakTimeRateRepository;
    }

    @GetMapping
    public ResponseEntity<List<PeakTimeRateDTO>> getAllPeakTimeRates() {
        return ResponseEntity.ok(peakTimeRateService.findAll());
    }

    @GetMapping("/{peakTimeRateId}")
    public ResponseEntity<PeakTimeRateDTO> getPeakTimeRate(
            @PathVariable(name = "peakTimeRateId") final Long peakTimeRateId) {
        return ResponseEntity.ok(peakTimeRateService.get(peakTimeRateId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPeakTimeRate(
            @RequestBody @Valid final PeakTimeRateDTO peakTimeRateDTO) {
        final Long createdPeakTimeRateId = peakTimeRateService.create(peakTimeRateDTO);
        return new ResponseEntity<>(createdPeakTimeRateId, HttpStatus.CREATED);
    }

    @PutMapping("/{peakTimeRateId}")
    public ResponseEntity<Long> updatePeakTimeRate(
            @PathVariable(name = "peakTimeRateId") final Long peakTimeRateId,
            @RequestBody @Valid final PeakTimeRateDTO peakTimeRateDTO) {
        peakTimeRateService.update(peakTimeRateId, peakTimeRateDTO);
        return ResponseEntity.ok(peakTimeRateId);
    }

    @DeleteMapping("/{peakTimeRateId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePeakTimeRate(
            @PathVariable(name = "peakTimeRateId") final Long peakTimeRateId) {
        final ReferencedWarning referencedWarning = peakTimeRateService.getReferencedWarning(peakTimeRateId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        peakTimeRateService.delete(peakTimeRateId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/previousValues")
    public ResponseEntity<Map<Long, String>> getPreviousValues() {
        return ResponseEntity.ok(peakTimeRateRepository.findAll(Sort.by("peakTimeRateId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(PeakTimeRate::getPeakTimeRateId, PeakTimeRate::getStartTime)));
    }

}
