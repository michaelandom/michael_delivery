package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.AppVersion;
import com.michael_delivery.backend.dto.AppVersionDTO;
import com.michael_delivery.backend.repository.AppVersionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class AppVersionService extends BaseService<AppVersion, AppVersionDTO,Integer, AppVersionRepository>{

    private final AppVersionRepository appVersionRepository;

    public AppVersionService(final AppVersionRepository appVersionRepository) {
        super(appVersionRepository,"appVersionId");
       this.appVersionRepository = appVersionRepository;
    }

    @Override
    public Page<AppVersionDTO> search(Specification<AppVersion> query, Pageable pageable) {
        return  this.appVersionRepository.findAll(query, pageable);
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
