package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.NoneBusinessHourRates;
import com.michael_delivery.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoneBusinessHourRatesRepository extends JpaRepository<NoneBusinessHourRates, Long> {

    NoneBusinessHourRates findFirstByCreatedBy(Users users);

//    boolean existsByUniqueStartTimeEndTimeCheckIgnoreCase(String uniqueStartTimeEndTimeCheck);

}
