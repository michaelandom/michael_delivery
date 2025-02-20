package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Suspensions;
import com.michael_delivery.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SuspensionsRepository extends JpaRepository<Suspensions, Long> {

    Suspensions findFirstByRider(Riders riders);

    Suspensions findFirstBySuspenedBy(Users users);

}
