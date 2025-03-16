package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.RidersDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RidersRepository extends JpaRepository<Riders, Long>  ,BaseRepository<RidersDTO,Riders> {

    Riders findFirstByUser(Users users);

}
