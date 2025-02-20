package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.Item;
import com.michael_delivery.backend.domain.SizeAndWeightDescriptions;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findFirstByDestination(Destination destination);

    Item findFirstBySizeWeightDescription(SizeAndWeightDescriptions sizeAndWeightDescriptions);

}
