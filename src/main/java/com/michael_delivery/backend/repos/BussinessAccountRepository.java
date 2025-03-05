package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.BussinessAccount;
import com.michael_delivery.backend.model.BussinessAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BussinessAccountRepository extends JpaRepository<BussinessAccount, Long>,BaseRepository<BussinessAccountDTO, BussinessAccount>  {


}
