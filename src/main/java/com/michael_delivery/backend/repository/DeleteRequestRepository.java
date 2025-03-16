package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.DeleteRequest;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.DeleteRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeleteRequestRepository extends JpaRepository<DeleteRequest, Long>,BaseRepository<DeleteRequestDTO,DeleteRequest> {

    DeleteRequest findFirstByUser(Users users);
}
