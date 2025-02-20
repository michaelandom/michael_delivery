package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.DeleteRequest;
import com.michael_delivery.backend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeleteRequestRepository extends JpaRepository<DeleteRequest, Long> {

    DeleteRequest findFirstByUser(Users users);

}
