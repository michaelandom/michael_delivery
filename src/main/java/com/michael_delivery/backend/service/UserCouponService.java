package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.*;
import com.michael_delivery.backend.dto.UserCouponDTO;
import com.michael_delivery.backend.repository.CouponsRepository;
import com.michael_delivery.backend.repository.UserCouponRepository;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class UserCouponService extends BaseService<UserCoupon, UserCouponDTO,Long, UserCouponRepository>{

    private final UserCouponRepository userCouponRepository;
    private final UsersRepository usersRepository;
    private final CouponsRepository couponsRepository;

    public UserCouponService(final UserCouponRepository userCouponRepository,
            final UsersRepository usersRepository, final CouponsRepository couponsRepository) {
        super(userCouponRepository,"userCouponId");
        this.userCouponRepository = userCouponRepository;
        this.usersRepository = usersRepository;
        this.couponsRepository = couponsRepository;
    }

    @Override
    public Page<UserCouponDTO> search(Specification<UserCoupon> query, Pageable pageable) {
        return this.userCouponRepository.findAll(query, pageable);
    }

    @Override
    protected UserCouponDTO mapToDTO(final UserCoupon userCoupon, final UserCouponDTO userCouponDTO) {
        userCouponDTO.setUserCouponId(userCoupon.getUserCouponId());
        userCouponDTO.setUser(userCoupon.getUser() == null ? null : userCoupon.getUser().getUserId());
        userCouponDTO.setCoupon(userCoupon.getCoupon() == null ? null : userCoupon.getCoupon().getCouponId());
        return userCouponDTO;
    }

    @Override
    protected UserCoupon mapToEntity(final UserCouponDTO userCouponDTO, final UserCoupon userCoupon) {
        final Users user = userCouponDTO.getUser() == null ? null : usersRepository.findById(userCouponDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        userCoupon.setUser(user);
        final Coupons coupon = userCouponDTO.getCoupon() == null ? null : couponsRepository.findById(userCouponDTO.getCoupon())
                .orElseThrow(() -> new NotFoundException("coupon not found"));
        userCoupon.setCoupon(coupon);
        return userCoupon;
    }

    @Override
    protected UserCouponDTO createDTO() {
        return new UserCouponDTO();
    }

    @Override
    protected UserCoupon createEntity() {
        return new UserCoupon();
    }

}
