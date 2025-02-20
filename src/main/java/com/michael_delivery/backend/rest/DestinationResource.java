package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.repos.OrdersRepository;
import com.michael_delivery.backend.repos.RidersRepository;
import com.michael_delivery.backend.service.DestinationService;
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
@RequestMapping(value = "/api/destinations", produces = MediaType.APPLICATION_JSON_VALUE)
public class DestinationResource {

    private final DestinationService destinationService;
    private final OrdersRepository ordersRepository;
    private final RidersRepository ridersRepository;

    public DestinationResource(final DestinationService destinationService,
            final OrdersRepository ordersRepository, final RidersRepository ridersRepository) {
        this.destinationService = destinationService;
        this.ordersRepository = ordersRepository;
        this.ridersRepository = ridersRepository;
    }

    @GetMapping
    public ResponseEntity<List<DestinationDTO>> getAllDestinations() {
        return ResponseEntity.ok(destinationService.findAll());
    }

    @GetMapping("/{destinationId}")
    public ResponseEntity<DestinationDTO> getDestination(
            @PathVariable(name = "destinationId") final Long destinationId) {
        return ResponseEntity.ok(destinationService.get(destinationId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDestination(
            @RequestBody @Valid final DestinationDTO destinationDTO) {
        final Long createdDestinationId = destinationService.create(destinationDTO);
        return new ResponseEntity<>(createdDestinationId, HttpStatus.CREATED);
    }

    @PutMapping("/{destinationId}")
    public ResponseEntity<Long> updateDestination(
            @PathVariable(name = "destinationId") final Long destinationId,
            @RequestBody @Valid final DestinationDTO destinationDTO) {
        destinationService.update(destinationId, destinationDTO);
        return ResponseEntity.ok(destinationId);
    }

    @DeleteMapping("/{destinationId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDestination(
            @PathVariable(name = "destinationId") final Long destinationId) {
        final ReferencedWarning referencedWarning = destinationService.getReferencedWarning(destinationId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        destinationService.delete(destinationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orderValues")
    public ResponseEntity<Map<Long, String>> getOrderValues() {
        return ResponseEntity.ok(ordersRepository.findAll(Sort.by("orderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Orders::getOrderId, Orders::getOrderNumber)));
    }

    @GetMapping("/deliveryByValues")
    public ResponseEntity<Map<Long, String>> getDeliveryByValues() {
        return ResponseEntity.ok(ridersRepository.findAll(Sort.by("riderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Riders::getRiderId, Riders::getEmergencyContactFirstName)));
    }

}
