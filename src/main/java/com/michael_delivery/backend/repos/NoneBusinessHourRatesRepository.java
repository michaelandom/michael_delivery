package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.NoneBusinessHourRates;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.NoneBusinessHourRatesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoneBusinessHourRatesRepository extends JpaRepository<NoneBusinessHourRates, Long>,BaseRepository<NoneBusinessHourRatesDTO,NoneBusinessHourRates> {

    NoneBusinessHourRates findFirstByCreatedBy(Users users);

//    boolean existsByUniqueStartTimeEndTimeCheckIgnoreCase(String uniqueStartTimeEndTimeCheck);


}
