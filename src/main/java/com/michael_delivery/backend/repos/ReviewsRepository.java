package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.domain.Reviews;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.ReviewsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewsRepository extends JpaRepository<Reviews, Long>  ,BaseRepository<ReviewsDTO,Reviews> {

    Reviews findFirstByRider(Riders riders);

    Reviews findFirstByUser(Users users);

    Reviews findFirstByOrder(Orders orders);

}
