package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.Vehicles;
import com.michael_delivery.backend.enums.VehicleType;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.dto.VehiclesDTO;
import com.michael_delivery.backend.repository.RidersRepository;
import com.michael_delivery.backend.service.VehiclesService;
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

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehiclesController {

    private final VehiclesService vehiclesService;
    private final RidersRepository ridersRepository;

    public VehiclesController(final VehiclesService vehiclesService,
            final RidersRepository ridersRepository) {
        this.vehiclesService = vehiclesService;
        this.ridersRepository = ridersRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLES','VIEW_VEHICLE_MANY')")
    public ResponseEntity<List<VehiclesDTO>> getAllVehicles(
    ) {
        return ResponseEntity.ok(vehiclesService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLES','VIEW_VEHICLE_MANY')")
    public ResponseEntity<Page<VehiclesDTO>> searchVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "vehicleId:asc") String[] sortBy,
            @RequestParam(required = false) VehicleType vehicleType,
            @RequestParam(required = false) String modelYear,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) String insurancePolicy,
            @RequestParam(required = false) OffsetDateTime driverLicenseValidFrom,
            @RequestParam(required = false) OffsetDateTime driverLicenseValidTo,
            @RequestParam(required = false) OffsetDateTime insurancePolicyValidFrom,
            @RequestParam(required = false) OffsetDateTime insurancePolicyValidTo,
            @RequestParam(required = false) OffsetDateTime expiryDate,
            @RequestParam(required = false) boolean isCurrentVehicle,
            @RequestParam(required = false) boolean driverLicenseValidFromIsAfter,
            @RequestParam(required = false) boolean driverLicenseValidToIsAfter,
            @RequestParam(required = false) boolean insurancePolicyValidFromIsAfter,
            @RequestParam(required = false) boolean insurancePolicyValidToIsAfter,
            @RequestParam(required = false) boolean expiryDateIsAfter

    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Vehicles> spec = new GenericSpecification<>();
        Specification<Vehicles> vehicleTypeSpec = spec.equals("vehicleType", vehicleType);
        Specification<Vehicles> modelYearSpec = spec.contains("modelYear", modelYear);
        Specification<Vehicles> manufacturerSpec = spec.contains("manufacturer", manufacturer);
        Specification<Vehicles> insurancePolicySpec = spec.contains("insurancePolicy", insurancePolicy);
        Specification<Vehicles> driverLicenseValidFromSpec = driverLicenseValidFromIsAfter ? spec.dateAfter("driverLicenseValidFrom", driverLicenseValidFrom) : spec.dateBefore("driverLicenseValidFrom", driverLicenseValidFrom);
        Specification<Vehicles> driverLicenseValidToIsAfterSpec = driverLicenseValidToIsAfter ? spec.dateAfter("driverLicenseValidTo", driverLicenseValidTo) : spec.dateBefore("driverLicenseValidTo", driverLicenseValidTo);
        Specification<Vehicles> insurancePolicyValidFromSpec = insurancePolicyValidFromIsAfter ? spec.dateAfter("insurancePolicyValidFrom", insurancePolicyValidFrom) : spec.dateBefore("insurancePolicyValidFrom", insurancePolicyValidFrom);
        Specification<Vehicles> insurancePolicyValidToSpec = insurancePolicyValidToIsAfter ? spec.dateAfter("insurancePolicyValidTo", insurancePolicyValidTo) : spec.dateBefore("insurancePolicyValidTo", insurancePolicyValidTo);
        Specification<Vehicles> expiryDateSpec = expiryDateIsAfter ? spec.dateAfter("expiryDate", expiryDate) : spec.dateBefore("expiryDate", expiryDate);
        Specification<Vehicles> isCurrentVehicleSpec = spec.equals("isCurrentVehicle", isCurrentVehicle);
        Specification<Vehicles> finalSpec = Specification.where(vehicleTypeSpec).and(modelYearSpec)
                .and(manufacturerSpec)
                .and(insurancePolicySpec)
                .and(driverLicenseValidFromSpec)
                .and(driverLicenseValidToIsAfterSpec)
                .and(insurancePolicyValidFromSpec)
                .and(insurancePolicyValidToSpec)
                .and(expiryDateSpec)
                .and(isCurrentVehicleSpec);
        return ResponseEntity.ok(vehiclesService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLES','VIEW_VEHICLE_MANY')")
     public ResponseEntity<Page<VehiclesDTO>> getAllVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "vehicleId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(vehiclesService.findAll(pageable.getPageable()));
    }
    @GetMapping("/{vehicleId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLES','VIEW_VEHICLE_ONE')")
    public ResponseEntity<VehiclesDTO> getVehicles(
            @PathVariable(name = "vehicleId") final Long vehicleId) {
        return ResponseEntity.ok(vehiclesService.get(vehicleId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLES','UPDATE_VEHICLE_ONE')")
    public ResponseEntity<Long> createVehicles(@RequestBody @Valid final VehiclesDTO vehiclesDTO) {
        final Long createdVehicleId = vehiclesService.create(vehiclesDTO);
        return new ResponseEntity<>(createdVehicleId, HttpStatus.CREATED);
    }

    @PutMapping("/{vehicleId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLES','UPDATE_VEHICLE_ONE')")
    public ResponseEntity<Long> updateVehicles(
            @PathVariable(name = "vehicleId") final Long vehicleId,
            @RequestBody @Valid final VehiclesDTO vehiclesDTO) {
        vehiclesService.update(vehicleId, vehiclesDTO);
        return ResponseEntity.ok(vehicleId);
    }

    @DeleteMapping("/{vehicleId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLES','DELETE_VEHICLE_ONE')")
    public ResponseEntity<Void> deleteVehicles(
            @PathVariable(name = "vehicleId") final Long vehicleId) {
        vehiclesService.delete(vehicleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/riderValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLES','VIEW_VEHICLE_ONE')")
    public ResponseEntity<Map<Long, String>> getRiderValues() {
        return ResponseEntity.ok(ridersRepository.findAll(Sort.by("riderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Riders::getRiderId, Riders::getEmergencyContactFirstName)));
    }

}
