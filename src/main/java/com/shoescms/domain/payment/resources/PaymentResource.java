package com.shoescms.domain.payment.resources;

import com.shoescms.common.security.JwtTokenProvider;
import com.shoescms.domain.payment.dtos.*;
import com.shoescms.domain.payment.services.IDonHangService;
import com.shoescms.domain.payment.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Tag(name = "05. Payment")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/payment")
public class PaymentResource {
    @Autowired
    private IDonHangService donHangService;

    private final JwtTokenProvider jwtTokenProvider;

    private final PaymentService paymentService;


    @Operation(summary = "lay TT don hang", description = "lay TT don hang")
    @GetMapping(value = "{id}")
    public ResponseEntity<?> getTTDonHang(@PathVariable Long id, HttpSession session) {
        DatHangReqDto reqDto = (DatHangReqDto) session.getAttribute("donHang");
        if (!"".equals(reqDto)) {

        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paymentService.getDonHang(id));
    }

    @PostMapping("dat-hang")
    public DonHangDto datHang(@RequestBody DatHangReqDto reqDto, @RequestHeader(name = "x-api-token", required = false) String xApiToken) throws UnsupportedEncodingException {
        if (xApiToken != null) // luu thong tin nguoi dat hang neu ho dang nhap
            reqDto.setNguoiTao(Long.parseLong(jwtTokenProvider.getUserPk(xApiToken)));
        DonHangDto dto = paymentService.datHang(reqDto);
        if(dto.getPhuongThucTT().equals(EPhuongThucTT.VNPAY))
            dto.setUrlPay(paymentService.taoUrlVnpay(dto));
        return dto;
    }


    @GetMapping("chi-tiet-don-hang/{id}")
    public DonHangDto chiTietDonHang(@PathVariable Long id)
    {
        return paymentService.chiTietDonHang(id);
    }
    @PostMapping("/filter")
    public Page<DonHangDto> search(@RequestBody LocDonHangReqDto model, @PageableDefault(sort="id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable) {
        return donHangService.filterEntities(pageable, null);
    }
}
