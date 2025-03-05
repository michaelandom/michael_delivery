package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.RidersDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RidersRepository extends JpaRepository<Riders, Long> {

    Riders findFirstByUser(Users users);

    public Page<RidersDTO> findAll(Specification<Riders> spec, Pageable pageable);

}
