package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.SsoProvider;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.SsoProviderDTO;
import com.michael_delivery.backend.repos.SsoProviderRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SsoProviderService {

    private final SsoProviderRepository ssoProviderRepository;
    private final UsersRepository usersRepository;

    public SsoProviderService(final SsoProviderRepository ssoProviderRepository,
                              final UsersRepository usersRepository) {
        this.ssoProviderRepository = ssoProviderRepository;
        this.usersRepository = usersRepository;
    }

    public List<SsoProviderDTO> findAll() {
        final List<SsoProvider> ssoProviders = ssoProviderRepository.findAll(Sort.by("ssoProviderId"));
        return ssoProviders.stream()
                .map(ssoProvider -> mapToDTO(ssoProvider, new SsoProviderDTO()))
                .toList();
    }

    public SsoProviderDTO get(final Long ssoProviderId) {
        return ssoProviderRepository.findById(ssoProviderId)
                .map(ssoProvider -> mapToDTO(ssoProvider, new SsoProviderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SsoProviderDTO ssoProviderDTO) {
        final SsoProvider ssoProvider = new SsoProvider();
        mapToEntity(ssoProviderDTO, ssoProvider);
        return ssoProviderRepository.save(ssoProvider).getSsoProviderId();
    }

    public void update(final Long ssoProviderId, final SsoProviderDTO ssoProviderDTO) {
        final SsoProvider ssoProvider = ssoProviderRepository.findById(ssoProviderId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(ssoProviderDTO, ssoProvider);
        ssoProviderRepository.save(ssoProvider);
    }

    public void delete(final Long ssoProviderId) {
        ssoProviderRepository.deleteById(ssoProviderId);
    }

    private SsoProviderDTO mapToDTO(final SsoProvider ssoProvider,
            final SsoProviderDTO ssoProviderDTO) {
        ssoProviderDTO.setSsoProviderId(ssoProvider.getSsoProviderId());
        ssoProviderDTO.setSsoProvider(ssoProvider.getSsoProvider());
        return ssoProviderDTO;
    }

    private SsoProvider mapToEntity(final SsoProviderDTO ssoProviderDTO,
            final SsoProvider ssoProvider) {
        ssoProvider.setSsoProvider(ssoProviderDTO.getSsoProvider());
        return ssoProvider;
    }

    public ReferencedWarning getReferencedWarning(final Long ssoProviderId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SsoProvider ssoProvider = ssoProviderRepository.findById(ssoProviderId)
                .orElseThrow(NotFoundException::new);
        final Users ssoProviderUsers = usersRepository.findFirstBySsoProvider(ssoProvider);
        if (ssoProviderUsers != null) {
            referencedWarning.setKey("ssoProvider.users.ssoProvider.referenced");
            referencedWarning.addParam(ssoProviderUsers.getUserId());
            return referencedWarning;
        }
        return null;
    }

}
