package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.BillingAddress;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.BillingAddressDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long>,BaseRepository<BillingAddressDTO,BillingAddress> {
    BillingAddress findFirstByUser(Users user);
}
