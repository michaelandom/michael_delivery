package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.DeleteRequest;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.dto.DeleteRequestDTO;
import com.michael_delivery.backend.repository.DeleteRequestRepository;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class DeleteRequestService extends BaseService<DeleteRequest, DeleteRequestDTO,Long, DeleteRequestRepository>{

    private final DeleteRequestRepository deleteRequestRepository;
    private final UsersRepository usersRepository;

    public DeleteRequestService(final DeleteRequestRepository deleteRequestRepository,
            final UsersRepository usersRepository) {
        super(deleteRequestRepository,"deleteRequestId");
        this.deleteRequestRepository = deleteRequestRepository;
        this.usersRepository = usersRepository;
    }


    @Override
    public Page<DeleteRequestDTO> search(Specification<DeleteRequest> query, Pageable pageable) {
        return this.deleteRequestRepository.findAll(query, pageable);
    }

    @Override
    protected DeleteRequestDTO mapToDTO(final DeleteRequest deleteRequest,
            final DeleteRequestDTO deleteRequestDTO) {
        deleteRequestDTO.setDeleteRequestId(deleteRequest.getDeleteRequestId());
        deleteRequestDTO.setReason(deleteRequest.getReason());
        deleteRequestDTO.setNote(deleteRequest.getNote());
        deleteRequestDTO.setUser(deleteRequest.getUser() == null ? null : deleteRequest.getUser().getUserId());
        return deleteRequestDTO;
    }

    @Override
    protected DeleteRequest mapToEntity(final DeleteRequestDTO deleteRequestDTO,
            final DeleteRequest deleteRequest) {
        deleteRequest.setReason(deleteRequestDTO.getReason());
        deleteRequest.setNote(deleteRequestDTO.getNote());
        final Users user = deleteRequestDTO.getUser() == null ? null : usersRepository.findById(deleteRequestDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        deleteRequest.setUser(user);
        return deleteRequest;
    }

    @Override
    protected DeleteRequestDTO createDTO() {
        return new DeleteRequestDTO();
    }

    @Override
    protected DeleteRequest createEntity() {
        return new DeleteRequest();
    }

}
