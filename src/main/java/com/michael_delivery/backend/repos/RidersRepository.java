package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RidersRepository extends JpaRepository<Riders, Long> {

    Riders findFirstByUser(Users users);

}
