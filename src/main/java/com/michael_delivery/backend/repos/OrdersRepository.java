package com.michael_delivery.backend.repos;


import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Orders findFirstByRider(Riders riders);

    Orders findFirstByCustomer(Users users);

    Orders findFirstByAssignedBy(Users users);

}
