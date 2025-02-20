package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.UserFavoriteAddress;
import com.michael_delivery.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserFavoriteAddressRepository extends JpaRepository<UserFavoriteAddress, Long> {

    UserFavoriteAddress findFirstByUser(Users users);

}
