package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.AppVersion;
import com.michael_delivery.backend.model.AppVersionDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppVersionRepository extends JpaRepository<AppVersion, Integer>, BaseRepository<AppVersionDTO, AppVersion> {

}
