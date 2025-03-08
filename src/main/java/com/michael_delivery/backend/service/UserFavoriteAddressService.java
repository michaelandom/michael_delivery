package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.CancellationRiderRequest;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.UserFavoriteAddress;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.UserFavoriteAddressDTO;
import com.michael_delivery.backend.repos.CancellationRiderRequestRepository;
import com.michael_delivery.backend.repos.UserFavoriteAddressRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserFavoriteAddressService extends BaseService<UserFavoriteAddress, UserFavoriteAddressDTO,Long, UserFavoriteAddressRepository> {

    private final UserFavoriteAddressRepository userFavoriteAddressRepository;
    private final UsersRepository usersRepository;

    public UserFavoriteAddressService(
            final UserFavoriteAddressRepository userFavoriteAddressRepository,
            final UsersRepository usersRepository) {
        super(userFavoriteAddressRepository,"favoriteAddressId");
        this.userFavoriteAddressRepository = userFavoriteAddressRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Page<UserFavoriteAddressDTO> search(Specification<UserFavoriteAddress> query, Pageable pageable) {
        return this.userFavoriteAddressRepository.findAll(query, pageable);
    }

    @Override
    protected UserFavoriteAddressDTO mapToDTO(final UserFavoriteAddress userFavoriteAddress,
            final UserFavoriteAddressDTO userFavoriteAddressDTO) {
        userFavoriteAddressDTO.setFavoriteAddressId(userFavoriteAddress.getFavoriteAddressId());
        userFavoriteAddressDTO.setLongAddress(userFavoriteAddress.getLongAddress());
        userFavoriteAddressDTO.setShortAddress(userFavoriteAddress.getShortAddress());
        userFavoriteAddressDTO.setCustomAddress(userFavoriteAddress.getCustomAddress());
        userFavoriteAddressDTO.setNickName(userFavoriteAddress.getNickName());
        userFavoriteAddressDTO.setLatitude(userFavoriteAddress.getLatitude());
        userFavoriteAddressDTO.setLongitude(userFavoriteAddress.getLongitude());
        userFavoriteAddressDTO.setAddressType(userFavoriteAddress.getAddressType());
        userFavoriteAddressDTO.setUser(userFavoriteAddress.getUser() == null ? null : userFavoriteAddress.getUser().getUserId());
        return userFavoriteAddressDTO;
    }

    @Override
    protected UserFavoriteAddress mapToEntity(final UserFavoriteAddressDTO userFavoriteAddressDTO,
            final UserFavoriteAddress userFavoriteAddress) {
        userFavoriteAddress.setLongAddress(userFavoriteAddressDTO.getLongAddress());
        userFavoriteAddress.setShortAddress(userFavoriteAddressDTO.getShortAddress());
        userFavoriteAddress.setCustomAddress(userFavoriteAddressDTO.getCustomAddress());
        userFavoriteAddress.setNickName(userFavoriteAddressDTO.getNickName());
        userFavoriteAddress.setLatitude(userFavoriteAddressDTO.getLatitude());
        userFavoriteAddress.setLongitude(userFavoriteAddressDTO.getLongitude());
        userFavoriteAddress.setAddressType(userFavoriteAddressDTO.getAddressType());
        final Users user = userFavoriteAddressDTO.getUser() == null ? null : usersRepository.findById(userFavoriteAddressDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        userFavoriteAddress.setUser(user);
        return userFavoriteAddress;
    }

    @Override
    protected UserFavoriteAddressDTO createDTO() {
        return new UserFavoriteAddressDTO();
    }

    @Override
    protected UserFavoriteAddress createEntity() {
        return new UserFavoriteAddress();
    }

}
