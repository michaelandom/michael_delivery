package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Destination;
import com.michael_delivery.backend.model.Item;
import com.michael_delivery.backend.model.SizeAndWeightDescriptions;
import com.michael_delivery.backend.dto.ItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<Item, Long> ,BaseRepository<ItemDTO,Item>{

    Item findFirstByDestination(Destination destination);

    Item findFirstBySizeWeightDescription(SizeAndWeightDescriptions sizeAndWeightDescriptions);

}
