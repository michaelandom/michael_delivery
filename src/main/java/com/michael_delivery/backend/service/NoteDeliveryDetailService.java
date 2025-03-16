package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.DeliveryDetail;
import com.michael_delivery.backend.model.NoteDeliveryDetail;
import com.michael_delivery.backend.dto.NoteDeliveryDetailDTO;
import com.michael_delivery.backend.repository.DeliveryDetailRepository;
import com.michael_delivery.backend.repository.NoteDeliveryDetailRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class NoteDeliveryDetailService extends BaseService<NoteDeliveryDetail, NoteDeliveryDetailDTO,Long, NoteDeliveryDetailRepository>{

    private final NoteDeliveryDetailRepository noteDeliveryDetailRepository;
    private final DeliveryDetailRepository deliveryDetailRepository;

    public NoteDeliveryDetailService(
            final NoteDeliveryDetailRepository noteDeliveryDetailRepository,
            final DeliveryDetailRepository deliveryDetailRepository) {
        super(noteDeliveryDetailRepository,"noteDeliveryDetailId");
        this.noteDeliveryDetailRepository = noteDeliveryDetailRepository;
        this.deliveryDetailRepository = deliveryDetailRepository;
    }

    @Override
    public Page<NoteDeliveryDetailDTO> search(Specification<NoteDeliveryDetail> query, Pageable pageable) {
        return this.noteDeliveryDetailRepository.findAll(query, pageable);
    }

    @Override
    protected NoteDeliveryDetailDTO mapToDTO(final NoteDeliveryDetail noteDeliveryDetail,
            final NoteDeliveryDetailDTO noteDeliveryDetailDTO) {
        noteDeliveryDetailDTO.setNoteDeliveryDetailId(noteDeliveryDetail.getNoteDeliveryDetailId());
        noteDeliveryDetailDTO.setNote(noteDeliveryDetail.getNote());
        noteDeliveryDetailDTO.setPhotoUrls(noteDeliveryDetail.getPhotoUrls());
        noteDeliveryDetailDTO.setDeliveryDetail(noteDeliveryDetail.getDeliveryDetail() == null ? null : noteDeliveryDetail.getDeliveryDetail().getDeliveryDetailId());
        return noteDeliveryDetailDTO;
    }

    @Override
    protected NoteDeliveryDetail mapToEntity(final NoteDeliveryDetailDTO noteDeliveryDetailDTO,
            final NoteDeliveryDetail noteDeliveryDetail) {
        noteDeliveryDetail.setNote(noteDeliveryDetailDTO.getNote());
        noteDeliveryDetail.setPhotoUrls(noteDeliveryDetailDTO.getPhotoUrls());
        final DeliveryDetail deliveryDetail = noteDeliveryDetailDTO.getDeliveryDetail() == null ? null : deliveryDetailRepository.findById(noteDeliveryDetailDTO.getDeliveryDetail())
                .orElseThrow(() -> new NotFoundException("deliveryDetail not found"));
        noteDeliveryDetail.setDeliveryDetail(deliveryDetail);
        return noteDeliveryDetail;
    }

    @Override
    protected NoteDeliveryDetailDTO createDTO() {
        return new NoteDeliveryDetailDTO();
    }

    @Override
    protected NoteDeliveryDetail createEntity() {
        return new NoteDeliveryDetail();
    }

}
