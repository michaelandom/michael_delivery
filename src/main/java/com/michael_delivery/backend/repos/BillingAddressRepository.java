package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {
}
