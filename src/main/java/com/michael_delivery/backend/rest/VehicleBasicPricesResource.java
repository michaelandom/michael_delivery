package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.enums.VehicleType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.VehicleBasicPrices;
import com.michael_delivery.backend.model.VehicleBasicPricesDTO;
import com.michael_delivery.backend.repos.VehicleBasicPricesRepository;
import com.michael_delivery.backend.service.VehicleBasicPricesService;
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
@RequestMapping(value = "/api/vehicleBasicPrices", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleBasicPricesResource {

    private final VehicleBasicPricesService vehicleBasicPricesService;
    private final VehicleBasicPricesRepository vehicleBasicPricesRepository;

    public VehicleBasicPricesResource(final VehicleBasicPricesService vehicleBasicPricesService,
            final VehicleBasicPricesRepository vehicleBasicPricesRepository) {
        this.vehicleBasicPricesService = vehicleBasicPricesService;
        this.vehicleBasicPricesRepository = vehicleBasicPricesRepository;
    }

    @GetMapping
    public ResponseEntity<List<VehicleBasicPricesDTO>> getAllVehicleBasicPrices() {
        return ResponseEntity.ok(vehicleBasicPricesService.findAll());
    }

    @GetMapping("/{vehicleBasicPriceId}")
    public ResponseEntity<VehicleBasicPricesDTO> getVehicleBasicPrices(
            @PathVariable(name = "vehicleBasicPriceId") final Long vehicleBasicPriceId) {
        return ResponseEntity.ok(vehicleBasicPricesService.get(vehicleBasicPriceId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createVehicleBasicPrices(
            @RequestBody @Valid final VehicleBasicPricesDTO vehicleBasicPricesDTO) {
        final Long createdVehicleBasicPriceId = vehicleBasicPricesService.create(vehicleBasicPricesDTO);
        return new ResponseEntity<>(createdVehicleBasicPriceId, HttpStatus.CREATED);
    }

    @PutMapping("/{vehicleBasicPriceId}")
    public ResponseEntity<Long> updateVehicleBasicPrices(
            @PathVariable(name = "vehicleBasicPriceId") final Long vehicleBasicPriceId,
            @RequestBody @Valid final VehicleBasicPricesDTO vehicleBasicPricesDTO) {
        vehicleBasicPricesService.update(vehicleBasicPriceId, vehicleBasicPricesDTO);
        return ResponseEntity.ok(vehicleBasicPriceId);
    }

    @DeleteMapping("/{vehicleBasicPriceId}")
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
    public ResponseEntity<Map<Long, VehicleType>> getPreviousValues() {
        return ResponseEntity.ok(vehicleBasicPricesRepository.findAll(Sort.by("vehicleBasicPriceId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(VehicleBasicPrices::getVehicleBasicPriceId, VehicleBasicPrices::getVehicleType)));
    }

}
