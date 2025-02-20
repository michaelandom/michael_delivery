package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.enums.VehicleType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.TransportBasicPrices;
import com.michael_delivery.backend.model.TransportBasicPricesDTO;
import com.michael_delivery.backend.repos.TransportBasicPricesRepository;
import com.michael_delivery.backend.service.TransportBasicPricesService;
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
@RequestMapping(value = "/api/transportBasicPrices", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransportBasicPricesResource {

    private final TransportBasicPricesService transportBasicPricesService;
    private final TransportBasicPricesRepository transportBasicPricesRepository;

    public TransportBasicPricesResource(
            final TransportBasicPricesService transportBasicPricesService,
            final TransportBasicPricesRepository transportBasicPricesRepository) {
        this.transportBasicPricesService = transportBasicPricesService;
        this.transportBasicPricesRepository = transportBasicPricesRepository;
    }

    @GetMapping
    public ResponseEntity<List<TransportBasicPricesDTO>> getAllTransportBasicPricess() {
        return ResponseEntity.ok(transportBasicPricesService.findAll());
    }

    @GetMapping("/{transportBasicPriceId}")
    public ResponseEntity<TransportBasicPricesDTO> getTransportBasicPrices(
            @PathVariable(name = "transportBasicPriceId") final Long transportBasicPriceId) {
        return ResponseEntity.ok(transportBasicPricesService.get(transportBasicPriceId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTransportBasicPrices(
            @RequestBody @Valid final TransportBasicPricesDTO transportBasicPricesDTO) {
        final Long createdTransportBasicPriceId = transportBasicPricesService.create(transportBasicPricesDTO);
        return new ResponseEntity<>(createdTransportBasicPriceId, HttpStatus.CREATED);
    }

    @PutMapping("/{transportBasicPriceId}")
    public ResponseEntity<Long> updateTransportBasicPrices(
            @PathVariable(name = "transportBasicPriceId") final Long transportBasicPriceId,
            @RequestBody @Valid final TransportBasicPricesDTO transportBasicPricesDTO) {
        transportBasicPricesService.update(transportBasicPriceId, transportBasicPricesDTO);
        return ResponseEntity.ok(transportBasicPriceId);
    }

    @DeleteMapping("/{transportBasicPriceId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTransportBasicPrices(
            @PathVariable(name = "transportBasicPriceId") final Long transportBasicPriceId) {
        final ReferencedWarning referencedWarning = transportBasicPricesService.getReferencedWarning(transportBasicPriceId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        transportBasicPricesService.delete(transportBasicPriceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/previousValues")
    public ResponseEntity<Map<Long, VehicleType>> getPreviousValues() {
        return ResponseEntity.ok(transportBasicPricesRepository.findAll(Sort.by("transportBasicPriceId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(TransportBasicPrices::getTransportBasicPriceId, TransportBasicPrices::getVehicleType)));
    }

}
