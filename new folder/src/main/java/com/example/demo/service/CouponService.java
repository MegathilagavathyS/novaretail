package com.example.demo.service;

import com.example.demo.dto.CouponRequestDTO;
import com.example.demo.model.Coupon;
import com.example.demo.repository.CouponRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(
            CouponRepository couponRepository) {

        this.couponRepository = couponRepository;
    }

    public Coupon createCoupon(
            CouponRequestDTO dto) {

        Coupon coupon = new Coupon();

        coupon.setCode(
                dto.getCode());

        coupon.setDiscountPercentage(
                dto.getDiscountPercentage());

        coupon.setExpiryDate(
                LocalDateTime.parse(
                        dto.getExpiryDate()));

        coupon.setActive(true);

        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons() {

        return couponRepository.findAll();
    }

    public Coupon getCoupon(
            String code) {

        return couponRepository.findByCode(code)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Coupon not found"));
    }
}