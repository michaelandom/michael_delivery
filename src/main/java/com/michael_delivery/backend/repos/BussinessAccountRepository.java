package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.BillingAddress;
import com.michael_delivery.backend.domain.BussinessAccount;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BussinessAccountRepository extends JpaRepository<BussinessAccount, Long> {

    BussinessAccount findFirstByBillingAddress(BillingAddress billingAddress);

}
