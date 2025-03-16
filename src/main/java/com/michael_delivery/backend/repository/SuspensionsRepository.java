package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.model.Suspensions;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.SuspensionsDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SuspensionsRepository extends JpaRepository<Suspensions, Long> ,BaseRepository<SuspensionsDTO,Suspensions> {

    Suspensions findFirstByRider(Riders riders);

    Suspensions findFirstBySuspenedBy(Users users);

}
