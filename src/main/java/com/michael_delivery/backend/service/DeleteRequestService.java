package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.DeleteRequest;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.DeleteRequestDTO;
import com.michael_delivery.backend.repos.DeleteRequestRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DeleteRequestService {

    private final DeleteRequestRepository deleteRequestRepository;
    private final UsersRepository usersRepository;

    public DeleteRequestService(final DeleteRequestRepository deleteRequestRepository,
            final UsersRepository usersRepository) {
        this.deleteRequestRepository = deleteRequestRepository;
        this.usersRepository = usersRepository;
    }

    public List<DeleteRequestDTO> findAll() {
        final List<DeleteRequest> deleteRequests = deleteRequestRepository.findAll(Sort.by("deleteRequestId"));
        return deleteRequests.stream()
                .map(deleteRequest -> mapToDTO(deleteRequest, new DeleteRequestDTO()))
                .toList();
    }

    public DeleteRequestDTO get(final Long deleteRequestId) {
        return deleteRequestRepository.findById(deleteRequestId)
                .map(deleteRequest -> mapToDTO(deleteRequest, new DeleteRequestDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DeleteRequestDTO deleteRequestDTO) {
        final DeleteRequest deleteRequest = new DeleteRequest();
        mapToEntity(deleteRequestDTO, deleteRequest);
        return deleteRequestRepository.save(deleteRequest).getDeleteRequestId();
    }

    public void update(final Long deleteRequestId, final DeleteRequestDTO deleteRequestDTO) {
        final DeleteRequest deleteRequest = deleteRequestRepository.findById(deleteRequestId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(deleteRequestDTO, deleteRequest);
        deleteRequestRepository.save(deleteRequest);
    }

    public void delete(final Long deleteRequestId) {
        deleteRequestRepository.deleteById(deleteRequestId);
    }

    private DeleteRequestDTO mapToDTO(final DeleteRequest deleteRequest,
            final DeleteRequestDTO deleteRequestDTO) {
        deleteRequestDTO.setDeleteRequestId(deleteRequest.getDeleteRequestId());
        deleteRequestDTO.setReason(deleteRequest.getReason());
        deleteRequestDTO.setNote(deleteRequest.getNote());
        deleteRequestDTO.setUser(deleteRequest.getUser() == null ? null : deleteRequest.getUser().getUserId());
        return deleteRequestDTO;
    }

    private DeleteRequest mapToEntity(final DeleteRequestDTO deleteRequestDTO,
            final DeleteRequest deleteRequest) {
        deleteRequest.setReason(deleteRequestDTO.getReason());
        deleteRequest.setNote(deleteRequestDTO.getNote());
        final Users user = deleteRequestDTO.getUser() == null ? null : usersRepository.findById(deleteRequestDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        deleteRequest.setUser(user);
        return deleteRequest;
    }

}
