package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.AppVersion;
import com.michael_delivery.backend.dto.AppVersionDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppVersionRepository extends JpaRepository<AppVersion, Integer>, BaseRepository<AppVersionDTO, AppVersion> {

}
