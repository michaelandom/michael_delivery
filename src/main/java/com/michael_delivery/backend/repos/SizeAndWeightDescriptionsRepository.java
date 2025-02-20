package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.SizeAndWeightDescriptions;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SizeAndWeightDescriptionsRepository extends JpaRepository<SizeAndWeightDescriptions, Long> {

    SizeAndWeightDescriptions findFirstByPreviousAndSizeWeightDescriptionIdNot(
            SizeAndWeightDescriptions sizeAndWeightDescriptions,
            final Long sizeWeightDescriptionId);

   // boolean existsByUniqueSizeCheckIgnoreCase(String uniqueSizeCheck);

}
