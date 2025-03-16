package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.BussinessAccount;
import com.michael_delivery.backend.dto.BussinessAccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BussinessAccountRepository extends JpaRepository<BussinessAccount, Long>,BaseRepository<BussinessAccountDTO, BussinessAccount>  {


}
