package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import com.michael_delivery.backend.repos.OrdersRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.CancellationRiderRequestService;
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
@RequestMapping(value = "/api/cancellationRiderRequests", produces = MediaType.APPLICATION_JSON_VALUE)
public class CancellationRiderRequestResource {

    private final CancellationRiderRequestService cancellationRiderRequestService;
    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;

    public CancellationRiderRequestResource(
            final CancellationRiderRequestService cancellationRiderRequestService,
            final OrdersRepository ordersRepository, final UsersRepository usersRepository) {
        this.cancellationRiderRequestService = cancellationRiderRequestService;
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public ResponseEntity<List<CancellationRiderRequestDTO>> getAllCancellationRiderRequests() {
        return ResponseEntity.ok(cancellationRiderRequestService.findAll());
    }

    @GetMapping("/{cancellationRiderRequestId}")
    public ResponseEntity<CancellationRiderRequestDTO> getCancellationRiderRequest(
            @PathVariable(name = "cancellationRiderRequestId") final Long cancellationRiderRequestId) {
        return ResponseEntity.ok(cancellationRiderRequestService.get(cancellationRiderRequestId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCancellationRiderRequest(
            @RequestBody @Valid final CancellationRiderRequestDTO cancellationRiderRequestDTO) {
        final Long createdCancellationRiderRequestId = cancellationRiderRequestService.create(cancellationRiderRequestDTO);
        return new ResponseEntity<>(createdCancellationRiderRequestId, HttpStatus.CREATED);
    }

    @PutMapping("/{cancellationRiderRequestId}")
    public ResponseEntity<Long> updateCancellationRiderRequest(
            @PathVariable(name = "cancellationRiderRequestId") final Long cancellationRiderRequestId,
            @RequestBody @Valid final CancellationRiderRequestDTO cancellationRiderRequestDTO) {
        cancellationRiderRequestService.update(cancellationRiderRequestId, cancellationRiderRequestDTO);
        return ResponseEntity.ok(cancellationRiderRequestId);
    }

    @DeleteMapping("/{cancellationRiderRequestId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCancellationRiderRequest(
            @PathVariable(name = "cancellationRiderRequestId") final Long cancellationRiderRequestId) {
        cancellationRiderRequestService.delete(cancellationRiderRequestId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orderValues")
    public ResponseEntity<Map<Long, String>> getOrderValues() {
        return ResponseEntity.ok(ordersRepository.findAll(Sort.by("orderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Orders::getOrderId, Orders::getOrderNumber)));
    }

    @GetMapping("/cancelledByValues")
    public ResponseEntity<Map<Long, String>> getCancelledByValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
