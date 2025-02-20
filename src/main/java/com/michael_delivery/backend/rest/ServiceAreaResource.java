package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.State;
import com.michael_delivery.backend.model.ServiceAreaDTO;
import com.michael_delivery.backend.repos.StateRepository;
import com.michael_delivery.backend.service.ServiceAreaService;
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
@RequestMapping(value = "/api/serviceAreas", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServiceAreaResource {

    private final ServiceAreaService serviceAreaService;
    private final StateRepository stateRepository;

    public ServiceAreaResource(final ServiceAreaService serviceAreaService,
            final StateRepository stateRepository) {
        this.serviceAreaService = serviceAreaService;
        this.stateRepository = stateRepository;
    }

    @GetMapping
    public ResponseEntity<List<ServiceAreaDTO>> getAllServiceAreas() {
        return ResponseEntity.ok(serviceAreaService.findAll());
    }

    @GetMapping("/{serviceAreaId}")
    public ResponseEntity<ServiceAreaDTO> getServiceArea(
            @PathVariable(name = "serviceAreaId") final Long serviceAreaId) {
        return ResponseEntity.ok(serviceAreaService.get(serviceAreaId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createServiceArea(
            @RequestBody @Valid final ServiceAreaDTO serviceAreaDTO) {
        final Long createdServiceAreaId = serviceAreaService.create(serviceAreaDTO);
        return new ResponseEntity<>(createdServiceAreaId, HttpStatus.CREATED);
    }

    @PutMapping("/{serviceAreaId}")
    public ResponseEntity<Long> updateServiceArea(
            @PathVariable(name = "serviceAreaId") final Long serviceAreaId,
            @RequestBody @Valid final ServiceAreaDTO serviceAreaDTO) {
        serviceAreaService.update(serviceAreaId, serviceAreaDTO);
        return ResponseEntity.ok(serviceAreaId);
    }

    @DeleteMapping("/{serviceAreaId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteServiceArea(
            @PathVariable(name = "serviceAreaId") final Long serviceAreaId) {
        serviceAreaService.delete(serviceAreaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stateNameValues")
    public ResponseEntity<Map<Long, String>> getStateNameValues() {
        return ResponseEntity.ok(stateRepository.findAll(Sort.by("stateId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(State::getStateId, State::getName)));
    }

}
