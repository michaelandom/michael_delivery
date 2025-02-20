package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupsRepository extends JpaRepository<Groups, Long> {
}
