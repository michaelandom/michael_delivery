package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.PeakTimeRate;
import com.michael_delivery.backend.dto.PeakTimeRateDTO;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.repository.PeakTimeRateRepository;
import com.michael_delivery.backend.service.PeakTimeRateService;
import com.michael_delivery.backend.util.CustomCollectors;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/peakTimeRates", produces = MediaType.APPLICATION_JSON_VALUE)
public class PeakTimeRateController {

    private final PeakTimeRateService peakTimeRateService;
    private final PeakTimeRateRepository peakTimeRateRepository;

    public PeakTimeRateController(final PeakTimeRateService peakTimeRateService,
            final PeakTimeRateRepository peakTimeRateRepository) {
        this.peakTimeRateService = peakTimeRateService;
        this.peakTimeRateRepository = peakTimeRateRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_PEAK_TIME_RATES','VIEW_PEAK_TIME_RATE_MANY')")
    public ResponseEntity<List<PeakTimeRateDTO>> getAllPeakTimeRate(
    ) {
        return ResponseEntity.ok(peakTimeRateService.findAll());
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_PEAK_TIME_RATES','VIEW_PEAK_TIME_RATE_MANY')")
    public ResponseEntity<Page<PeakTimeRateDTO>> getAllPeakTimeRate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "peakTimeRateId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(peakTimeRateService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{peakTimeRateId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_PEAK_TIME_RATES','VIEW_PEAK_TIME_RATE')")
    public ResponseEntity<PeakTimeRateDTO> getPeakTimeRate(
            @PathVariable(name = "peakTimeRateId") final Long peakTimeRateId) {
        return ResponseEntity.ok(peakTimeRateService.get(peakTimeRateId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_PEAK_TIME_RATES','UPDATE_PEAK_TIME_RATE_ONE')")
    public ResponseEntity<Long> createPeakTimeRate(
            @RequestBody @Valid final PeakTimeRateDTO peakTimeRateDTO) {
        final Long createdPeakTimeRateId = peakTimeRateService.create(peakTimeRateDTO);
        return new ResponseEntity<>(createdPeakTimeRateId, HttpStatus.CREATED);
    }

    @PutMapping("/{peakTimeRateId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_PEAK_TIME_RATES','UPDATE_PEAK_TIME_RATE_ONE')")
    public ResponseEntity<Long> updatePeakTimeRate(
            @PathVariable(name = "peakTimeRateId") final Long peakTimeRateId,
            @RequestBody @Valid final PeakTimeRateDTO peakTimeRateDTO) {
        peakTimeRateService.update(peakTimeRateId, peakTimeRateDTO);
        return ResponseEntity.ok(peakTimeRateId);
    }

    @DeleteMapping("/{peakTimeRateId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_PEAK_TIME_RATES','DELETE_PEAK_TIME_RATE_ONE')")
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
    @PreAuthorize("hasAnyAuthority('MANAGE_PEAK_TIME_RATES','VIEW_PEAK_TIME_RATE_MANY')")
    public ResponseEntity<Map<Long, OffsetDateTime>> getPreviousValues() {
        return ResponseEntity.ok(peakTimeRateRepository.findAll(Sort.by("peakTimeRateId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(PeakTimeRate::getPeakTimeRateId, PeakTimeRate::getStartTime)));
    }

}
