package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.DeliveryDetail;
import com.michael_delivery.backend.model.NoteDeliveryDetail;
import com.michael_delivery.backend.model.Orders;
import com.michael_delivery.backend.dto.DeliveryDetailDTO;
import com.michael_delivery.backend.repository.DeliveryDetailRepository;
import com.michael_delivery.backend.repository.NoteDeliveryDetailRepository;
import com.michael_delivery.backend.repository.OrdersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class DeliveryDetailService extends BaseService<DeliveryDetail, DeliveryDetailDTO,Long, DeliveryDetailRepository>{

    private final DeliveryDetailRepository deliveryDetailRepository;
    private final OrdersRepository ordersRepository;
    private final NoteDeliveryDetailRepository noteDeliveryDetailRepository;

    public DeliveryDetailService(final DeliveryDetailRepository deliveryDetailRepository,
            final OrdersRepository ordersRepository,
            final NoteDeliveryDetailRepository noteDeliveryDetailRepository) {
        super(deliveryDetailRepository,"deliveryDetailId");
        this.deliveryDetailRepository = deliveryDetailRepository;
        this.ordersRepository = ordersRepository;
        this.noteDeliveryDetailRepository = noteDeliveryDetailRepository;
    }

    @Override
    public Page<DeliveryDetailDTO> search(Specification<DeliveryDetail> query, Pageable pageable) {
        return this.deliveryDetailRepository.findAll(query, pageable);
    }

    @Override
    protected DeliveryDetailDTO mapToDTO(final DeliveryDetail deliveryDetail,
            final DeliveryDetailDTO deliveryDetailDTO) {
        deliveryDetailDTO.setDeliveryDetailId(deliveryDetail.getDeliveryDetailId());
        deliveryDetailDTO.setPickupLatitude(deliveryDetail.getPickupLatitude());
        deliveryDetailDTO.setPickupLongitude(deliveryDetail.getPickupLongitude());
        deliveryDetailDTO.setPickupAddressText(deliveryDetail.getPickupAddressText());
        deliveryDetailDTO.setEstimatedTime(deliveryDetail.getEstimatedTime());
        deliveryDetailDTO.setPickupTime(deliveryDetail.getPickupTime());
        deliveryDetailDTO.setPickupDateTime(deliveryDetail.getPickupDateTime());
        deliveryDetailDTO.setPickedUpDateTime(deliveryDetail.getPickedUpDateTime());
        deliveryDetailDTO.setDesiredArrivalDateTime(deliveryDetail.getDesiredArrivalDateTime());
        deliveryDetailDTO.setPickedUpBy(deliveryDetail.getPickedUpBy());
        deliveryDetailDTO.setPickedUpNotes(deliveryDetail.getPickedUpNotes());
        deliveryDetailDTO.setRecipientPhoneNumber(deliveryDetail.getRecipientPhoneNumber());
        deliveryDetailDTO.setRecipientName(deliveryDetail.getRecipientName());
        deliveryDetailDTO.setPickupPhotos(deliveryDetail.getPickupPhotoUrls());
        deliveryDetailDTO.setOrder(deliveryDetail.getOrder() == null ? null : deliveryDetail.getOrder().getOrderId());
        return deliveryDetailDTO;
    }

    @Override
    protected DeliveryDetail mapToEntity(final DeliveryDetailDTO deliveryDetailDTO,
            final DeliveryDetail deliveryDetail) {
        deliveryDetail.setPickupLatitude(deliveryDetailDTO.getPickupLatitude());
        deliveryDetail.setPickupLongitude(deliveryDetailDTO.getPickupLongitude());
        deliveryDetail.setPickupAddressText(deliveryDetailDTO.getPickupAddressText());
        deliveryDetail.setEstimatedTime(deliveryDetailDTO.getEstimatedTime());
        deliveryDetail.setPickupTime(deliveryDetailDTO.getPickupTime());
        deliveryDetail.setPickupDateTime(deliveryDetailDTO.getPickupDateTime());
        deliveryDetail.setPickedUpDateTime(deliveryDetailDTO.getPickedUpDateTime());
        deliveryDetail.setDesiredArrivalDateTime(deliveryDetailDTO.getDesiredArrivalDateTime());
        deliveryDetail.setPickedUpBy(deliveryDetailDTO.getPickedUpBy());
        deliveryDetail.setPickedUpNotes(deliveryDetailDTO.getPickedUpNotes());
        deliveryDetail.setRecipientPhoneNumber(deliveryDetailDTO.getRecipientPhoneNumber());
        deliveryDetail.setRecipientName(deliveryDetailDTO.getRecipientName());
        deliveryDetail.setPickupPhotoUrls(deliveryDetailDTO.getPickupPhotos());
        final Orders order = deliveryDetailDTO.getOrder() == null ? null : ordersRepository.findById(deliveryDetailDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        deliveryDetail.setOrder(order);
        return deliveryDetail;
    }

    @Override
    protected DeliveryDetailDTO createDTO() {
        return new DeliveryDetailDTO();
    }

    @Override
    protected DeliveryDetail createEntity() {
        return new DeliveryDetail();
    }

    public ReferencedWarning getReferencedWarning(final Long deliveryDetailId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final DeliveryDetail deliveryDetail = deliveryDetailRepository.findById(deliveryDetailId)
                .orElseThrow(NotFoundException::new);
        final NoteDeliveryDetail deliveryDetailNoteDeliveryDetail = noteDeliveryDetailRepository.findFirstByDeliveryDetail(deliveryDetail);
        if (deliveryDetailNoteDeliveryDetail != null) {
            referencedWarning.setKey("deliveryDetail.noteDeliveryDetail.deliveryDetail.referenced");
            referencedWarning.addParam(deliveryDetailNoteDeliveryDetail.getNoteDeliveryDetailId());
            return referencedWarning;
        }
        return null;
    }

}
