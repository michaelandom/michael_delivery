package com.michael_delivery.backend.specification;


import com.michael_delivery.backend.domain.Advertisement;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class AdvertisementSpecification {

    public static Specification<Advertisement> titleContains(String title) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(title)) {
                return criteriaBuilder.like(root.get("title"), "%" + title + "%");
            }
            return null;
        };
    }

    public static Specification<Advertisement> contentContains(String content) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(content)) {
                return criteriaBuilder.like(root.get("content"), "%" + content + "%");
            }
            return null;
        };
    }
}