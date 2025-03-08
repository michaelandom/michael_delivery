package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.enums.PickupTimeType;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @GetMapping("/all")
    public ResponseEntity<List<DestinationDTO>> getAllDestination(
    ) {
        return ResponseEntity.ok(destinationService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DestinationDTO>> searchDestination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "destinationId:asc") String[] sortBy,
            @RequestParam(required = false) Double destinationLatitude,
            @RequestParam(required = false) Double destinationLongitude,
            @RequestParam(required = false) String destinationAddressText,
            @RequestParam(required = false) int sequence,
            @RequestParam(required = false) String recipientPhoneNumber,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) String recipientName,
            @RequestParam(required = false) String safeStorage,
            @RequestParam(required = false) String specificRecipient
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Destination> spec = new GenericSpecification<>();
        Specification<Destination> destinationLatitudeSpec = spec.equals("destinationLatitude", destinationLatitude);
        Specification<Destination> destinationLongitudeSpec = spec.equals("destinationLongitude", destinationLongitude);
        Specification<Destination> destinationAddressTextSpec = spec.contains("destinationAddressText", destinationAddressText);
        Specification<Destination> recipientPhoneNumberSpec = spec.contains("recipientPhoneNumber", recipientPhoneNumber);
        Specification<Destination> recipientNameSpec = spec.contains("recipientName", recipientName);
        Specification<Destination> sequenceSpec = spec.equals("sequence", sequence);
        Specification<Destination> priceSpec = spec.equals("price", price);
        Specification<Destination> safeStorageSpec = spec.equals("safeStorage", safeStorage);
        Specification<Destination> specificRecipientSpec = spec.equals("specificRecipient", specificRecipient);
        Specification<Destination> finalSpec = Specification.where(destinationLatitudeSpec).and(destinationLongitudeSpec)
                .and(destinationAddressTextSpec)
                .and(recipientPhoneNumberSpec)
                .and(recipientNameSpec)
                .and(sequenceSpec)
                .and(priceSpec)
                .and(safeStorageSpec)
                .and(specificRecipientSpec);
        return ResponseEntity.ok(destinationService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    public ResponseEntity<Page<DestinationDTO>> getAllDestination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "destinationId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(destinationService.findAll(pageable.getPageable()));
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
