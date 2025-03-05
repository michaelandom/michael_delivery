package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.Item;
import com.michael_delivery.backend.domain.SizeAndWeightDescriptions;
import com.michael_delivery.backend.model.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findFirstByDestination(Destination destination);

    Item findFirstBySizeWeightDescription(SizeAndWeightDescriptions sizeAndWeightDescriptions);

    public Page<ItemDTO> findAll(Specification<Item> spec, Pageable pageable);

}
