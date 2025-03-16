package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.*;
import com.michael_delivery.backend.dto.ReviewsDTO;
import com.michael_delivery.backend.repository.*;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class ReviewsService extends BaseService<Reviews, ReviewsDTO,Long, ReviewsRepository>{

    private final ReviewsRepository reviewsRepository;
    private final RidersRepository ridersRepository;
    private final UsersRepository usersRepository;
    private final OrdersRepository ordersRepository;

    public ReviewsService(final ReviewsRepository reviewsRepository,
            final RidersRepository ridersRepository, final UsersRepository usersRepository,
            final OrdersRepository ordersRepository) {
        super(reviewsRepository,"reviewId");

        this.reviewsRepository = reviewsRepository;
        this.ridersRepository = ridersRepository;
        this.usersRepository = usersRepository;
        this.ordersRepository = ordersRepository;
    }


    @Override
    public Page<ReviewsDTO> search(Specification<Reviews> query, Pageable pageable) {
        return this.reviewsRepository.findAll(query, pageable);
    }
    @Override
    protected ReviewsDTO mapToDTO(final Reviews reviews, final ReviewsDTO reviewsDTO) {
        reviewsDTO.setReviewId(reviews.getReviewId());
        reviewsDTO.setReview(reviews.getReview());
        reviewsDTO.setRate(reviews.getRate());
        reviewsDTO.setRider(reviews.getRider() == null ? null : reviews.getRider().getRiderId());
        reviewsDTO.setUser(reviews.getUser() == null ? null : reviews.getUser().getUserId());
        reviewsDTO.setOrder(reviews.getOrder() == null ? null : reviews.getOrder().getOrderId());
        return reviewsDTO;
    }

    @Override
    protected Reviews mapToEntity(final ReviewsDTO reviewsDTO, final Reviews reviews) {
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

    @Override
    protected ReviewsDTO createDTO() {
        return new ReviewsDTO();
    }

    @Override
    protected Reviews createEntity() {
        return new Reviews();
    }

}
