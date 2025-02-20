package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.BillingAddress;
import com.michael_delivery.backend.domain.BussinessAccount;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.BillingAddressDTO;
import com.michael_delivery.backend.repos.BillingAddressRepository;
import com.michael_delivery.backend.repos.BussinessAccountRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BillingAddressService {

    private final BillingAddressRepository billingAddressRepository;
    private final BussinessAccountRepository bussinessAccountRepository;
    private final UsersRepository usersRepository;

    public BillingAddressService(final BillingAddressRepository billingAddressRepository,
            final BussinessAccountRepository bussinessAccountRepository,
            final UsersRepository usersRepository) {
        this.billingAddressRepository = billingAddressRepository;
        this.bussinessAccountRepository = bussinessAccountRepository;
        this.usersRepository = usersRepository;
    }

    public List<BillingAddressDTO> findAll() {
        final List<BillingAddress> billingAddresses = billingAddressRepository.findAll(Sort.by("billingAddressId"));
        return billingAddresses.stream()
                .map(billingAddress -> mapToDTO(billingAddress, new BillingAddressDTO()))
                .toList();
    }

    public BillingAddressDTO get(final Long billingAddressId) {
        return billingAddressRepository.findById(billingAddressId)
                .map(billingAddress -> mapToDTO(billingAddress, new BillingAddressDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BillingAddressDTO billingAddressDTO) {
        final BillingAddress billingAddress = new BillingAddress();
        mapToEntity(billingAddressDTO, billingAddress);
        return billingAddressRepository.save(billingAddress).getBillingAddressId();
    }

    public void update(final Long billingAddressId, final BillingAddressDTO billingAddressDTO) {
        final BillingAddress billingAddress = billingAddressRepository.findById(billingAddressId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(billingAddressDTO, billingAddress);
        billingAddressRepository.save(billingAddress);
    }

    public void delete(final Long billingAddressId) {
        billingAddressRepository.deleteById(billingAddressId);
    }

    private BillingAddressDTO mapToDTO(final BillingAddress billingAddress,
            final BillingAddressDTO billingAddressDTO) {
        billingAddressDTO.setBillingAddressId(billingAddress.getBillingAddressId());
        billingAddressDTO.setBillingEmail(billingAddress.getBillingEmail());
        billingAddressDTO.setBillingStreetAddress(billingAddress.getBillingStreetAddress());
        billingAddressDTO.setBillingStreetAddress2(billingAddress.getBillingStreetAddress2());
        billingAddressDTO.setBillingState(billingAddress.getBillingState());
        billingAddressDTO.setBillingPostcode(billingAddress.getBillingPostcode());
        billingAddressDTO.setBillingSuburb(billingAddress.getBillingSuburb());
        return billingAddressDTO;
    }

    private BillingAddress mapToEntity(final BillingAddressDTO billingAddressDTO,
            final BillingAddress billingAddress) {
        billingAddress.setBillingEmail(billingAddressDTO.getBillingEmail());
        billingAddress.setBillingStreetAddress(billingAddressDTO.getBillingStreetAddress());
        billingAddress.setBillingStreetAddress2(billingAddressDTO.getBillingStreetAddress2());
        billingAddress.setBillingState(billingAddressDTO.getBillingState());
        billingAddress.setBillingPostcode(billingAddressDTO.getBillingPostcode());
        billingAddress.setBillingSuburb(billingAddressDTO.getBillingSuburb());
        return billingAddress;
    }

    public ReferencedWarning getReferencedWarning(final Long billingAddressId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final BillingAddress billingAddress = billingAddressRepository.findById(billingAddressId)
                .orElseThrow(NotFoundException::new);
        final BussinessAccount billingAddressBussinessAccount = bussinessAccountRepository.findFirstByBillingAddress(billingAddress);
        if (billingAddressBussinessAccount != null) {
            referencedWarning.setKey("billingAddress.bussinessAccount.billingAddress.referenced");
            referencedWarning.addParam(billingAddressBussinessAccount.getBussinessAccountId());
            return referencedWarning;
        }
        final Users billingAddressUsers = usersRepository.findFirstByBillingAddress(billingAddress);
        if (billingAddressUsers != null) {
            referencedWarning.setKey("billingAddress.users.billingAddress.referenced");
            referencedWarning.addParam(billingAddressUsers.getUserId());
            return referencedWarning;
        }
        return null;
    }

}
