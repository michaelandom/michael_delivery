package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Announcement;
import com.michael_delivery.backend.domain.AppVersion;
import com.michael_delivery.backend.model.AnnouncementDTO;
import com.michael_delivery.backend.model.AppVersionDTO;
import com.michael_delivery.backend.repos.AnnouncementRepository;
import com.michael_delivery.backend.repos.AppVersionRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AppVersionService extends BaseService<AppVersion, AppVersionDTO,Integer, AppVersionRepository>{

    private final AppVersionRepository appVersionRepository;

    public AppVersionService(final AppVersionRepository appVersionRepository) {
        super(appVersionRepository,"appVersionId");
       this.appVersionRepository = appVersionRepository;
    }

    @Override
    protected AppVersionDTO mapToDTO(final AppVersion appVersion, final AppVersionDTO appVersionDTO) {
        appVersionDTO.setAppVersionId(appVersion.getAppVersionId());
        appVersionDTO.setAppName(appVersion.getAppName());
        appVersionDTO.setUpdateType(appVersion.getUpdateType());
        appVersionDTO.setVersion(appVersion.getVersion());
        return appVersionDTO;
    }
    @Override
    protected AppVersion mapToEntity(final AppVersionDTO appVersionDTO, final AppVersion appVersion) {
        appVersion.setAppName(appVersionDTO.getAppName());
        appVersion.setUpdateType(appVersionDTO.getUpdateType());
        appVersion.setVersion(appVersionDTO.getVersion());
        return appVersion;
    }

    @Override
    protected AppVersionDTO createDTO() {
        return new AppVersionDTO();
    }

    @Override
    protected AppVersion createEntity() {
        return new AppVersion();
    }

}
