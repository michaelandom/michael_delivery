package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.CancellationRequestDTO;
import com.michael_delivery.backend.repos.OrdersRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.CancellationRequestService;
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
@RequestMapping(value = "/api/cancellationRequests", produces = MediaType.APPLICATION_JSON_VALUE)
public class CancellationRequestResource {

    private final CancellationRequestService cancellationRequestService;
    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;

    public CancellationRequestResource(final CancellationRequestService cancellationRequestService,
            final OrdersRepository ordersRepository, final UsersRepository usersRepository) {
        this.cancellationRequestService = cancellationRequestService;
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public ResponseEntity<List<CancellationRequestDTO>> getAllCancellationRequests() {
        return ResponseEntity.ok(cancellationRequestService.findAll());
    }

    @GetMapping("/{cancellationRequestId}")
    public ResponseEntity<CancellationRequestDTO> getCancellationRequest(
            @PathVariable(name = "cancellationRequestId") final Long cancellationRequestId) {
        return ResponseEntity.ok(cancellationRequestService.get(cancellationRequestId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCancellationRequest(
            @RequestBody @Valid final CancellationRequestDTO cancellationRequestDTO) {
        final Long createdCancellationRequestId = cancellationRequestService.create(cancellationRequestDTO);
        return new ResponseEntity<>(createdCancellationRequestId, HttpStatus.CREATED);
    }

    @PutMapping("/{cancellationRequestId}")
    public ResponseEntity<Long> updateCancellationRequest(
            @PathVariable(name = "cancellationRequestId") final Long cancellationRequestId,
            @RequestBody @Valid final CancellationRequestDTO cancellationRequestDTO) {
        cancellationRequestService.update(cancellationRequestId, cancellationRequestDTO);
        return ResponseEntity.ok(cancellationRequestId);
    }

    @DeleteMapping("/{cancellationRequestId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCancellationRequest(
            @PathVariable(name = "cancellationRequestId") final Long cancellationRequestId) {
        cancellationRequestService.delete(cancellationRequestId);
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
