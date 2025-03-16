package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.SsoProvider;
import com.michael_delivery.backend.dto.SsoProviderDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SsoProviderRepository extends JpaRepository<SsoProvider, Long>   ,BaseRepository<SsoProviderDTO,SsoProvider>{

}
