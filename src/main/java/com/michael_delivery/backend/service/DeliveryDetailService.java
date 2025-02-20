package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.DeliveryDetail;
import com.michael_delivery.backend.domain.NoteDeliveryDetail;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.model.DeliveryDetailDTO;
import com.michael_delivery.backend.repos.DeliveryDetailRepository;
import com.michael_delivery.backend.repos.NoteDeliveryDetailRepository;
import com.michael_delivery.backend.repos.OrdersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DeliveryDetailService {

    private final DeliveryDetailRepository deliveryDetailRepository;
    private final OrdersRepository ordersRepository;
    private final NoteDeliveryDetailRepository noteDeliveryDetailRepository;

    public DeliveryDetailService(final DeliveryDetailRepository deliveryDetailRepository,
            final OrdersRepository ordersRepository,
            final NoteDeliveryDetailRepository noteDeliveryDetailRepository) {
        this.deliveryDetailRepository = deliveryDetailRepository;
        this.ordersRepository = ordersRepository;
        this.noteDeliveryDetailRepository = noteDeliveryDetailRepository;
    }

    public List<DeliveryDetailDTO> findAll() {
        final List<DeliveryDetail> deliveryDetails = deliveryDetailRepository.findAll(Sort.by("deliveryDetailId"));
        return deliveryDetails.stream()
                .map(deliveryDetail -> mapToDTO(deliveryDetail, new DeliveryDetailDTO()))
                .toList();
    }

    public DeliveryDetailDTO get(final Long deliveryDetailId) {
        return deliveryDetailRepository.findById(deliveryDetailId)
                .map(deliveryDetail -> mapToDTO(deliveryDetail, new DeliveryDetailDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DeliveryDetailDTO deliveryDetailDTO) {
        final DeliveryDetail deliveryDetail = new DeliveryDetail();
        mapToEntity(deliveryDetailDTO, deliveryDetail);
        return deliveryDetailRepository.save(deliveryDetail).getDeliveryDetailId();
    }

    public void update(final Long deliveryDetailId, final DeliveryDetailDTO deliveryDetailDTO) {
        final DeliveryDetail deliveryDetail = deliveryDetailRepository.findById(deliveryDetailId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(deliveryDetailDTO, deliveryDetail);
        deliveryDetailRepository.save(deliveryDetail);
    }

    public void delete(final Long deliveryDetailId) {
        deliveryDetailRepository.deleteById(deliveryDetailId);
    }

    private DeliveryDetailDTO mapToDTO(final DeliveryDetail deliveryDetail,
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

    private DeliveryDetail mapToEntity(final DeliveryDetailDTO deliveryDetailDTO,
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
