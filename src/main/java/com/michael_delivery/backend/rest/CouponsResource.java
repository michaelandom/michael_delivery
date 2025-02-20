package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.CouponsDTO;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.CouponsService;
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
@RequestMapping(value = "/api/coupons", produces = MediaType.APPLICATION_JSON_VALUE)
public class CouponsResource {

    private final CouponsService couponsService;
    private final UsersRepository usersRepository;

    public CouponsResource(final CouponsService couponsService,
            final UsersRepository usersRepository) {
        this.couponsService = couponsService;
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public ResponseEntity<List<CouponsDTO>> getAllCoupons() {
        return ResponseEntity.ok(couponsService.findAll());
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponsDTO> getCoupons(
            @PathVariable(name = "couponId") final Long couponId) {
        return ResponseEntity.ok(couponsService.get(couponId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCoupons(@RequestBody @Valid final CouponsDTO couponsDTO) {
        final Long createdCouponId = couponsService.create(couponsDTO);
        return new ResponseEntity<>(createdCouponId, HttpStatus.CREATED);
    }

    @PutMapping("/{couponId}")
    public ResponseEntity<Long> updateCoupons(@PathVariable(name = "couponId") final Long couponId,
            @RequestBody @Valid final CouponsDTO couponsDTO) {
        couponsService.update(couponId, couponsDTO);
        return ResponseEntity.ok(couponId);
    }

    @DeleteMapping("/{couponId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCoupons(
            @PathVariable(name = "couponId") final Long couponId) {
        final ReferencedWarning referencedWarning = couponsService.getReferencedWarning(couponId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        couponsService.delete(couponId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/createdByValues")
    public ResponseEntity<Map<Long, String>> getCreatedByValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
