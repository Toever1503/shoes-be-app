package com.shoescms.domain.payment.resources;


import com.shoescms.common.security.JwtTokenProvider;
import com.shoescms.domain.payment.model.MaGiamGiaModel;
import com.shoescms.domain.payment.services.IMaGiamGiaService;
import com.shoescms.domain.product.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/coupon")
public class MaGiamGiaResource {

    @Autowired
    private IMaGiamGiaService maGiamGiaService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody MaGiamGiaModel model, @RequestHeader ("x-api-token") String token) {
        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(token));
        model.setNgayTao(LocalDateTime.now());
        model.setNguoiTao(userId);
        return ResponseEntity.ok(maGiamGiaService.add(model));
    }

    @PutMapping("/update")
    public ResponseDto update(@RequestBody MaGiamGiaModel model, @RequestHeader ("x-api-token") String token) {
        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(token));
        model.setNgayCapNhat(LocalDateTime.now());
        model.setNguoiCapNhat(userId);
        return ResponseDto.of(maGiamGiaService.update(model));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        maGiamGiaService.delete(id);
    }

    @GetMapping("/getByCode")
    public ResponseEntity getByCode(@RequestParam("maCode") String maCode) {
        return ResponseEntity.ok(maGiamGiaService.findByMaCode(maCode));
    }
}
