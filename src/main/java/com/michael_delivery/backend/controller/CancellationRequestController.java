package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.CancellationRequest;
import com.michael_delivery.backend.enums.CancellationStatusType;
import com.michael_delivery.backend.enums.CancellationType;
import com.michael_delivery.backend.enums.CancelledByType;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Orders;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.CancellationRequestDTO;
import com.michael_delivery.backend.repository.OrdersRepository;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.service.CancellationRequestService;
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
@RequestMapping(value = "/api/cancellationRequests", produces = MediaType.APPLICATION_JSON_VALUE)
public class CancellationRequestController {

    private final CancellationRequestService cancellationRequestService;
    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;

    public CancellationRequestController(final CancellationRequestService cancellationRequestService,
            final OrdersRepository ordersRepository, final UsersRepository usersRepository) {
        this.cancellationRequestService = cancellationRequestService;
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_REQUESTS','VIEW_CANCELLATION_REQUEST_MANY')")
    public ResponseEntity<List<CancellationRequestDTO>> getAllCancellationRequests(
    ) {
        return ResponseEntity.ok(cancellationRequestService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_REQUESTS','VIEW_CANCELLATION_REQUEST_MANY')")
    public ResponseEntity<Page<CancellationRequestDTO>> searchCancellationRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "cancellationRequestId:asc") String[] sortBy,
            @RequestParam(required = false) CancellationType type,
            @RequestParam(required = false) CancellationStatusType status,
            @RequestParam(required = false) String reason,
            @RequestParam(required = false) String remark,
            @RequestParam(required = false) CancelledByType cancelledByType,
            @RequestParam(required = false) Double cancellationFee,
            @RequestParam(required = false) Double refundAmount,
            @RequestParam(required = false) Double paidAt,
            @RequestParam(required = false) boolean cancellationFeeIsGreaterThan,
            @RequestParam(required = false) boolean refundAmountIsGreaterThan,
            @RequestParam(required = false) boolean paidAtIsAfter
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<CancellationRequest> spec = new GenericSpecification<>();
        Specification<CancellationRequest> typeSpec = spec.equals("type", type);
        Specification<CancellationRequest> statusSpec = spec.equals("status", status);
        Specification<CancellationRequest> reasonSpec = spec.contains("reason", reason);
        Specification<CancellationRequest> remarkSpec = spec.contains("remark", remark);
        Specification<CancellationRequest> cancelledByTypeSpec = spec.equals("cancelledByType", cancelledByType);
        Specification<CancellationRequest> cancellationFeeSpec = cancellationFeeIsGreaterThan ? spec.greaterThan("cancellationFee", cancellationFee):spec.lessThan("cancellationFee", cancellationFee) ;
        Specification<CancellationRequest> refundAmountSpec = refundAmountIsGreaterThan ? spec.greaterThan("refundAmount", refundAmount):spec.lessThan("refundAmount", refundAmount) ;
        Specification<CancellationRequest> paidAtSpec = paidAtIsAfter ? spec.dateAfter("paidAt", paidAt):spec.dateBefore("paidAt", paidAt) ;
        Specification<CancellationRequest> finalSpec = Specification.where(typeSpec).and(statusSpec).and(reasonSpec).and(remarkSpec).and(cancelledByTypeSpec).and(cancellationFeeSpec).and(refundAmountSpec).and(paidAtSpec);
        return ResponseEntity.ok(cancellationRequestService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_REQUESTS','VIEW_CANCELLATION_REQUEST_MANY')")
    public ResponseEntity<Page<CancellationRequestDTO>> getAllCancellationRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "cancellationRequestId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(cancellationRequestService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{cancellationRequestId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_REQUESTS','VIEW_CANCELLATION_REQUEST')")
    public ResponseEntity<CancellationRequestDTO> getCancellationRequest(
            @PathVariable(name = "cancellationRequestId") final Long cancellationRequestId) {
        return ResponseEntity.ok(cancellationRequestService.get(cancellationRequestId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_REQUESTS','UPDATE_CANCELLATION_REQUEST_ONE')")
    public ResponseEntity<Long> createCancellationRequest(
            @RequestBody @Valid final CancellationRequestDTO cancellationRequestDTO) {
        final Long createdCancellationRequestId = cancellationRequestService.create(cancellationRequestDTO);
        return new ResponseEntity<>(createdCancellationRequestId, HttpStatus.CREATED);
    }

    @PutMapping("/{cancellationRequestId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_REQUESTS','UPDATE_CANCELLATION_REQUEST_ONE')")
    public ResponseEntity<Long> updateCancellationRequest(
            @PathVariable(name = "cancellationRequestId") final Long cancellationRequestId,
            @RequestBody @Valid final CancellationRequestDTO cancellationRequestDTO) {
        cancellationRequestService.update(cancellationRequestId, cancellationRequestDTO);
        return ResponseEntity.ok(cancellationRequestId);
    }

    @DeleteMapping("/{cancellationRequestId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_REQUESTS','DELETE_CANCELLATION_REQUEST_ONE')")
    public ResponseEntity<Void> deleteCancellationRequest(
            @PathVariable(name = "cancellationRequestId") final Long cancellationRequestId) {
        cancellationRequestService.delete(cancellationRequestId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orderValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_REQUESTS','VIEW_CANCELLATION_REQUEST_MANY')")
    public ResponseEntity<Map<Long, String>> getOrderValues() {
        return ResponseEntity.ok(ordersRepository.findAll(Sort.by("orderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Orders::getOrderId, Orders::getOrderNumber)));
    }

    @GetMapping("/cancelledByValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_CANCELLATION_REQUESTS','VIEW_CANCELLATION_REQUEST_MANY')")
    public ResponseEntity<Map<Long, String>> getCancelledByValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
