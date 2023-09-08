package com.shoescms.domain.cart.controller;


import com.shoescms.domain.cart.entity.GioHang;
import com.shoescms.domain.cart.entity.GioHangChiTiet;
import com.shoescms.domain.cart.model.GioHangChiTietModel;
import com.shoescms.domain.cart.service.GioHangChiTietService;
import com.shoescms.domain.cart.service.GioHangService;
import com.shoescms.domain.shoes.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class GioHangChiTietController {

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private GioHangChiTietService gioHangChiTietService;

    @PostMapping("/add-item/{cartId}")
    public ResponseDto addItemToCart(
            @PathVariable Long cartId,
            @RequestBody GioHangChiTietModel model) {
        GioHang optionalCart = gioHangService.findById(cartId);
            GioHang cart = optionalCart;
            model.setGioHang(cart.getId());
            return ResponseDto.of(gioHangChiTietService.add(model));
    }

    @GetMapping("{cartId}")
    public ResponseEntity<String> viewCart(@PathVariable Long cartId) {
        GioHang optionalCart = gioHangService.findById(cartId);
            GioHang cart = optionalCart;
            return ResponseEntity.ok(cart.toString());
    }

    @DeleteMapping("/{cartId}/remove-item/{itemId}")
    public ResponseDto removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long itemId) {
            GioHang optionalCart = gioHangService.findById(cartId);
            gioHangChiTietService.remove(itemId);
            return ResponseDto.of(gioHangChiTietService.remove(itemId));
        }

}
