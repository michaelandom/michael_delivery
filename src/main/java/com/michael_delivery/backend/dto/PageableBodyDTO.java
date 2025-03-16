package com.michael_delivery.backend.dto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageableBodyDTO {

    public int page;
    public int size;
    public String[] sortBy;


    public PageableBodyDTO(int page, int size, String[] sortBy) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
    }

    public Pageable getPageable() {
        Sort sort = buildSort(sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return pageable;
    }

    private Sort buildSort(String[] sortBy) {
        System.out.println("buildSort " + Arrays.toString(sortBy));
        if (sortBy == null || sortBy.length == 0) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (String sort : sortBy) {
            String[] sortParts = sort.split(":");
            if (sortParts.length == 2) {
                String field = sortParts[0];
                String direction = sortParts[1].toLowerCase();
                if ("desc".equals(direction)) {
                    orders.add(Sort.Order.desc(field));
                } else {
                    orders.add(Sort.Order.asc(field));
                }
            }
        }
        return Sort.by(orders);
    }

}
