package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.model.GroupsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupsRepository extends JpaRepository<Groups, Long> ,BaseRepository<GroupsDTO,Groups>{
    public Page<GroupsDTO> findAll(Specification<Groups> spec, Pageable pageable);

}
