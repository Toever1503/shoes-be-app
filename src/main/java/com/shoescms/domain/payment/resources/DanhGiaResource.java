package com.shoescms.domain.payment.resources;

import com.shoescms.common.security.JwtTokenProvider;
import com.shoescms.domain.payment.dtos.DanhGiaDto;
import com.shoescms.domain.payment.dtos.DanhGiaReqDTO;
import com.shoescms.domain.payment.entities.DanhGiaEntity;
import com.shoescms.domain.payment.services.IDanhGiaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/danh-gia")
public class DanhGiaResource {
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private IDanhGiaService service;

    @PostMapping("/create")
    public List<DanhGiaEntity> create(@RequestBody List<DanhGiaEntity> danhGiaList, @RequestHeader(name = "x-api-token", required = false) String xApiToken) {
        Long userId = Long.parseLong(jwtTokenProvider.getUserPk(xApiToken));
        List<DanhGiaEntity> savedDanhGiaList = new ArrayList<>();

        for (DanhGiaEntity danhGia : danhGiaList) {
            danhGia.setNguoiTaoId(userId);
            danhGia.setNgayTao(LocalDateTime.now());
            savedDanhGiaList.add(service.create(danhGia));
        }

        return savedDanhGiaList;
    }

    @GetMapping("/byIds")
    public ResponseEntity<List<DanhGiaEntity>> searchByIds(@RequestParam("ids") List<Long> ids) {
        // Gọi phương thức trong service để thực hiện tìm kiếm theo danh sách ID.
        List<DanhGiaEntity> resultList = service.findByIds(ids);
        System.out.println("resultList: " + resultList);
        if (resultList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(resultList);
        }
    }

    @DeleteMapping("/xoa/{id}")
    public void xoaDanhGia(@PathVariable Long id){
        service.xoaDanhGia(id);
    }
    @GetMapping("public/lay-danh-gia/{id}")
    public Page<DanhGiaDto> layDanhGiaChoSp(@PathVariable Long id, @RequestParam(required = false) String q, Pageable pageable){
        return service.layDanhGiaChoSp(id, q, pageable);
    }

    @GetMapping("/soSao")
    public ResponseEntity<Double> findRatingBySanPham(@RequestParam("idSanPham") Long idSanPham) {
        // Gọi phương thức trong service để thực hiện tìm kiếm theo danh sách ID.
        Double result = service.findRatingBySanPham(idSanPham);
        System.out.println("result: " + result);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
