package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Coupons;
import com.michael_delivery.backend.domain.DeleteRequest;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.CouponsDTO;
import com.michael_delivery.backend.model.DeleteRequestDTO;
import com.michael_delivery.backend.repos.CouponsRepository;
import com.michael_delivery.backend.repos.DeleteRequestRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


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
