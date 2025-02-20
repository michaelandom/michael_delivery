package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Coupons;
import com.michael_delivery.backend.domain.UserCoupon;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.CouponsDTO;
import com.michael_delivery.backend.repos.CouponsRepository;
import com.michael_delivery.backend.repos.UserCouponRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CouponsService {

    private final CouponsRepository couponsRepository;
    private final UsersRepository usersRepository;
    private final UserCouponRepository userCouponRepository;

    public CouponsService(final CouponsRepository couponsRepository,
            final UsersRepository usersRepository,
            final UserCouponRepository userCouponRepository) {
        this.couponsRepository = couponsRepository;
        this.usersRepository = usersRepository;
        this.userCouponRepository = userCouponRepository;
    }

    public List<CouponsDTO> findAll() {
        final List<Coupons> couponses = couponsRepository.findAll(Sort.by("couponId"));
        return couponses.stream()
                .map(coupons -> mapToDTO(coupons, new CouponsDTO()))
                .toList();
    }

    public CouponsDTO get(final Long couponId) {
        return couponsRepository.findById(couponId)
                .map(coupons -> mapToDTO(coupons, new CouponsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CouponsDTO couponsDTO) {
        final Coupons coupons = new Coupons();
        mapToEntity(couponsDTO, coupons);
        return couponsRepository.save(coupons).getCouponId();
    }

    public void update(final Long couponId, final CouponsDTO couponsDTO) {
        final Coupons coupons = couponsRepository.findById(couponId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(couponsDTO, coupons);
        couponsRepository.save(coupons);
    }

    public void delete(final Long couponId) {
        couponsRepository.deleteById(couponId);
    }

    private CouponsDTO mapToDTO(final Coupons coupons, final CouponsDTO couponsDTO) {
        couponsDTO.setCouponId(coupons.getCouponId());
        couponsDTO.setDiscountType(coupons.getDiscountType());
        couponsDTO.setDiscountAmount(coupons.getDiscountAmount());
        couponsDTO.setDiscountPercentage(coupons.getDiscountPercentage());
        couponsDTO.setMaximumDiscountAmount(coupons.getMaximumDiscountAmount());
        couponsDTO.setMinimumPurchasePrice(coupons.getMinimumPurchasePrice());
        couponsDTO.setStartDate(coupons.getStartDate());
        couponsDTO.setEndDate(coupons.getEndDate());
        couponsDTO.setIssuedTo(coupons.getIssuedTo());
        couponsDTO.setHowUserWasAdded(coupons.getHowUserWasAdded());
        couponsDTO.setCode(coupons.getCode());
        couponsDTO.setNumberOfIssuedCoupons(coupons.getNumberOfIssuedCoupons());
        couponsDTO.setNumberOfUsedCoupons(coupons.getNumberOfUsedCoupons());
        couponsDTO.setExcelFile(coupons.getExcelFile());
        couponsDTO.setCreatedBy(coupons.getCreatedBy() == null ? null : coupons.getCreatedBy().getUserId());
        return couponsDTO;
    }

    private Coupons mapToEntity(final CouponsDTO couponsDTO, final Coupons coupons) {
        coupons.setDiscountType(couponsDTO.getDiscountType());
        coupons.setDiscountAmount(couponsDTO.getDiscountAmount());
        coupons.setDiscountPercentage(couponsDTO.getDiscountPercentage());
        coupons.setMaximumDiscountAmount(couponsDTO.getMaximumDiscountAmount());
        coupons.setMinimumPurchasePrice(couponsDTO.getMinimumPurchasePrice());
        coupons.setStartDate(couponsDTO.getStartDate());
        coupons.setEndDate(couponsDTO.getEndDate());
        coupons.setIssuedTo(couponsDTO.getIssuedTo());
        coupons.setHowUserWasAdded(couponsDTO.getHowUserWasAdded());
        coupons.setCode(couponsDTO.getCode());
        coupons.setNumberOfIssuedCoupons(couponsDTO.getNumberOfIssuedCoupons());
        coupons.setNumberOfUsedCoupons(couponsDTO.getNumberOfUsedCoupons());
        coupons.setExcelFile(couponsDTO.getExcelFile());
        final Users createdBy = couponsDTO.getCreatedBy() == null ? null : usersRepository.findById(couponsDTO.getCreatedBy())
                .orElseThrow(() -> new NotFoundException("createdBy not found"));
        coupons.setCreatedBy(createdBy);
        return coupons;
    }

    public ReferencedWarning getReferencedWarning(final Long couponId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Coupons coupons = couponsRepository.findById(couponId)
                .orElseThrow(NotFoundException::new);
        final UserCoupon couponUserCoupon = userCouponRepository.findFirstByCoupon(coupons);
        if (couponUserCoupon != null) {
            referencedWarning.setKey("coupons.userCoupon.coupon.referenced");
            referencedWarning.addParam(couponUserCoupon.getUserCouponId());
            return referencedWarning;
        }
        return null;
    }

}
