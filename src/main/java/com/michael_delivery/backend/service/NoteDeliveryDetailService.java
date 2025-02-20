package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.DeliveryDetail;
import com.michael_delivery.backend.domain.NoteDeliveryDetail;
import com.michael_delivery.backend.model.NoteDeliveryDetailDTO;
import com.michael_delivery.backend.repos.DeliveryDetailRepository;
import com.michael_delivery.backend.repos.NoteDeliveryDetailRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoteDeliveryDetailService {

    private final NoteDeliveryDetailRepository noteDeliveryDetailRepository;
    private final DeliveryDetailRepository deliveryDetailRepository;

    public NoteDeliveryDetailService(
            final NoteDeliveryDetailRepository noteDeliveryDetailRepository,
            final DeliveryDetailRepository deliveryDetailRepository) {
        this.noteDeliveryDetailRepository = noteDeliveryDetailRepository;
        this.deliveryDetailRepository = deliveryDetailRepository;
    }

    public List<NoteDeliveryDetailDTO> findAll() {
        final List<NoteDeliveryDetail> noteDeliveryDetails = noteDeliveryDetailRepository.findAll(Sort.by("noteDeliveryDetailId"));
        return noteDeliveryDetails.stream()
                .map(noteDeliveryDetail -> mapToDTO(noteDeliveryDetail, new NoteDeliveryDetailDTO()))
                .toList();
    }

    public NoteDeliveryDetailDTO get(final Long noteDeliveryDetailId) {
        return noteDeliveryDetailRepository.findById(noteDeliveryDetailId)
                .map(noteDeliveryDetail -> mapToDTO(noteDeliveryDetail, new NoteDeliveryDetailDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final NoteDeliveryDetailDTO noteDeliveryDetailDTO) {
        final NoteDeliveryDetail noteDeliveryDetail = new NoteDeliveryDetail();
        mapToEntity(noteDeliveryDetailDTO, noteDeliveryDetail);
        return noteDeliveryDetailRepository.save(noteDeliveryDetail).getNoteDeliveryDetailId();
    }

    public void update(final Long noteDeliveryDetailId,
            final NoteDeliveryDetailDTO noteDeliveryDetailDTO) {
        final NoteDeliveryDetail noteDeliveryDetail = noteDeliveryDetailRepository.findById(noteDeliveryDetailId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(noteDeliveryDetailDTO, noteDeliveryDetail);
        noteDeliveryDetailRepository.save(noteDeliveryDetail);
    }

    public void delete(final Long noteDeliveryDetailId) {
        noteDeliveryDetailRepository.deleteById(noteDeliveryDetailId);
    }

    private NoteDeliveryDetailDTO mapToDTO(final NoteDeliveryDetail noteDeliveryDetail,
            final NoteDeliveryDetailDTO noteDeliveryDetailDTO) {
        noteDeliveryDetailDTO.setNoteDeliveryDetailId(noteDeliveryDetail.getNoteDeliveryDetailId());
        noteDeliveryDetailDTO.setNote(noteDeliveryDetail.getNote());
        noteDeliveryDetailDTO.setPhotoUrls(noteDeliveryDetail.getPhotoUrls());
        noteDeliveryDetailDTO.setDeliveryDetail(noteDeliveryDetail.getDeliveryDetail() == null ? null : noteDeliveryDetail.getDeliveryDetail().getDeliveryDetailId());
        return noteDeliveryDetailDTO;
    }

    private NoteDeliveryDetail mapToEntity(final NoteDeliveryDetailDTO noteDeliveryDetailDTO,
            final NoteDeliveryDetail noteDeliveryDetail) {
        noteDeliveryDetail.setNote(noteDeliveryDetailDTO.getNote());
        noteDeliveryDetail.setPhotoUrls(noteDeliveryDetailDTO.getPhotoUrls());
        final DeliveryDetail deliveryDetail = noteDeliveryDetailDTO.getDeliveryDetail() == null ? null : deliveryDetailRepository.findById(noteDeliveryDetailDTO.getDeliveryDetail())
                .orElseThrow(() -> new NotFoundException("deliveryDetail not found"));
        noteDeliveryDetail.setDeliveryDetail(deliveryDetail);
        return noteDeliveryDetail;
    }

}
