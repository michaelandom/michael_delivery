package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.enums.DiscountType;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.dto.UserCouponDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Coupons;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.repository.CouponsRepository;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.service.UserCouponService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/userCoupons", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserCouponController {

    private final UserCouponService userCouponService;
    private final UsersRepository usersRepository;
    private final CouponsRepository couponsRepository;

    public UserCouponController(final UserCouponService userCouponService,
            final UsersRepository usersRepository, final CouponsRepository couponsRepository) {
        this.userCouponService = userCouponService;
        this.usersRepository = usersRepository;
        this.couponsRepository = couponsRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_COUPONS','VIEW_USER_COUPON_MANY')")
    public ResponseEntity<List<UserCouponDTO>> getAllUserCoupon(
    ) {
        return ResponseEntity.ok(userCouponService.findAll());
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_COUPONS','VIEW_USER_COUPON_MANY')")
    public ResponseEntity<Page<UserCouponDTO>> getAllUserCoupon(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userCouponId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(userCouponService.findAll(pageable.getPageable()));
    }
    @GetMapping("/{userCouponId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_COUPONS','VIEW_USER_COUPON')")
    public ResponseEntity<UserCouponDTO> getUserCoupon(
            @PathVariable(name = "userCouponId") final Long userCouponId) {
        return ResponseEntity.ok(userCouponService.get(userCouponId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_COUPONS','UPDATE_USER_COUPON_ONE')")
    public ResponseEntity<Long> createUserCoupon(
            @RequestBody @Valid final UserCouponDTO userCouponDTO) {
        final Long createdUserCouponId = userCouponService.create(userCouponDTO);
        return new ResponseEntity<>(createdUserCouponId, HttpStatus.CREATED);
    }

    @PutMapping("/{userCouponId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_COUPONS','UPDATE_USER_COUPON_ONE')")
    public ResponseEntity<Long> updateUserCoupon(
            @PathVariable(name = "userCouponId") final Long userCouponId,
            @RequestBody @Valid final UserCouponDTO userCouponDTO) {
        userCouponService.update(userCouponId, userCouponDTO);
        return ResponseEntity.ok(userCouponId);
    }

    @DeleteMapping("/{userCouponId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_COUPONS','DELETE_USER_COUPON_ONE')")
    public ResponseEntity<Void> deleteUserCoupon(
            @PathVariable(name = "userCouponId") final Long userCouponId) {
        userCouponService.delete(userCouponId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_COUPONS','VIEW_USER_COUPON_MANY')")
    public ResponseEntity<Map<Long, String>> getUserValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

    @GetMapping("/couponValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_COUPONS','VIEW_USER_COUPON_MANY')")
    public ResponseEntity<Map<Long, DiscountType>> getCouponValues() {
        return ResponseEntity.ok(couponsRepository.findAll(Sort.by("couponId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Coupons::getCouponId, Coupons::getDiscountType)));
    }

}
