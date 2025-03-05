package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.UserFavoriteAddress;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.UserFavoriteAddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserFavoriteAddressRepository extends JpaRepository<UserFavoriteAddress, Long>  ,BaseRepository<UserFavoriteAddressDTO,UserFavoriteAddress> {

    UserFavoriteAddress findFirstByUser(Users users);

}
