package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.Reviews;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.dto.ReviewsDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Orders;
import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.repository.OrdersRepository;
import com.michael_delivery.backend.repository.RidersRepository;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.service.ReviewsService;
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
@RequestMapping(value = "/api/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewsController {

    private final ReviewsService reviewsService;
    private final RidersRepository ridersRepository;
    private final UsersRepository usersRepository;
    private final OrdersRepository ordersRepository;

    public ReviewsController(final ReviewsService reviewsService,
            final RidersRepository ridersRepository, final UsersRepository usersRepository,
            final OrdersRepository ordersRepository) {
        this.reviewsService = reviewsService;
        this.ridersRepository = ridersRepository;
        this.usersRepository = usersRepository;
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_REVIEWS','VIEW_REVIEW_MANY')")
    public ResponseEntity<List<ReviewsDTO>> getAllReviews(
    ) {
        return ResponseEntity.ok(reviewsService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_REVIEWS','VIEW_REVIEW_MANY')")
    public ResponseEntity<Page<ReviewsDTO>> searchReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "reviewId:asc") String[] sortBy,
            @RequestParam(required = false) String review,
            @RequestParam(required = false) String rate

    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Reviews> spec = new GenericSpecification<>();
        Specification<Reviews> reviewSpec = spec.contains("review", review);
        Specification<Reviews> rateSpec = spec.equals("rate", rate);
        Specification<Reviews> finalSpec = Specification.where(reviewSpec)
                .and(rateSpec);
        return ResponseEntity.ok(reviewsService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_REVIEWS','VIEW_REVIEW_MANY')")
    public ResponseEntity<Page<ReviewsDTO>> getAllReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "reviewId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(reviewsService.findAll(pageable.getPageable()));
    }
    @GetMapping("/{reviewId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_REVIEWS','VIEW_REVIEW')")
    public ResponseEntity<ReviewsDTO> getReviews(
            @PathVariable(name = "reviewId") final Long reviewId) {
        return ResponseEntity.ok(reviewsService.get(reviewId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_REVIEWS','UPDATE_REVIEW_ONE')")
    public ResponseEntity<Long> createReviews(@RequestBody @Valid final ReviewsDTO reviewsDTO) {
        final Long createdReviewId = reviewsService.create(reviewsDTO);
        return new ResponseEntity<>(createdReviewId, HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_REVIEWS','UPDATE_REVIEW_ONE')")
    public ResponseEntity<Long> updateReviews(@PathVariable(name = "reviewId") final Long reviewId,
            @RequestBody @Valid final ReviewsDTO reviewsDTO) {
        reviewsService.update(reviewId, reviewsDTO);
        return ResponseEntity.ok(reviewId);
    }

    @DeleteMapping("/{reviewId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_REVIEWS','DELETE_REVIEW_ONE')")
    public ResponseEntity<Void> deleteReviews(
            @PathVariable(name = "reviewId") final Long reviewId) {
        reviewsService.delete(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/riderValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_REVIEWS','VIEW_REVIEW_MANY')")
    public ResponseEntity<Map<Long, String>> getRiderValues() {
        return ResponseEntity.ok(ridersRepository.findAll(Sort.by("riderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Riders::getRiderId, Riders::getEmergencyContactFirstName)));
    }

    @GetMapping("/userValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_REVIEWS','VIEW_REVIEW_MANY')")
    public ResponseEntity<Map<Long, String>> getUserValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

    @GetMapping("/orderValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_REVIEWS','VIEW_REVIEW_MANY')")
    public ResponseEntity<Map<Long, String>> getOrderValues() {
        return ResponseEntity.ok(ordersRepository.findAll(Sort.by("orderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Orders::getOrderId, Orders::getOrderNumber)));
    }

}
