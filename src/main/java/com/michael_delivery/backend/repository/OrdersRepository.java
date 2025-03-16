package com.michael_delivery.backend.repository;


import com.michael_delivery.backend.model.Orders;
import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.OrdersDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrdersRepository extends JpaRepository<Orders, Long> ,BaseRepository<OrdersDTO,Orders> {

    Orders findFirstByRider(Riders riders);

    Orders findFirstByCustomer(Users users);

    Orders findFirstByAssignedBy(Users users);

}
