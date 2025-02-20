package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.domain.Reviews;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.ReviewsDTO;
import com.michael_delivery.backend.repos.OrdersRepository;
import com.michael_delivery.backend.repos.ReviewsRepository;
import com.michael_delivery.backend.repos.RidersRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewsService {

    private final ReviewsRepository reviewsRepository;
    private final RidersRepository ridersRepository;
    private final UsersRepository usersRepository;
    private final OrdersRepository ordersRepository;

    public ReviewsService(final ReviewsRepository reviewsRepository,
            final RidersRepository ridersRepository, final UsersRepository usersRepository,
            final OrdersRepository ordersRepository) {
        this.reviewsRepository = reviewsRepository;
        this.ridersRepository = ridersRepository;
        this.usersRepository = usersRepository;
        this.ordersRepository = ordersRepository;
    }

    public List<ReviewsDTO> findAll() {
        final List<Reviews> reviewses = reviewsRepository.findAll(Sort.by("reviewId"));
        return reviewses.stream()
                .map(reviews -> mapToDTO(reviews, new ReviewsDTO()))
                .toList();
    }

    public ReviewsDTO get(final Long reviewId) {
        return reviewsRepository.findById(reviewId)
                .map(reviews -> mapToDTO(reviews, new ReviewsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ReviewsDTO reviewsDTO) {
        final Reviews reviews = new Reviews();
        mapToEntity(reviewsDTO, reviews);
        return reviewsRepository.save(reviews).getReviewId();
    }

    public void update(final Long reviewId, final ReviewsDTO reviewsDTO) {
        final Reviews reviews = reviewsRepository.findById(reviewId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(reviewsDTO, reviews);
        reviewsRepository.save(reviews);
    }

    public void delete(final Long reviewId) {
        reviewsRepository.deleteById(reviewId);
    }

    private ReviewsDTO mapToDTO(final Reviews reviews, final ReviewsDTO reviewsDTO) {
        reviewsDTO.setReviewId(reviews.getReviewId());
        reviewsDTO.setReview(reviews.getReview());
        reviewsDTO.setRate(reviews.getRate());
        reviewsDTO.setRider(reviews.getRider() == null ? null : reviews.getRider().getRiderId());
        reviewsDTO.setUser(reviews.getUser() == null ? null : reviews.getUser().getUserId());
        reviewsDTO.setOrder(reviews.getOrder() == null ? null : reviews.getOrder().getOrderId());
        return reviewsDTO;
    }

    private Reviews mapToEntity(final ReviewsDTO reviewsDTO, final Reviews reviews) {
        reviews.setReview(reviewsDTO.getReview());
        reviews.setRate(reviewsDTO.getRate());
        final Riders rider = reviewsDTO.getRider() == null ? null : ridersRepository.findById(reviewsDTO.getRider())
                .orElseThrow(() -> new NotFoundException("rider not found"));
        reviews.setRider(rider);
        final Users user = reviewsDTO.getUser() == null ? null : usersRepository.findById(reviewsDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        reviews.setUser(user);
        final Orders order = reviewsDTO.getOrder() == null ? null : ordersRepository.findById(reviewsDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        reviews.setOrder(order);
        return reviews;
    }

}
