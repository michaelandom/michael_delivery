package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.*;
import com.michael_delivery.backend.model.AdvertisementDTO;
import com.michael_delivery.backend.model.AnnouncementDTO;
import com.michael_delivery.backend.model.BillingAddressDTO;
import com.michael_delivery.backend.repos.AnnouncementRepository;
import com.michael_delivery.backend.repos.BillingAddressRepository;
import com.michael_delivery.backend.repos.BussinessAccountRepository;
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
public class BillingAddressService extends BaseService<BillingAddress, BillingAddressDTO,Long, BillingAddressRepository> {

    private final BillingAddressRepository billingAddressRepository;
    private final BussinessAccountRepository bussinessAccountRepository;
    private final UsersRepository usersRepository;

    public BillingAddressService(final BillingAddressRepository billingAddressRepository,
            final BussinessAccountRepository bussinessAccountRepository,
            final UsersRepository usersRepository) {
        super(billingAddressRepository,"billingAddressId");
        this.billingAddressRepository = billingAddressRepository;
        this.bussinessAccountRepository = bussinessAccountRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Page<BillingAddressDTO> search(Specification<BillingAddress> query, Pageable pageable) {
        return this.billingAddressRepository.findAll(query, pageable);
    }

    @Override
    protected BillingAddressDTO mapToDTO(final BillingAddress billingAddress,
            final BillingAddressDTO billingAddressDTO) {
        billingAddressDTO.setBillingAddressId(billingAddress.getBillingAddressId());
        billingAddressDTO.setBillingEmail(billingAddress.getBillingEmail());
        billingAddressDTO.setBillingStreetAddress(billingAddress.getBillingStreetAddress());
        billingAddressDTO.setBillingStreetAddress2(billingAddress.getBillingStreetAddress2());
        billingAddressDTO.setBillingPostcode(billingAddress.getBillingPostcode());
        billingAddressDTO.setBillingSuburb(billingAddress.getBillingSuburb());
        billingAddressDTO.setUserId(billingAddress.getUsers() == null ? null : billingAddress.getUsers().getUserId());

        return billingAddressDTO;
    }
    @Override
    protected BillingAddress mapToEntity(final BillingAddressDTO billingAddressDTO,
            final BillingAddress billingAddress) {
        billingAddress.setBillingEmail(billingAddressDTO.getBillingEmail());
        billingAddress.setBillingStreetAddress(billingAddressDTO.getBillingStreetAddress());
        billingAddress.setBillingStreetAddress2(billingAddressDTO.getBillingStreetAddress2());
        billingAddress.setBillingPostcode(billingAddressDTO.getBillingPostcode());
        billingAddress.setBillingSuburb(billingAddressDTO.getBillingSuburb());
        final Users user = billingAddressDTO.getUserId() == null ? null : usersRepository.findById(billingAddressDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("User Not Found")
                );
        billingAddress.setUsers(user);
        return billingAddress;
    }

    @Override
    protected BillingAddressDTO createDTO() {
        return new BillingAddressDTO();
    }

    @Override
    protected BillingAddress createEntity() {
        return new BillingAddress();
    }


}
