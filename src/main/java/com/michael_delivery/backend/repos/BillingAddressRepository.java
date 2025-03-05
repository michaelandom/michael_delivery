package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.AppVersion;
import com.michael_delivery.backend.domain.BillingAddress;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.AppVersionDTO;
import com.michael_delivery.backend.model.BillingAddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long>,BaseRepository<BillingAddressDTO,BillingAddress> {
    BillingAddress findFirstByUser(Users user);
}
