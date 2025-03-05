package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.SsoProvider;
import com.michael_delivery.backend.model.SsoProviderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SsoProviderRepository extends JpaRepository<SsoProvider, Long>   ,BaseRepository<SsoProviderDTO,SsoProvider>{

}
