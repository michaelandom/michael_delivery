package com.michael_delivery.backend.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class ShareFunction {
    public static Pageable getPageable(Pageable pageable) {
        System.out.println("getPageable: " + pageable);
        if (pageable.getSort().isEmpty()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        }
        System.out.println("PageRequest: " + pageable);
        return pageable;
    }
}
