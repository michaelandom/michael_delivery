package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.BillingAddress;
import com.michael_delivery.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {
    BillingAddress findFirstByUser(Users user);
}
