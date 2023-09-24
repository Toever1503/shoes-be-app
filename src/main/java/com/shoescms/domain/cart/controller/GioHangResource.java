package com.shoescms.domain.cart.controller;


import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.common.security.JwtTokenProvider;
import com.shoescms.domain.cart.dto.GioHangChiTietDto;
import com.shoescms.domain.cart.dto.GioHangResDto;
import com.shoescms.domain.cart.entity.GioHang;
import com.shoescms.domain.cart.model.GioHangChiTietModel;
import com.shoescms.domain.cart.service.GioHangService;
import com.shoescms.domain.product.entitis.SanPhamBienThe;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/gio-hang")
public class GioHangResource {

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public GioHangChiTietDto addItemToCart(
            @Parameter(required = true, description = "info items to add into cart")
            @RequestBody GioHangChiTietModel model,
            @Parameter(required = true, description = "access token to use API")
            @RequestHeader("x-api-token") String token
            ) {
        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(token));
        GioHangResDto gioHang = gioHangService.findByUserEntity(userId);
        SanPhamBienThe sanPhamBienThe = gioHangService.getBienTheBySanPhamId(model.getSanPhamBienThe());
        if(gioHang.getId() == null){
            gioHang = gioHangService.add(GioHang.builder().userEntity(userId).build());
            model.setGioHang(gioHang.getId());
        }else {
            if(sanPhamBienThe.getSoLuong() > 0) {
                model.setGioHang(gioHang.getId());
                return gioHangService.addItem(model);
            }
            else if(sanPhamBienThe.getSoLuong() == 0){
                throw new ObjectNotFoundException(50);
            }
        }
        return gioHangService.addItem(model);
    }

    @GetMapping("gio-hang-cua-toi")
    public GioHangResDto viewCart(
            @RequestHeader("x-api-token") String token)
    {
        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(token));
        return gioHangService.findByUserEntity(userId);
    }

    @DeleteMapping("remove-item/{itemId}")
    public void removeItemFromCart(@RequestHeader("x-api-token") String token,
            @PathVariable Long itemId) {

        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(token));
        gioHangService.remove(itemId, userId);
        }

}
