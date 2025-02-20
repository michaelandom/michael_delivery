package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.OrdersDTO;
import com.michael_delivery.backend.repos.RidersRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.OrdersService;
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
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrdersResource {

    private final OrdersService ordersService;
    private final RidersRepository ridersRepository;
    private final UsersRepository usersRepository;

    public OrdersResource(final OrdersService ordersService,
                          final RidersRepository ridersRepository,
                          final UsersRepository usersRepository) {
        this.ordersService = ordersService;
        this.ridersRepository = ridersRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public ResponseEntity<List<OrdersDTO>> getAllOrders() {
        return ResponseEntity.ok(ordersService.findAll());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersDTO> getOrders(@PathVariable(name = "orderId") final Long orderId) {
        return ResponseEntity.ok(ordersService.get(orderId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createOrders(@RequestBody @Valid final OrdersDTO ordersDTO) {
        final Long createdOrderId = ordersService.create(ordersDTO);
        return new ResponseEntity<>(createdOrderId, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Long> updateOrders(@PathVariable(name = "orderId") final Long orderId,
            @RequestBody @Valid final OrdersDTO ordersDTO) {
        ordersService.update(orderId, ordersDTO);
        return ResponseEntity.ok(orderId);
    }

    @DeleteMapping("/{orderId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOrders(@PathVariable(name = "orderId") final Long orderId) {
        final ReferencedWarning referencedWarning = ordersService.getReferencedWarning(orderId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        ordersService.delete(orderId);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/riderValues")
//    public ResponseEntity<Map<Long, String>> getRiderValues() {
//        return ResponseEntity.ok(ridersRepository.findAll(Sort.by("riderId"))
//                .stream()
//                .collect(CustomCollectors.toSortedMap(Riders::getRiderId, Riders::getEmergencyContactFirstName)));
//    }

    @GetMapping("/customerValues")
    public ResponseEntity<Map<Long, String>> getCustomerValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

    @GetMapping("/assignedByValues")
    public ResponseEntity<Map<Long, String>> getAssignedByValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
