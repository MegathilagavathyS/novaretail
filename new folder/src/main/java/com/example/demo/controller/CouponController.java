package com.example.demo.controller;

import com.example.demo.dto.CouponRequestDTO;
import com.example.demo.model.Coupon;
import com.example.demo.service.CouponService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService service;

    public CouponController(
            CouponService service) {

        this.service = service;
    }

    @PostMapping
    public Coupon createCoupon(
            @RequestBody
            CouponRequestDTO dto) {

        return service.createCoupon(dto);
    }

    @GetMapping
    public List<Coupon> getCoupons() {

        return service.getAllCoupons();
    }

    @GetMapping("/{code}")
    public Coupon getCoupon(
            @PathVariable String code) {

        return service.getCoupon(code);
    }
}