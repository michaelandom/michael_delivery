package com.michael_delivery.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;


public abstract class MongoBaseService<T, ID> {

    protected final MongoRepository<T, ID> repository;

    protected MongoBaseService(MongoRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T save(T entity) {
        return repository.save(entity);
    }


    public List<T> saveAll(List<T> entities) {
        return repository.saveAll(entities);
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Page<T> findAll(Pageable pageable) {
        if (repository != null) {
            return ((PagingAndSortingRepository<T, ID>) repository).findAll(pageable);
        }
        throw new UnsupportedOperationException("Repository does not support pagination");
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }


    public void deleteAll() {
        repository.deleteAll();
    }


    public long count() {
        return repository.count();
    }


    public boolean existsById(ID id) {
        return repository.existsById(id);
    }
}