package com.michael_delivery.backend.rest;

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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/reviewss", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping
    public ResponseEntity<List<ReviewsDTO>> getAllReviewss() {
        return ResponseEntity.ok(reviewsService.findAll());
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
