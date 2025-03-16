package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.VehicleBasicPrices;
import com.michael_delivery.backend.enums.VehicleType;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.dto.VehicleBasicPricesDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.repository.VehicleBasicPricesRepository;
import com.michael_delivery.backend.service.VehicleBasicPricesService;
import com.michael_delivery.backend.util.CustomCollectors;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/vehicleBasicPrices", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleBasicPricesController {

    private final VehicleBasicPricesService vehicleBasicPricesService;
    private final VehicleBasicPricesRepository vehicleBasicPricesRepository;

    public VehicleBasicPricesController(final VehicleBasicPricesService vehicleBasicPricesService,
            final VehicleBasicPricesRepository vehicleBasicPricesRepository) {
        this.vehicleBasicPricesService = vehicleBasicPricesService;
        this.vehicleBasicPricesRepository = vehicleBasicPricesRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLE_BASIC_PRICES','VIEW_VEHICLE_BASIC_PRICE_MANY')")
    public ResponseEntity<List<VehicleBasicPricesDTO>> getAllVehicleBasicPrices(
    ) {
        return ResponseEntity.ok(vehicleBasicPricesService.findAll());
    }

    @GetMapping("/search")
@PreAuthorize("hasAnyAuthority('MANAGE_VEHICLE_BASIC_PRICES','VIEW_VEHICLE_BASIC_PRICE_MANY')")
    public ResponseEntity<Page<VehicleBasicPricesDTO>> searchVehicleBasicPrices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "vehicleBasicPriceId:asc") String[] sortBy,
            @RequestParam(required = false) VehicleType vehicleType,
            @RequestParam(required = false) boolean isLatest
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<VehicleBasicPrices> spec = new GenericSpecification<>();
        Specification<VehicleBasicPrices> vehicleTypeSpec = spec.equals("vehicleType", vehicleType);
        Specification<VehicleBasicPrices> isLatestSpec = spec.equals("isLatest", isLatest);
        Specification<VehicleBasicPrices> finalSpec = Specification.where(vehicleTypeSpec).and(isLatestSpec);
        return ResponseEntity.ok(vehicleBasicPricesService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLE_BASIC_PRICES','VIEW_VEHICLE_BASIC_PRICE_MANY')")
    public ResponseEntity<Page<VehicleBasicPricesDTO>> getAllVehicleBasicPrices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "vehicleBasicPriceId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(vehicleBasicPricesService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{vehicleBasicPriceId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLE_BASIC_PRICES','VIEW_VEHICLE_BASIC_PRICE_ONE')")
    public ResponseEntity<VehicleBasicPricesDTO> getVehicleBasicPrices(
            @PathVariable(name = "vehicleBasicPriceId") final Long vehicleBasicPriceId) {
        return ResponseEntity.ok(vehicleBasicPricesService.get(vehicleBasicPriceId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLE_BASIC_PRICES','UPDATE_VEHICLE_BASIC_PRICE_ONE')")
    public ResponseEntity<Long> createVehicleBasicPrices(
            @RequestBody @Valid final VehicleBasicPricesDTO vehicleBasicPricesDTO) {
        final Long createdVehicleBasicPriceId = vehicleBasicPricesService.create(vehicleBasicPricesDTO);
        return new ResponseEntity<>(createdVehicleBasicPriceId, HttpStatus.CREATED);
    }

    @PutMapping("/{vehicleBasicPriceId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLE_BASIC_PRICES','UPDATE_VEHICLE_BASIC_PRICE_ONE')")
    public ResponseEntity<Long> updateVehicleBasicPrices(
            @PathVariable(name = "vehicleBasicPriceId") final Long vehicleBasicPriceId,
            @RequestBody @Valid final VehicleBasicPricesDTO vehicleBasicPricesDTO) {
        vehicleBasicPricesService.update(vehicleBasicPriceId, vehicleBasicPricesDTO);
        return ResponseEntity.ok(vehicleBasicPriceId);
    }

    @DeleteMapping("/{vehicleBasicPriceId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLE_BASIC_PRICES','DELETE_VEHICLE_BASIC_PRICE_ONE')")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteVehicleBasicPrices(
            @PathVariable(name = "vehicleBasicPriceId") final Long vehicleBasicPriceId) {
        final ReferencedWarning referencedWarning = vehicleBasicPricesService.getReferencedWarning(vehicleBasicPriceId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        vehicleBasicPricesService.delete(vehicleBasicPriceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/previousValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLE_BASIC_PRICES','VIEW_VEHICLE_BASIC_PRICE_MANY')")
    public ResponseEntity<Map<Long, VehicleType>> getPreviousValues() {
        return ResponseEntity.ok(vehicleBasicPricesRepository.findAll(Sort.by("vehicleBasicPriceId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(VehicleBasicPrices::getVehicleBasicPriceId, VehicleBasicPrices::getVehicleType)));
    }

}
