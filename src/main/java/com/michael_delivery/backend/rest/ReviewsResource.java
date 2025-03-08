package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Reviews;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.model.ReviewsDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.ReviewsDTO;
import com.michael_delivery.backend.repos.OrdersRepository;
import com.michael_delivery.backend.repos.RidersRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.ReviewsService;
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
@RequestMapping(value = "/api/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewsResource {

    private final ReviewsService reviewsService;
    private final RidersRepository ridersRepository;
    private final UsersRepository usersRepository;
    private final OrdersRepository ordersRepository;

    public ReviewsResource(final ReviewsService reviewsService,
            final RidersRepository ridersRepository, final UsersRepository usersRepository,
            final OrdersRepository ordersRepository) {
        this.reviewsService = reviewsService;
        this.ridersRepository = ridersRepository;
        this.usersRepository = usersRepository;
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReviewsDTO>> getAllReviews(
    ) {
        return ResponseEntity.ok(reviewsService.findAll());
    }

    @GetMapping("/search")
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
    public ResponseEntity<Page<ReviewsDTO>> getAllReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "reviewId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(reviewsService.findAll(pageable.getPageable()));
    }
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewsDTO> getReviews(
            @PathVariable(name = "reviewId") final Long reviewId) {
        return ResponseEntity.ok(reviewsService.get(reviewId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createReviews(@RequestBody @Valid final ReviewsDTO reviewsDTO) {
        final Long createdReviewId = reviewsService.create(reviewsDTO);
        return new ResponseEntity<>(createdReviewId, HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Long> updateReviews(@PathVariable(name = "reviewId") final Long reviewId,
            @RequestBody @Valid final ReviewsDTO reviewsDTO) {
        reviewsService.update(reviewId, reviewsDTO);
        return ResponseEntity.ok(reviewId);
    }

    @DeleteMapping("/{reviewId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteReviews(
            @PathVariable(name = "reviewId") final Long reviewId) {
        reviewsService.delete(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/riderValues")
    public ResponseEntity<Map<Long, String>> getRiderValues() {
        return ResponseEntity.ok(ridersRepository.findAll(Sort.by("riderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Riders::getRiderId, Riders::getEmergencyContactFirstName)));
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<Long, String>> getUserValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

    @GetMapping("/orderValues")
    public ResponseEntity<Map<Long, String>> getOrderValues() {
        return ResponseEntity.ok(ordersRepository.findAll(Sort.by("orderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Orders::getOrderId, Orders::getOrderNumber)));
    }

}
