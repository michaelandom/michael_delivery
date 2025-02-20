package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppVersionRepository extends JpaRepository<AppVersion, Integer> {
}
