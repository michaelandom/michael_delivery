package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Coupons;
import com.michael_delivery.backend.domain.UserCoupon;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.UserCouponDTO;
import com.michael_delivery.backend.repos.CouponsRepository;
import com.michael_delivery.backend.repos.UserCouponRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserCouponService {

    private final UserCouponRepository userCouponRepository;
    private final UsersRepository usersRepository;
    private final CouponsRepository couponsRepository;

    public UserCouponService(final UserCouponRepository userCouponRepository,
            final UsersRepository usersRepository, final CouponsRepository couponsRepository) {
        this.userCouponRepository = userCouponRepository;
        this.usersRepository = usersRepository;
        this.couponsRepository = couponsRepository;
    }

    public List<UserCouponDTO> findAll() {
        final List<UserCoupon> userCoupons = userCouponRepository.findAll(Sort.by("userCouponId"));
        return userCoupons.stream()
                .map(userCoupon -> mapToDTO(userCoupon, new UserCouponDTO()))
                .toList();
    }

    public UserCouponDTO get(final Long userCouponId) {
        return userCouponRepository.findById(userCouponId)
                .map(userCoupon -> mapToDTO(userCoupon, new UserCouponDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserCouponDTO userCouponDTO) {
        final UserCoupon userCoupon = new UserCoupon();
        mapToEntity(userCouponDTO, userCoupon);
        return userCouponRepository.save(userCoupon).getUserCouponId();
    }

    public void update(final Long userCouponId, final UserCouponDTO userCouponDTO) {
        final UserCoupon userCoupon = userCouponRepository.findById(userCouponId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userCouponDTO, userCoupon);
        userCouponRepository.save(userCoupon);
    }

    public void delete(final Long userCouponId) {
        userCouponRepository.deleteById(userCouponId);
    }

    private UserCouponDTO mapToDTO(final UserCoupon userCoupon, final UserCouponDTO userCouponDTO) {
        userCouponDTO.setUserCouponId(userCoupon.getUserCouponId());
        userCouponDTO.setUser(userCoupon.getUser() == null ? null : userCoupon.getUser().getUserId());
        userCouponDTO.setCoupon(userCoupon.getCoupon() == null ? null : userCoupon.getCoupon().getCouponId());
        return userCouponDTO;
    }

    private UserCoupon mapToEntity(final UserCouponDTO userCouponDTO, final UserCoupon userCoupon) {
        final Users user = userCouponDTO.getUser() == null ? null : usersRepository.findById(userCouponDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        userCoupon.setUser(user);
        final Coupons coupon = userCouponDTO.getCoupon() == null ? null : couponsRepository.findById(userCouponDTO.getCoupon())
                .orElseThrow(() -> new NotFoundException("coupon not found"));
        userCoupon.setCoupon(coupon);
        return userCoupon;
    }

}
