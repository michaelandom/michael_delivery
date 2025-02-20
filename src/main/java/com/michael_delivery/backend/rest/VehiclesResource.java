package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.model.VehiclesDTO;
import com.michael_delivery.backend.repos.RidersRepository;
import com.michael_delivery.backend.service.VehiclesService;
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
@RequestMapping(value = "/api/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehiclesResource {

    private final VehiclesService vehiclesService;
    private final RidersRepository ridersRepository;

    public VehiclesResource(final VehiclesService vehiclesService,
            final RidersRepository ridersRepository) {
        this.vehiclesService = vehiclesService;
        this.ridersRepository = ridersRepository;
    }

    @GetMapping
    public ResponseEntity<List<VehiclesDTO>> getAllVehicles() {
        return ResponseEntity.ok(vehiclesService.findAll());
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehiclesDTO> getVehicles(
            @PathVariable(name = "vehicleId") final Long vehicleId) {
        return ResponseEntity.ok(vehiclesService.get(vehicleId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createVehicles(@RequestBody @Valid final VehiclesDTO vehiclesDTO) {
        final Long createdVehicleId = vehiclesService.create(vehiclesDTO);
        return new ResponseEntity<>(createdVehicleId, HttpStatus.CREATED);
    }

    @PutMapping("/{vehicleId}")
    public ResponseEntity<Long> updateVehicles(
            @PathVariable(name = "vehicleId") final Long vehicleId,
            @RequestBody @Valid final VehiclesDTO vehiclesDTO) {
        vehiclesService.update(vehicleId, vehiclesDTO);
        return ResponseEntity.ok(vehicleId);
    }

    @DeleteMapping("/{vehicleId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteVehicles(
            @PathVariable(name = "vehicleId") final Long vehicleId) {
        vehiclesService.delete(vehicleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/riderValues")
    public ResponseEntity<Map<Long, String>> getRiderValues() {
        return ResponseEntity.ok(ridersRepository.findAll(Sort.by("riderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Riders::getRiderId, Riders::getEmergencyContactFirstName)));
    }

}
