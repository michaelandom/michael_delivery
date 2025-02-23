package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.BillingAddress;
import com.michael_delivery.backend.domain.BussinessAccount;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.BussinessAccountDTO;
import com.michael_delivery.backend.repos.BillingAddressRepository;
import com.michael_delivery.backend.repos.BussinessAccountRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BussinessAccountService {

    private final BussinessAccountRepository bussinessAccountRepository;
    private final BillingAddressRepository billingAddressRepository;
    private final UsersRepository usersRepository;

    public BussinessAccountService(final BussinessAccountRepository bussinessAccountRepository,
            final BillingAddressRepository billingAddressRepository,
            final UsersRepository usersRepository) {
        this.bussinessAccountRepository = bussinessAccountRepository;
        this.billingAddressRepository = billingAddressRepository;
        this.usersRepository = usersRepository;
    }

    public List<BussinessAccountDTO> findAll() {
        final List<BussinessAccount> bussinessAccounts = bussinessAccountRepository.findAll(Sort.by("bussinessAccountId"));
        return bussinessAccounts.stream()
                .map(bussinessAccount -> mapToDTO(bussinessAccount, new BussinessAccountDTO()))
                .toList();
    }

    public BussinessAccountDTO get(final Long bussinessAccountId) {
        return bussinessAccountRepository.findById(bussinessAccountId)
                .map(bussinessAccount -> mapToDTO(bussinessAccount, new BussinessAccountDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BussinessAccountDTO bussinessAccountDTO) {
        final BussinessAccount bussinessAccount = new BussinessAccount();
        mapToEntity(bussinessAccountDTO, bussinessAccount);
        return bussinessAccountRepository.save(bussinessAccount).getBussinessAccountId();
    }

    public void update(final Long bussinessAccountId,
            final BussinessAccountDTO bussinessAccountDTO) {
        final BussinessAccount bussinessAccount = bussinessAccountRepository.findById(bussinessAccountId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bussinessAccountDTO, bussinessAccount);
        bussinessAccountRepository.save(bussinessAccount);
    }

    public void delete(final Long bussinessAccountId) {
        bussinessAccountRepository.deleteById(bussinessAccountId);
    }

    private BussinessAccountDTO mapToDTO(final BussinessAccount bussinessAccount,
            final BussinessAccountDTO bussinessAccountDTO) {
        bussinessAccountDTO.setBussinessAccountId(bussinessAccount.getBussinessAccountId());
        bussinessAccountDTO.setCompanyAbn(bussinessAccount.getCompanyAbn());
        bussinessAccountDTO.setCompanyName(bussinessAccount.getCompanyName());
        bussinessAccountDTO.setLogoUrl(bussinessAccount.getLogoUrl());
        bussinessAccountDTO.setIsActive(bussinessAccount.getIsActive());
//        bussinessAccountDTO.setBillingAddress(bussinessAccount.getBillingAddress() == null ? null : bussinessAccount.getBillingAddress().getBillingAddressId());
        return bussinessAccountDTO;
    }

    private BussinessAccount mapToEntity(final BussinessAccountDTO bussinessAccountDTO,
            final BussinessAccount bussinessAccount) {
        bussinessAccount.setCompanyAbn(bussinessAccountDTO.getCompanyAbn());
        bussinessAccount.setCompanyName(bussinessAccountDTO.getCompanyName());
        bussinessAccount.setLogoUrl(bussinessAccountDTO.getLogoUrl());
        bussinessAccount.setIsActive(bussinessAccountDTO.getIsActive());
        final BillingAddress billingAddress = bussinessAccountDTO.getBillingAddress() == null ? null : billingAddressRepository.findById(bussinessAccountDTO.getBillingAddress())
                .orElseThrow(() -> new NotFoundException("billingAddress not found"));
//        bussinessAccount.setBillingAddress(billingAddress);
        return bussinessAccount;
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
