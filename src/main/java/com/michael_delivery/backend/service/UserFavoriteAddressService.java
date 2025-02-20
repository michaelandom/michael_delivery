package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.UserFavoriteAddress;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.UserFavoriteAddressDTO;
import com.michael_delivery.backend.repos.UserFavoriteAddressRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserFavoriteAddressService {

    private final UserFavoriteAddressRepository userFavoriteAddressRepository;
    private final UsersRepository usersRepository;

    public UserFavoriteAddressService(
            final UserFavoriteAddressRepository userFavoriteAddressRepository,
            final UsersRepository usersRepository) {
        this.userFavoriteAddressRepository = userFavoriteAddressRepository;
        this.usersRepository = usersRepository;
    }

    public List<UserFavoriteAddressDTO> findAll() {
        final List<UserFavoriteAddress> userFavoriteAddresses = userFavoriteAddressRepository.findAll(Sort.by("favoriteAddressId"));
        return userFavoriteAddresses.stream()
                .map(userFavoriteAddress -> mapToDTO(userFavoriteAddress, new UserFavoriteAddressDTO()))
                .toList();
    }

    public UserFavoriteAddressDTO get(final Long favoriteAddressId) {
        return userFavoriteAddressRepository.findById(favoriteAddressId)
                .map(userFavoriteAddress -> mapToDTO(userFavoriteAddress, new UserFavoriteAddressDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserFavoriteAddressDTO userFavoriteAddressDTO) {
        final UserFavoriteAddress userFavoriteAddress = new UserFavoriteAddress();
        mapToEntity(userFavoriteAddressDTO, userFavoriteAddress);
        return userFavoriteAddressRepository.save(userFavoriteAddress).getFavoriteAddressId();
    }

    public void update(final Long favoriteAddressId,
            final UserFavoriteAddressDTO userFavoriteAddressDTO) {
        final UserFavoriteAddress userFavoriteAddress = userFavoriteAddressRepository.findById(favoriteAddressId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userFavoriteAddressDTO, userFavoriteAddress);
        userFavoriteAddressRepository.save(userFavoriteAddress);
    }

    public void delete(final Long favoriteAddressId) {
        userFavoriteAddressRepository.deleteById(favoriteAddressId);
    }

    private UserFavoriteAddressDTO mapToDTO(final UserFavoriteAddress userFavoriteAddress,
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

    private UserFavoriteAddress mapToEntity(final UserFavoriteAddressDTO userFavoriteAddressDTO,
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

}
