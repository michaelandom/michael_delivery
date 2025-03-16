package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.SizeAndWeightDescriptions;
import com.michael_delivery.backend.dto.SizeAndWeightDescriptionsDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SizeAndWeightDescriptionsRepository extends JpaRepository<SizeAndWeightDescriptions, Long>  ,BaseRepository<SizeAndWeightDescriptionsDTO, SizeAndWeightDescriptions> {

    SizeAndWeightDescriptions findFirstByPreviousAndSizeWeightDescriptionIdNot(
            SizeAndWeightDescriptions sizeAndWeightDescriptions,
            final Long sizeWeightDescriptionId);

   // boolean existsByUniqueSizeCheckIgnoreCase(String uniqueSizeCheck);

}
