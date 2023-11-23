package com.shoescms.domain.payment.resources;

import com.shoescms.common.security.JwtTokenProvider;
import com.shoescms.domain.payment.dtos.DanhGiaDTO;
import com.shoescms.domain.payment.dtos.DanhGiaReqDTO;
import com.shoescms.domain.payment.entities.DanhGia;
import com.shoescms.domain.payment.services.IDanhGiaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.shoescms.domain.payment.entities.QChiTietDonHangEntity.chiTietDonHangEntity;
import static com.shoescms.domain.payment.entities.QDanhGia.danhGia;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/danh-gia")
public class DanhGiaResource {
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private IDanhGiaService service;

    @PostMapping("/create")
    public List<DanhGiaDTO> create(@RequestBody List<DanhGiaReqDTO> danhGiaList, @RequestHeader(name = "x-api-token", required = false) String xApiToken) {
        Long userId = Long.parseLong(jwtTokenProvider.getUserPk(xApiToken));
        List<DanhGiaDTO> savedDanhGiaList = new ArrayList<>();

           for (DanhGiaReqDTO danhGia : danhGiaList) {
                danhGia.setNguoiTaoId(userId);
                danhGia.setNgayTao(LocalDateTime.now());
                savedDanhGiaList.add(service.create(danhGia));
            }

        return savedDanhGiaList;
    }

    @GetMapping("/byIds")
    public ResponseEntity<List<DanhGia>> searchByIds(@RequestParam("ids") List<Long> ids) {
        // Gọi phương thức trong service để thực hiện tìm kiếm theo danh sách ID.
        List<DanhGia> resultList = service.findByIds(ids);
        System.out.println("resultList: " + resultList);
        if (resultList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(resultList);
        }
    }

    @GetMapping("public/lay-danh-gia/{id}")
    public List<DanhGia> layDanhGiaChoSp(@PathVariable Long id){
        return service.layDanhGiaChoSp(id);
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
