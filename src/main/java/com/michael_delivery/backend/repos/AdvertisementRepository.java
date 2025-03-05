package com.michael_delivery.backend.repos;


import com.michael_delivery.backend.domain.Advertisement;
import com.michael_delivery.backend.model.AdvertisementDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository  extends JpaRepository<Advertisement,Long>, BaseRepository<AdvertisementDTO,Advertisement> {
}
