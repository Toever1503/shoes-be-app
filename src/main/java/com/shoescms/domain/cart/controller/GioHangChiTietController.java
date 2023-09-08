package com.shoescms.domain.cart.controller;


import com.shoescms.common.security.JwtTokenProvider;
import com.shoescms.domain.cart.entity.GioHang;
import com.shoescms.domain.cart.entity.GioHangChiTiet;
import com.shoescms.domain.cart.model.GioHangChiTietModel;
import com.shoescms.domain.cart.service.GioHangChiTietService;
import com.shoescms.domain.cart.service.GioHangService;
import com.shoescms.domain.shoes.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("web/api/cart")
public class GioHangChiTietController {

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private GioHangChiTietService gioHangChiTietService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/add-item")
    public ResponseDto addItemToCart(
            @Parameter(required = true, description = "info items to add into cart")
            @RequestBody GioHangChiTietModel model,
            @Parameter(required = true, description = "access token to use API")
            @RequestHeader("x-api-token") String token
            ) {
        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(token));


        GioHang optionalCart = gioHangService.findById(cartId);
            GioHang cart = optionalCart;
            model.setGioHang(cart.getId());
            return ResponseDto.of(gioHangChiTietService.add(model));
    }

    @GetMapping("my-cart")
    public ResponseEntity<String> viewCart(
            @RequestHeader("x-api-token") String token)
    {
        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(token));
        GioHang optionalCart = gioHangService.findById(cartId);
            GioHang cart = optionalCart;
            return ResponseEntity.ok(cart.toString());
    }

    @DeleteMapping("remove-item/{itemId}")
    public ResponseDto removeItemFromCart(@RequestHeader("x-api-token") String token,
            @PathVariable Long itemId) {

        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(token));
            GioHang optionalCart = gioHangService.findById(cartId);
            gioHangChiTietService.remove(itemId);
            return ResponseDto.of(gioHangChiTietService.remove(itemId));
        }

}
