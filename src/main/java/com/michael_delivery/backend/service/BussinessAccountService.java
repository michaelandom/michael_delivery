package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.BussinessAccount;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.BussinessAccountDTO;
import com.michael_delivery.backend.repository.BillingAddressRepository;
import com.michael_delivery.backend.repository.BussinessAccountRepository;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class BussinessAccountService extends BaseService<BussinessAccount, BussinessAccountDTO,Long, BussinessAccountRepository>  {

    private final BussinessAccountRepository bussinessAccountRepository;
    private final BillingAddressRepository billingAddressRepository;
    private final UsersRepository usersRepository;

    public BussinessAccountService(final BussinessAccountRepository bussinessAccountRepository,
            final BillingAddressRepository billingAddressRepository,
            final UsersRepository usersRepository) {
        super(bussinessAccountRepository,"bussinessAccountId");

        this.bussinessAccountRepository = bussinessAccountRepository;
        this.billingAddressRepository = billingAddressRepository;
        this.usersRepository = usersRepository;
    }
    @Override
    protected BussinessAccountDTO mapToDTO(final BussinessAccount bussinessAccount,
            final BussinessAccountDTO bussinessAccountDTO) {
        bussinessAccountDTO.setBussinessAccountId(bussinessAccount.getBussinessAccountId());
        bussinessAccountDTO.setCompanyAbn(bussinessAccount.getCompanyAbn());
        bussinessAccountDTO.setCompanyName(bussinessAccount.getCompanyName());
        bussinessAccountDTO.setLogoUrl(bussinessAccount.getLogoUrl());
        bussinessAccountDTO.setIsActive(bussinessAccount.getIsActive());
        return bussinessAccountDTO;
    }

    @Override
    public Page<BussinessAccountDTO> search(Specification<BussinessAccount> query, Pageable pageable) {
        return this.bussinessAccountRepository.findAll(query, pageable);
    }
    @Override
    protected BussinessAccount mapToEntity(final BussinessAccountDTO bussinessAccountDTO,
            final BussinessAccount bussinessAccount) {
        bussinessAccount.setCompanyAbn(bussinessAccountDTO.getCompanyAbn());
        bussinessAccount.setCompanyName(bussinessAccountDTO.getCompanyName());
        bussinessAccount.setLogoUrl(bussinessAccountDTO.getLogoUrl());
        bussinessAccount.setIsActive(bussinessAccountDTO.getIsActive());
        return bussinessAccount;
    }

    @Override
    protected BussinessAccountDTO createDTO() {
        return new BussinessAccountDTO();
    }

    @Override
    protected BussinessAccount createEntity() {
        return new BussinessAccount();
    }

    public ReferencedWarning getReferencedWarning(final Long bussinessAccountId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final BussinessAccount bussinessAccount = bussinessAccountRepository.findById(bussinessAccountId)
                .orElseThrow(NotFoundException::new);
        final Users bussinessAccountUsers = usersRepository.findFirstByBussinessAccount(bussinessAccount);
        if (bussinessAccountUsers != null) {
            referencedWarning.setKey("bussinessAccount.users.bussinessAccount.referenced");
            referencedWarning.addParam(bussinessAccountUsers.getUserId());
            return referencedWarning;
        }
        return null;
    }

}
