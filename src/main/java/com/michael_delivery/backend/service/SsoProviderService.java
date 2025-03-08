package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.CancellationRiderRequest;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.SsoProvider;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.SsoProviderDTO;
import com.michael_delivery.backend.repos.CancellationRiderRequestRepository;
import com.michael_delivery.backend.repos.SsoProviderRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SsoProviderService extends BaseService<SsoProvider, SsoProviderDTO,Long, SsoProviderRepository> {

    private final SsoProviderRepository ssoProviderRepository;
    private final UsersRepository usersRepository;

    public SsoProviderService(final SsoProviderRepository ssoProviderRepository,
                              final UsersRepository usersRepository) {
        super(ssoProviderRepository,"ssoProviderId");
        this.ssoProviderRepository = ssoProviderRepository;
        this.usersRepository = usersRepository;
    }


    @Override
    public Page<SsoProviderDTO> search(Specification<SsoProvider> query, Pageable pageable) {
        return this.ssoProviderRepository.findAll(query, pageable);
    }

    @Override
    protected SsoProviderDTO mapToDTO(final SsoProvider ssoProvider,
            final SsoProviderDTO ssoProviderDTO) {
        ssoProviderDTO.setSsoProviderId(ssoProvider.getSsoProviderId());
        ssoProviderDTO.setSsoProvider(ssoProvider.getSsoProvider());
        return ssoProviderDTO;
    }

    @Override
    protected SsoProvider mapToEntity(final SsoProviderDTO ssoProviderDTO,
            final SsoProvider ssoProvider) {
        ssoProvider.setSsoProvider(ssoProviderDTO.getSsoProvider());
        return ssoProvider;
    }

    @Override
    protected SsoProviderDTO createDTO() {
        return new SsoProviderDTO();
    }

    @Override
    protected SsoProvider createEntity() {
        return new SsoProvider();
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
