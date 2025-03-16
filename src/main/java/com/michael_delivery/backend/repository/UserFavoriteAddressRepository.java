package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.UserFavoriteAddress;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.UserFavoriteAddressDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserFavoriteAddressRepository extends JpaRepository<UserFavoriteAddress, Long>  ,BaseRepository<UserFavoriteAddressDTO,UserFavoriteAddress> {

    UserFavoriteAddress findFirstByUser(Users users);

}
