package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.model.ExtrFeeDTO;
import com.michael_delivery.backend.repos.OrdersRepository;
import com.michael_delivery.backend.service.ExtrFeeService;
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
@RequestMapping(value = "/api/extrFees", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExtrFeeResource {

    private final ExtrFeeService extrFeeService;
    private final OrdersRepository ordersRepository;

    public ExtrFeeResource(final ExtrFeeService extrFeeService,
            final OrdersRepository ordersRepository) {
        this.extrFeeService = extrFeeService;
        this.ordersRepository = ordersRepository;
    }

    @GetMapping
    public ResponseEntity<List<ExtrFeeDTO>> getAllExtrFees() {
        return ResponseEntity.ok(extrFeeService.findAll());
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
