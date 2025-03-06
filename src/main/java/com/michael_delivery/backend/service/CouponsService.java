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
public class CouponsService extends BaseService<Coupons, CouponsDTO,Long, CouponsRepository>  {

    private final CouponsRepository couponsRepository;
    private final UsersRepository usersRepository;
    private final UserCouponRepository userCouponRepository;

    public CouponsService(final CouponsRepository couponsRepository,
            final UsersRepository usersRepository,
            final UserCouponRepository userCouponRepository) {
        super(couponsRepository,"couponId");
        this.couponsRepository = couponsRepository;
        this.usersRepository = usersRepository;
        this.userCouponRepository = userCouponRepository;
    }

    @Override
    protected CouponsDTO createDTO() {
        return new CouponsDTO();
    }

    @Override
    protected Coupons createEntity() {
        return new Coupons();
    }

    @Override
    protected CouponsDTO mapToDTO(final Coupons coupons, final CouponsDTO couponsDTO) {
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
        couponsDTO.setExcelFileUrl(coupons.getExcelFileUrl());
        couponsDTO.setCreatedBy(coupons.getCreatedBy() == null ? null : coupons.getCreatedBy().getUserId());
        return couponsDTO;
    }

    @Override
    protected Coupons mapToEntity(final CouponsDTO couponsDTO, final Coupons coupons) {
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
        coupons.setExcelFileUrl(couponsDTO.getExcelFileUrl());
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
