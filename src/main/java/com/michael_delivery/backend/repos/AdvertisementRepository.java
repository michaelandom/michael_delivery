package com.michael_delivery.backend.repos;


import com.michael_delivery.backend.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdvertisementRepository  extends JpaRepository<Advertisement,Long> {
}
