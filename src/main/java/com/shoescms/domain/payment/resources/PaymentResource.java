package com.shoescms.domain.payment.resources;

import com.shoescms.common.security.JwtTokenProvider;
import com.shoescms.domain.payment.dtos.DatHangReqDto;
import com.shoescms.domain.payment.dtos.DonHangDto;
import com.shoescms.domain.payment.dtos.LocDonHangReqDto;
import com.shoescms.domain.payment.services.IDonHangService;
import com.shoescms.domain.payment.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "Dat hang", description = "Dat hang")
    @PostMapping(value = "/tao-don-hang")
    public ResponseEntity<?> taoDonHang(@RequestBody @Valid DatHangReqDto reqDto,
                                        @RequestHeader("x-api-token") String xApiToken,
                                        UriComponentsBuilder ucb, HttpSession session) throws UnsupportedEncodingException {
        Long nguoiDungId = Long.parseLong(jwtTokenProvider.getUserPk(xApiToken));
        paymentService.datHang(reqDto);

        //luu don hang vao session
        session.setAttribute("donHang", reqDto);

        // thanh toan vnpay
        String vnp_OrderInfo = "";//Thông tin mô tả nội dung thanh toán;
        String vnp_TxnRef = "";//Mã tham chiếu của giao dịch tại hệ thống của merchant.
        String bank_code = ""; // edit later

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "ATM";
        String vnp_IpAddr = "0:0:0:0:0:0:0:1";
        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;
        Integer amount = 0; //gia tien don hang (gán tạm)
        amount = amount * 100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn";
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(dt);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        Calendar cldvnp_ExpireDate = Calendar.getInstance();
        cldvnp_ExpireDate.add(Calendar.MINUTE, 15);
        Date vnp_ExpireDateD = cldvnp_ExpireDate.getTime();

        System.out.println("expireDate: " + vnp_ExpireDateD);
        String vnp_ExpireDate = formatter.format(vnp_ExpireDateD);

        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                // hashData.append(fieldValue); //sử dụng và 2.0.0 và 2.0.1 checksum sha256
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString())); // sử dụng v2.1.0
                // check sum
                // sha512
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.vnp_HashSecret, hashData.toString());
        System.out.println("hash: " + vnp_SecureHash);
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnPayConfig.vnp_PayUrl + "?" + queryUrl;


        return ResponseEntity
                .created(ucb.path("/payment/{id}")
                        .buildAndExpand(1)
                        .toUri()).build();
    }


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
    public DonHangDto datHang(@RequestBody DatHangReqDto reqDto, @RequestHeader(name = "x-api-token", required = false) String xApiToken) {
        if (xApiToken != null) // luu thong tin nguoi dat hang neu ho dang nhap
            reqDto.setNguoiTao(Long.parseLong(jwtTokenProvider.getUserPk(xApiToken)));
        return paymentService.datHang(reqDto);
    }

    @PostMapping("/filter")
    public Page<DonHangDto> search(@RequestBody LocDonHangReqDto model, @PageableDefault(sort="id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable) {
        return donHangService.filterEntities(pageable, null);
    }
}
