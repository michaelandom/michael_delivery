package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.enums.OrderStatusType;
import com.michael_delivery.backend.enums.VehicleType;
import com.michael_delivery.backend.model.OrdersDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    @GetMapping("/all")
    public ResponseEntity<List<OrdersDTO>> getAllOrders(
    ) {
        return ResponseEntity.ok(ordersService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OrdersDTO>> searchOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderId:asc") String[] sortBy,
            @RequestParam(required = false) String orderNumber,
            @RequestParam(required = false) String customerFullName,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) String customerPhoneNumber,
            @RequestParam(required = false) Double totalPrice,
            @RequestParam(required = false) Double totalDistance,
            @RequestParam(required = false) Double boostedBy,
            @RequestParam(required = false) boolean ageLimit,
            @RequestParam(required = false) VehicleType vehicleType,
            @RequestParam(required = false) OrderStatusType orderStatus,
            @RequestParam(required = false) boolean totalPriceIsGreaterThan,
            @RequestParam(required = false) boolean totalDistanceIsGreaterThan,
            @RequestParam(required = false) boolean boostedByIsGreaterThan

    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Orders> spec = new GenericSpecification<>();
        Specification<Orders> orderNumberSpec = spec.contains("orderNumber", orderNumber);
        Specification<Orders> customerFullNameSpec = spec.contains("customerFullName", customerFullName);
        Specification<Orders> messageSpec = spec.contains("message", message);
        Specification<Orders> customerPhoneNumberSpec = spec.contains("customerPhoneNumber", customerPhoneNumber);
        Specification<Orders> totalPriceSpec = totalPriceIsGreaterThan ? spec.greaterThan("totalPrice", totalPrice): spec.lessThan("totalPrice", totalPrice);
        Specification<Orders> totalDistanceSpec = totalDistanceIsGreaterThan ? spec.greaterThan("totalDistance", totalDistance): spec.lessThan("totalDistance", totalDistance);
        Specification<Orders> boostedBySpec = boostedByIsGreaterThan ? spec.greaterThan("boostedBy", boostedBy): spec.lessThan("boostedBy", boostedBy);
        Specification<Orders> ageLimitSpec = spec.equals("ageLimit", ageLimit);
        Specification<Orders> vehicleTypeSpec = spec.equals("vehicleType", vehicleType);
        Specification<Orders> orderStatusSpec = spec.equals("orderStatus", orderStatus);
        Specification<Orders> finalSpec = Specification.where(orderNumberSpec)
                .and(customerFullNameSpec)
                .and(messageSpec)
                .and(customerPhoneNumberSpec)
                .and(totalPriceSpec)
                .and(totalDistanceSpec)
                .and(boostedBySpec)
               .and(ageLimitSpec)
                .and(vehicleTypeSpec)
                .and(orderStatusSpec);
        return ResponseEntity.ok(ordersService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    public ResponseEntity<Page<OrdersDTO>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(ordersService.findAll(pageable.getPageable()));
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
