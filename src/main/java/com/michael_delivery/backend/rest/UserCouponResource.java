package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.UserCoupon;
import com.michael_delivery.backend.enums.DiscountType;
import com.michael_delivery.backend.enums.VehicleType;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.model.UserCouponDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Coupons;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.UserCouponDTO;
import com.michael_delivery.backend.repos.CouponsRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.UserCouponService;
import com.michael_delivery.backend.util.CustomCollectors;
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
@RequestMapping(value = "/api/userCoupons", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserCouponResource {

    private final UserCouponService userCouponService;
    private final UsersRepository usersRepository;
    private final CouponsRepository couponsRepository;

    public UserCouponResource(final UserCouponService userCouponService,
            final UsersRepository usersRepository, final CouponsRepository couponsRepository) {
        this.userCouponService = userCouponService;
        this.usersRepository = usersRepository;
        this.couponsRepository = couponsRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserCouponDTO>> getAllUserCoupon(
    ) {
        return ResponseEntity.ok(userCouponService.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<UserCouponDTO>> getAllUserCoupon(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userCouponId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(userCouponService.findAll(pageable.getPageable()));
    }
    @GetMapping("/{userCouponId}")
    public ResponseEntity<UserCouponDTO> getUserCoupon(
            @PathVariable(name = "userCouponId") final Long userCouponId) {
        return ResponseEntity.ok(userCouponService.get(userCouponId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUserCoupon(
            @RequestBody @Valid final UserCouponDTO userCouponDTO) {
        final Long createdUserCouponId = userCouponService.create(userCouponDTO);
        return new ResponseEntity<>(createdUserCouponId, HttpStatus.CREATED);
    }

    @PutMapping("/{userCouponId}")
    public ResponseEntity<Long> updateUserCoupon(
            @PathVariable(name = "userCouponId") final Long userCouponId,
            @RequestBody @Valid final UserCouponDTO userCouponDTO) {
        userCouponService.update(userCouponId, userCouponDTO);
        return ResponseEntity.ok(userCouponId);
    }

    @DeleteMapping("/{userCouponId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUserCoupon(
            @PathVariable(name = "userCouponId") final Long userCouponId) {
        userCouponService.delete(userCouponId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<Long, String>> getUserValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

    @GetMapping("/couponValues")
    public ResponseEntity<Map<Long, DiscountType>> getCouponValues() {
        return ResponseEntity.ok(couponsRepository.findAll(Sort.by("couponId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Coupons::getCouponId, Coupons::getDiscountType)));
    }

}
