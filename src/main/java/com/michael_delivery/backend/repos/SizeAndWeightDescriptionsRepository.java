package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.SizeAndWeightDescriptions;
import com.michael_delivery.backend.model.SizeAndWeightDescriptionsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SizeAndWeightDescriptionsRepository extends JpaRepository<SizeAndWeightDescriptions, Long> {

    SizeAndWeightDescriptions findFirstByPreviousAndSizeWeightDescriptionIdNot(
            SizeAndWeightDescriptions sizeAndWeightDescriptions,
            final Long sizeWeightDescriptionId);

   // boolean existsByUniqueSizeCheckIgnoreCase(String uniqueSizeCheck);

    public Page<SizeAndWeightDescriptionsDTO> findAll(Specification<SizeAndWeightDescriptions> spec, Pageable pageable);

}
