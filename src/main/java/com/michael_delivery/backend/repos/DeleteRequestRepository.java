package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.DeleteRequest;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.DeleteRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeleteRequestRepository extends JpaRepository<DeleteRequest, Long>,BaseRepository<DeleteRequestDTO,DeleteRequest> {

    DeleteRequest findFirstByUser(Users users);
}
