package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.CancellationRiderRequest;
import com.michael_delivery.backend.enums.CancellationStatusType;
import com.michael_delivery.backend.dto.CancellationRiderRequestDTO;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Orders;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.repository.OrdersRepository;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.service.CancellationRiderRequestService;
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

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/cancellationRiderRequests", produces = MediaType.APPLICATION_JSON_VALUE)
public class CancellationRiderRequestController {

    private final CancellationRiderRequestService cancellationRiderRequestService;
    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;

    public CancellationRiderRequestController(
            final CancellationRiderRequestService cancellationRiderRequestService,
            final OrdersRepository ordersRepository, final UsersRepository usersRepository) {
        this.cancellationRiderRequestService = cancellationRiderRequestService;
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_RIDER_REQUESTS','VIEW_CANCELLATION_RIDER_REQUEST_MANY')")
    public ResponseEntity<List<CancellationRiderRequestDTO>> getAllCancellationRiderRequests(
    ) {
        return ResponseEntity.ok(cancellationRiderRequestService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_RIDER_REQUESTS','VIEW_CANCELLATION_RIDER_REQUEST_MANY')")
    public ResponseEntity<Page<CancellationRiderRequestDTO>> searchCancellationRiderRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "cancellationRiderRequestId:asc") String[] sortBy,
            @RequestParam(required = false) CancellationStatusType status,
            @RequestParam(required = false) String reason,
            @RequestParam(required = false) String remark
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<CancellationRiderRequest> spec = new GenericSpecification<>();
        Specification<CancellationRiderRequest> statusSpec = spec.equals("status", status);
        Specification<CancellationRiderRequest> reasonSpec = spec.contains("reason", reason);
        Specification<CancellationRiderRequest> remarkSpec = spec.contains("remark", remark);
        Specification<CancellationRiderRequest> finalSpec = Specification.where(statusSpec).and(statusSpec).and(reasonSpec).and(remarkSpec).and(remarkSpec);
        return ResponseEntity.ok(cancellationRiderRequestService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_RIDER_REQUESTS','VIEW_CANCELLATION_RIDER_REQUEST_MANY')")
    public ResponseEntity<Page<CancellationRiderRequestDTO>> getAllCancellationRiderRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "cancellationRiderRequestId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(cancellationRiderRequestService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{cancellationRiderRequestId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_RIDER_REQUESTS','VIEW_CANCELLATION_RIDER_REQUEST')")
    public ResponseEntity<CancellationRiderRequestDTO> getCancellationRiderRequest(
            @PathVariable(name = "cancellationRiderRequestId") final Long cancellationRiderRequestId) {
        return ResponseEntity.ok(cancellationRiderRequestService.get(cancellationRiderRequestId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_RIDER_REQUESTS','UPDATE_CANCELLATION_RIDER_REQUEST_ONE')")
    public ResponseEntity<Long> createCancellationRiderRequest(
            @RequestBody @Valid final CancellationRiderRequestDTO cancellationRiderRequestDTO) {
        final Long createdCancellationRiderRequestId = cancellationRiderRequestService.create(cancellationRiderRequestDTO);
        return new ResponseEntity<>(createdCancellationRiderRequestId, HttpStatus.CREATED);
    }

    @PutMapping("/{cancellationRiderRequestId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_RIDER_REQUESTS','UPDATE_CANCELLATION_RIDER_REQUEST_ONE')")
    public ResponseEntity<Long> updateCancellationRiderRequest(
            @PathVariable(name = "cancellationRiderRequestId") final Long cancellationRiderRequestId,
            @RequestBody @Valid final CancellationRiderRequestDTO cancellationRiderRequestDTO) {
        cancellationRiderRequestService.update(cancellationRiderRequestId, cancellationRiderRequestDTO);
        return ResponseEntity.ok(cancellationRiderRequestId);
    }

    @DeleteMapping("/{cancellationRiderRequestId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_RIDER_REQUESTS','DELETE_CANCELLATION_RIDER_REQUEST_ONE')")
    public ResponseEntity<Void> deleteCancellationRiderRequest(
            @PathVariable(name = "cancellationRiderRequestId") final Long cancellationRiderRequestId) {
        cancellationRiderRequestService.delete(cancellationRiderRequestId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orderValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_RIDER_REQUESTS','VIEW_CANCELLATION_RIDER_REQUEST_MANY')")
    public ResponseEntity<Map<Long, String>> getOrderValues() {
        return ResponseEntity.ok(ordersRepository.findAll(Sort.by("orderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Orders::getOrderId, Orders::getOrderNumber)));
    }

    @GetMapping("/cancelledByValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_RIDER_REQUESTS','VIEW_CANCELLATION_RIDER_REQUEST_MANY')")
    public ResponseEntity<Map<Long, String>> getCancelledByValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
