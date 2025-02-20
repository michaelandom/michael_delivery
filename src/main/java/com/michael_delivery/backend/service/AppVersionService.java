package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.AppVersion;
import com.michael_delivery.backend.model.AppVersionDTO;
import com.michael_delivery.backend.repos.AppVersionRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AppVersionService {

    private final AppVersionRepository appVersionRepository;

    public AppVersionService(final AppVersionRepository appVersionRepository) {
        this.appVersionRepository = appVersionRepository;
    }

    public List<AppVersionDTO> findAll() {
        final List<AppVersion> appVersions = appVersionRepository.findAll(Sort.by("appVersionId"));
        return appVersions.stream()
                .map(appVersion -> mapToDTO(appVersion, new AppVersionDTO()))
                .toList();
    }

    public AppVersionDTO get(final Integer appVersionId) {
        return appVersionRepository.findById(appVersionId)
                .map(appVersion -> mapToDTO(appVersion, new AppVersionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final AppVersionDTO appVersionDTO) {
        final AppVersion appVersion = new AppVersion();
        mapToEntity(appVersionDTO, appVersion);
        return appVersionRepository.save(appVersion).getAppVersionId();
    }

    public void update(final Integer appVersionId, final AppVersionDTO appVersionDTO) {
        final AppVersion appVersion = appVersionRepository.findById(appVersionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(appVersionDTO, appVersion);
        appVersionRepository.save(appVersion);
    }

    public void delete(final Integer appVersionId) {
        appVersionRepository.deleteById(appVersionId);
    }

    private AppVersionDTO mapToDTO(final AppVersion appVersion, final AppVersionDTO appVersionDTO) {
        appVersionDTO.setAppVersionId(appVersion.getAppVersionId());
        appVersionDTO.setAppName(appVersion.getAppName());
        appVersionDTO.setUpdateType(appVersion.getUpdateType());
        appVersionDTO.setVersion(appVersion.getVersion());
        return appVersionDTO;
    }

    private AppVersion mapToEntity(final AppVersionDTO appVersionDTO, final AppVersion appVersion) {
        appVersion.setAppName(appVersionDTO.getAppName());
        appVersion.setUpdateType(appVersionDTO.getUpdateType());
        appVersion.setVersion(appVersionDTO.getVersion());
        return appVersion;
    }

}
