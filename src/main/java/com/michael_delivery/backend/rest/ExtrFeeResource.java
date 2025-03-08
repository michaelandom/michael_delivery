package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.ExtrFee;
import com.michael_delivery.backend.enums.PaymentStatusType;
import com.michael_delivery.backend.model.ExtrFeeDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.model.ExtrFeeDTO;
import com.michael_delivery.backend.repos.OrdersRepository;
import com.michael_delivery.backend.service.ExtrFeeService;
import com.michael_delivery.backend.util.CustomCollectors;
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
@RequestMapping(value = "/api/extrFees", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExtrFeeResource {

    private final ExtrFeeService extrFeeService;
    private final OrdersRepository ordersRepository;

    public ExtrFeeResource(final ExtrFeeService extrFeeService,
            final OrdersRepository ordersRepository) {
        this.extrFeeService = extrFeeService;
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExtrFeeDTO>> getAllExtrFee(
    ) {
        return ResponseEntity.ok(extrFeeService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ExtrFeeDTO>> searchExtrFee(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "extrFeeId:asc") String[] sortBy,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) PaymentStatusType paymentStatus,
            @RequestParam(required = false) Date recipientDob
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<ExtrFee> spec = new GenericSpecification<>();
        Specification<ExtrFee> messageSpec = spec.contains("message", message);
        Specification<ExtrFee> paymentStatusSpec = spec.equals("paymentStatus", paymentStatus);
        Specification<ExtrFee> finalSpec = Specification.where(messageSpec).and(paymentStatusSpec);
        return ResponseEntity.ok(extrFeeService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    public ResponseEntity<Page<ExtrFeeDTO>> getAllExtrFee(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "extrFeeId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(extrFeeService.findAll(pageable.getPageable()));
    }
    @GetMapping("/{extrFeeId}")
    public ResponseEntity<ExtrFeeDTO> getExtrFee(
            @PathVariable(name = "extrFeeId") final Long extrFeeId) {
        return ResponseEntity.ok(extrFeeService.get(extrFeeId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createExtrFee(@RequestBody @Valid final ExtrFeeDTO extrFeeDTO) {
        final Long createdExtrFeeId = extrFeeService.create(extrFeeDTO);
        return new ResponseEntity<>(createdExtrFeeId, HttpStatus.CREATED);
    }

    @PutMapping("/{extrFeeId}")
    public ResponseEntity<Long> updateExtrFee(
            @PathVariable(name = "extrFeeId") final Long extrFeeId,
            @RequestBody @Valid final ExtrFeeDTO extrFeeDTO) {
        extrFeeService.update(extrFeeId, extrFeeDTO);
        return ResponseEntity.ok(extrFeeId);
    }

    @DeleteMapping("/{extrFeeId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteExtrFee(
            @PathVariable(name = "extrFeeId") final Long extrFeeId) {
        extrFeeService.delete(extrFeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orderValues")
    public ResponseEntity<Map<Long, String>> getOrderValues() {
        return ResponseEntity.ok(ordersRepository.findAll(Sort.by("orderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Orders::getOrderId, Orders::getOrderNumber)));
    }

}
