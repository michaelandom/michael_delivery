package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.SsoProvider;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SsoProviderRepository extends JpaRepository<SsoProvider, Long> {
}
