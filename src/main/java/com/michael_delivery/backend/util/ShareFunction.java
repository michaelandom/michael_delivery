package com.michael_delivery.backend.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class ShareFunction {
    public static Pageable getPageable(Pageable pageable) {
        if (pageable.getSort().isSorted() ) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        }
        return pageable;
    }
}
