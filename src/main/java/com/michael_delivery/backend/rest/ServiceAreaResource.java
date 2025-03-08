package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.ServiceArea;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.model.ServiceAreaDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.State;
import com.michael_delivery.backend.repos.StateRepository;
import com.michael_delivery.backend.service.ServiceAreaService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
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

    @GetMapping("/all")
    public ResponseEntity<List<ServiceAreaDTO>> getAllServiceArea(
    ) {
        return ResponseEntity.ok(serviceAreaService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ServiceAreaDTO>> searchServiceArea(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "serviceAreaId:asc") String[] sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) boolean isActive
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<ServiceArea> spec = new GenericSpecification<>();
        Specification<ServiceArea> nameSpec = spec.contains("name", name);
        Specification<ServiceArea> codeSpec = spec.contains("code", code);
        Specification<ServiceArea> isActiveSpec = spec.equals("isActive", isActive);
        Specification<ServiceArea> finalSpec = Specification.where(nameSpec)
                .and(codeSpec)
                .and(isActiveSpec);
        return ResponseEntity.ok(serviceAreaService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    public ResponseEntity<Page<ServiceAreaDTO>> getAllServiceArea(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "serviceAreaId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(serviceAreaService.findAll(pageable.getPageable()));
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
