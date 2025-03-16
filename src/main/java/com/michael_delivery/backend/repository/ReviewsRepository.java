package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Orders;
import com.michael_delivery.backend.model.Reviews;
import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.ReviewsDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewsRepository extends JpaRepository<Reviews, Long>  ,BaseRepository<ReviewsDTO,Reviews> {

    Reviews findFirstByRider(Riders riders);

    Reviews findFirstByUser(Users users);

    Reviews findFirstByOrder(Orders orders);

}
