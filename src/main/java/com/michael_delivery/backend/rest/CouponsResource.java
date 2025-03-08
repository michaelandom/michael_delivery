package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Coupons;
import com.michael_delivery.backend.enums.CouponType;
import com.michael_delivery.backend.enums.DiscountType;
import com.michael_delivery.backend.enums.UserImportType;
import com.michael_delivery.backend.model.CouponsDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.CouponsDTO;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.CouponsService;
import com.michael_delivery.backend.util.CustomCollectors;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(value = "/api/coupons", produces = MediaType.APPLICATION_JSON_VALUE)
public class CouponsResource {

    private final CouponsService couponsService;
    private final UsersRepository usersRepository;

    public CouponsResource(final CouponsService couponsService,
            final UsersRepository usersRepository) {
        this.couponsService = couponsService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CouponsDTO>> getAllCoupons(
    ) {
        return ResponseEntity.ok(couponsService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CouponsDTO>> searchCoupons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "couponId:asc") String[] sortBy,
            @RequestParam(required = false) DiscountType discountType,
            @RequestParam(required = false) Double discountAmount,
            @RequestParam(required = false) Double discountPercentage,
            @RequestParam(required = false) Double maximumDiscountAmount,
            @RequestParam(required = false) Double minimumPurchasePrice,
            @RequestParam(required = false) Long numberOfIssuedCoupons,
            @RequestParam(required = false) Long numberOfUsedCoupons,
            @RequestParam(required = false) CouponType issuedTo,
            @RequestParam(required = false) UserImportType howUserWasAdded,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            @RequestParam(required = false) boolean discountAmountIsGreaterThan,
            @RequestParam(required = false) boolean discountPercentageIsGreaterThan,
            @RequestParam(required = false) boolean maximumDiscountAmountIsGreaterThan,
            @RequestParam(required = false) boolean minimumPurchaseIsGreaterThan,
            @RequestParam(required = false) boolean numberOfIssuedCouponsIsGreaterThan,
            @RequestParam(required = false) boolean numberOfUsedCouponsIsGreaterThan,
            @RequestParam(required = false) boolean startDateIsAfter,
            @RequestParam(required = false) boolean endDateIsAfter
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Coupons> spec = new GenericSpecification<>();
        Specification<Coupons> discountTypeSpec = spec.equals("discountType", discountType);
        Specification<Coupons> discountAmountSpec = discountAmountIsGreaterThan ? spec.greaterThan("discountAmount", discountAmount) : spec.lessThan("discountAmount", discountAmount);
        Specification<Coupons> discountPercentageSpec =discountPercentageIsGreaterThan ?  spec.greaterThan("discountPercentage", discountPercentage) : spec.lessThan("discountPercentage", discountPercentage);
        Specification<Coupons> maximumDiscountAmountSpec = maximumDiscountAmountIsGreaterThan ? spec.greaterThan("maximumDiscountAmount", maximumDiscountAmount) : spec.lessThan("maximumDiscountAmount", maximumDiscountAmount);
        Specification<Coupons> minimumPurchasePriceSpec = minimumPurchaseIsGreaterThan? spec.greaterThan("minimumPurchasePrice", minimumPurchasePrice) : spec.lessThan("minimumPurchasePrice", minimumPurchasePrice);
        Specification<Coupons> numberOfIssuedCouponsSpec = numberOfIssuedCouponsIsGreaterThan ? spec.greaterThan("numberOfIssuedCoupons",numberOfIssuedCoupons) : spec.lessThan("numberOfIssuedCoupons", numberOfIssuedCoupons);
        Specification<Coupons> numberOfUsedCouponsSpec = numberOfUsedCouponsIsGreaterThan ? spec.greaterThan("numberOfUsedCoupons",numberOfUsedCoupons) : spec.lessThan("numberOfUsedCoupons", numberOfUsedCoupons);
        Specification<Coupons> issuedToSpec = spec.equals("issuedTo", issuedTo);
        Specification<Coupons> howUserWasAddedSpec = spec.equals("howUserWasAdded", howUserWasAdded);
        Specification<Coupons> startDateSpec = startDateIsAfter ? spec.dateAfter("startDate",startDate) : spec.dateBefore("startDate", startDate);
        Specification<Coupons> endDateSpec = endDateIsAfter ? spec.dateAfter("endDate",endDate) : spec.dateBefore("endDate", endDate);
        Specification<Coupons> finalSpec = Specification.where(discountTypeSpec).and(discountAmountSpec).and(discountPercentageSpec).and(maximumDiscountAmountSpec).and(minimumPurchasePriceSpec).and(numberOfIssuedCouponsSpec).and(numberOfUsedCouponsSpec).and(issuedToSpec).and(howUserWasAddedSpec).and(startDateSpec).and(endDateSpec);
        return ResponseEntity.ok(couponsService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    public ResponseEntity<Page<CouponsDTO>> getAllCoupons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "couponId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(couponsService.findAll(pageable.getPageable()));
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
