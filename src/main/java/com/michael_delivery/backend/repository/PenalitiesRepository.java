package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Penalities;
import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.PenalitiesDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PenalitiesRepository extends JpaRepository<Penalities, Long>  ,BaseRepository<PenalitiesDTO,Penalities> {

    Penalities findFirstByRider(Riders riders);

    Penalities findFirstByAdmin(Users users);

}
