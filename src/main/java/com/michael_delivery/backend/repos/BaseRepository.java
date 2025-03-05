package com.michael_delivery.backend.repos;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface BaseRepository<D,E> {
     public Page<D> findAll(Specification<E> spec, Pageable pageable);
}
