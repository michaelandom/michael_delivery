package com.michael_delivery.backend.repository;


import com.michael_delivery.backend.model.Advertisement;
import com.michael_delivery.backend.dto.AdvertisementDTO;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AdvertisementRepository  extends JpaRepository<Advertisement,Long>, BaseRepository<AdvertisementDTO,Advertisement> {
}
