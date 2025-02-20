package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Penalities;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PenalitiesRepository extends JpaRepository<Penalities, Long> {

    Penalities findFirstByRider(Riders riders);

    Penalities findFirstByAdmin(Users users);

}
