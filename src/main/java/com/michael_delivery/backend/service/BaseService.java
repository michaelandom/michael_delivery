package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.BaseModel;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ShareFunction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public abstract class BaseService<E extends BaseModel<ID>, D,ID extends Serializable, R extends JpaRepository<E, ID>> {

    protected final R repository;
    protected final String tableId;

    public BaseService(R repository, String tableId) {
        this.repository = repository;
        this.tableId = tableId;
    }

    /**
     * Abstract method to map Entity to DTO
     * @param entity Source entity
     * @param dto Target DTO
     * @return Mapped DTO
     */
    protected abstract D mapToDTO(E entity, D dto);


    /**
     * Abstract method to map Entity to DTO
     * @param dto Source entity
     * @param entity Target DTO
     * @return Mapped entity
     */
    protected abstract E mapToEntity(D dto, E entity);



    /**
     * Find all entities with pagination and map to DTO
     * @param pageable Pagination information
     * @return Page of DTOs
     */
    public Page<D> findAll(Pageable pageable) {
        pageable = ShareFunction.getPageable(pageable);
        return repository.findAll(pageable)
                .map(entity -> mapToDTO(entity, createDTO()));
    }

    /**
     * Find all entities map to DTO
     * @return List of DTOs
     */
    public List<D> findAll() {
        final List<E> entities = repository.findAll(Sort.by(this.tableId));
        return entities.stream()
                .map(entity -> mapToDTO(entity, createDTO()))
                .toList();
    }

    /**
     * Find one entity map to DTO
     * @return one DTO
     */
    public D get(final ID entityId) {
        return repository.findById(entityId)
                .map(entity -> mapToDTO(entity, createDTO()))
                .orElseThrow(NotFoundException::new);
    }

    /**
     * Update entity from DTO
     * @param entityId ID to convert to entity
     */
    public void update(final ID entityId, final D d) {
        final E entity = repository.findById(entityId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(d, entity);
        repository.save(entity);
    }

    /**
     * Delete entity by id
     * @param entityId ID
     */
    public void delete(final ID entityId) {
        repository.deleteById(entityId);
    }

    /**
     * Create a new entity from DTO
     * @param dto DTO to convert to entity
     * @return ID of the saved entity
     */
    public ID create(final D dto) {
        final E entity = createEntity();
        mapToEntity(dto, entity);
        return repository.save(entity).getId();
    }

    public abstract Page<D> search(final Specification<E> query, final Pageable pageable);

    /**
     * Create a new DTO instance
     * @return New DTO instance
     */
    protected abstract D createDTO();



    /**
     * Create a new Entity instance
     * @return New Entity instance
     */
    protected abstract E createEntity();

}