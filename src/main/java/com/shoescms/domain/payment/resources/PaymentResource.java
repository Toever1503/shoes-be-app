package com.shoescms.domain.payment.resources;

import com.shoescms.common.security.JwtTokenProvider;
import com.shoescms.domain.payment.dtos.DatHangReqDto;
import com.shoescms.domain.payment.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Tag(name = "05. Payment")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/payment")
public class PaymentResource {

    private final JwtTokenProvider jwtTokenProvider;
    private final PaymentService paymentService;

    @Operation(summary = "Dat hang", description = "Dat hang")
    @PostMapping(value = "/tao-don-hang")
    public ResponseEntity<?> taoDonHang(@RequestBody @Valid DatHangReqDto reqDto,
                                        @RequestHeader("x-api-token") String xApiToken,
                                        UriComponentsBuilder ucb) {
        Long nguoiDungId = Long.parseLong(jwtTokenProvider.getUserPk(xApiToken));
        paymentService.datHang(reqDto, nguoiDungId);

        return ResponseEntity
                .created(ucb.path("/payment/{id}")
                        .buildAndExpand(1)
                        .toUri()).build();
    }

    @Operation(summary = "lay TT don hang", description = "lay TT don hang")
    @GetMapping(value = "{id}")
    public ResponseEntity<?> getTTDonHang(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getDonHang(id));
    }
}
