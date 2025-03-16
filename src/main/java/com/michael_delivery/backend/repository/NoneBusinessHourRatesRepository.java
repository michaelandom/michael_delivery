package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.NoneBusinessHourRates;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.NoneBusinessHourRatesDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoneBusinessHourRatesRepository extends JpaRepository<NoneBusinessHourRates, Long>,BaseRepository<NoneBusinessHourRatesDTO,NoneBusinessHourRates> {

    NoneBusinessHourRates findFirstByCreatedBy(Users users);

//    boolean existsByUniqueStartTimeEndTimeCheckIgnoreCase(String uniqueStartTimeEndTimeCheck);


}
