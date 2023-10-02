package com.shoescms.domain.payment.services;

import com.shoescms.common.config.CommonConfig;
import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.common.exception.ProcessFailedException;
import com.shoescms.domain.cart.entity.GioHangChiTiet;
import com.shoescms.domain.cart.repository.GioHangChiTietRepository;
import com.shoescms.domain.cart.repository.GioHangRepository;
import com.shoescms.domain.cart.service.GioHangService;
import com.shoescms.domain.payment.dtos.*;
import com.shoescms.domain.payment.entities.ChiTietDonHangEntity;
import com.shoescms.domain.payment.entities.DiaChiEntity;
import com.shoescms.domain.payment.entities.DonHangEntity;
import com.shoescms.domain.payment.repositories.IChiTietDonHangRepository;
import com.shoescms.domain.payment.repositories.IDiaChiRepository;
import com.shoescms.domain.payment.repositories.IDonHangRepository;
import com.shoescms.domain.payment.resources.VnPayConfig;
import com.shoescms.domain.product.dto.SanPhamMetadataResDto;
import com.shoescms.domain.product.entitis.BienTheGiaTri;
import com.shoescms.domain.product.entitis.SanPham;
import com.shoescms.domain.product.entitis.SanPhamBienThe;
import com.shoescms.domain.product.enums.ELoaiBienThe;
import com.shoescms.domain.product.repository.IBienTheGiaTriRepository;
import com.shoescms.domain.product.repository.ISanPhamBienTheRepository;
import com.shoescms.domain.product.repository.ISanPhamRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Component
//@Log4j
@RequiredArgsConstructor
public class PaymentService {

    private final IDonHangRepository donHangRepository;
    private final IDiaChiRepository diaChiRepository;
    private final GioHangChiTietRepository gioHangChiTietRepository;
    private final ISanPhamBienTheRepository sanPhamBienTheRepository;
    private final GioHangRepository gioHangRepository;

    private final CommonConfig commonConfig;

    @Transactional
    public DonHangDto datHang(DatHangReqDto reqDto) {

        System.out.println();
        // luu thong tin don hang
        DonHangEntity donHangEntity = new DonHangEntity();
        donHangEntity.setNguoiMuaId(reqDto.getNguoiTao());
        //ma don hang
        donHangEntity.setMaDonHang(getRandomNumber(10));

        // luu thong tin chi tiet don hang
        taoChiTietDonHangTuGioHangTamThoi(reqDto.getGioHangTamThoiReqDto(), donHangEntity);

        donHangEntity.setPhuongThucTT(reqDto.getPhuongThucTT());
        if (reqDto.getPhuongThucTT().equals(EPhuongThucTT.VNPAY)) {
            donHangEntity.setNgayXoa(LocalDateTime.now());
        }
        donHangEntity.setTrangThai(ETrangThaiDonHang.WAITING_CONFIRM);

        donHangEntity.setTongGiaCuoiCung(donHangEntity.getTongGiaTien()); // need update later

        // luu dia chi dat hang
        DiaChiEntity diaChi = new DiaChiEntity();
        donHangEntity.setDiaChiEntity(diaChi);
        diaChi.setDiaChi(reqDto.getDiaChiNhanHang());
        diaChi.setSdt(reqDto.getSoDienThoaiNhanHang());
        diaChi.setTenNguoiNhan(reqDto.getHoTenNguoiNhan());

        diaChiRepository.saveAndFlush(diaChi);
        donHangRepository.saveAndFlush(donHangEntity);

        // xoa gio hang sau khi dat thanh cong
        if (reqDto.getNguoiTao() != null)
            gioHangChiTietRepository.deleteItemFromCart(reqDto.getGioHangItemIds(), gioHangRepository.findByUserEntity(reqDto.getNguoiTao()).getId());

        DonHangDto donHangDto = new DonHangDto();
        donHangDto.setId(donHangEntity.getId());
        donHangDto.setMaDonHang(donHangEntity.getMaDonHang());
        donHangDto.setTongSp(donHangEntity.getTongSp());
        donHangDto.setPhuongThucTT(donHangEntity.getPhuongThucTT());
        donHangDto.setNguoiMuaId(donHangEntity.getNguoiMuaId());
        donHangDto.setTrangThai(donHangEntity.getTrangThai());
        donHangDto.setNgayTao(donHangEntity.getNgayTao());
        donHangDto.setTongGiaCuoiCung(donHangEntity.getTongGiaTien());

        List<ChiTietDonHangDto> chiTietDonHangDtos = new ArrayList<>();
        for (int i = 0; i < donHangEntity.getChiTietDonHangs().size(); i++) {
            ChiTietDonHangDto chiTietDonHangDto = new ChiTietDonHangDto();
            chiTietDonHangDto.setId(donHangEntity.getChiTietDonHangs().get(i).getId());

            SanPhamBienThe sanPhamBienThe = sanPhamBienTheRepository.findById(donHangEntity.getChiTietDonHangs().get(i).getPhanLoaiSpId()).orElse(null);
            chiTietDonHangDto.setSanPham(SanPhamMetadataResDto.toDto(sanPhamBienThe.getSanPham()));
            chiTietDonHangDto.setPhanLoaiSpId(donHangEntity.getChiTietDonHangs().get(i).getPhanLoaiSpId());
            chiTietDonHangDto.setSoLuong(donHangEntity.getChiTietDonHangs().get(i).getSoLuong());
            chiTietDonHangDto.setGiaTien(donHangEntity.getChiTietDonHangs().get(i).getGiaTien());

            chiTietDonHangDtos.add(chiTietDonHangDto);
        }

        DiaChiDto diaChiDto = new DiaChiDto();
        diaChiDto.setId(diaChi.getId());
        diaChiDto.setTenNguoiNhan(diaChi.getTenNguoiNhan());
        diaChiDto.setSdt(diaChi.getSdt());
        diaChiDto.setDiaChi(diaChi.getDiaChi());
        donHangDto.setDiaChi(diaChiDto);
        donHangDto.setChiTietDonHang(chiTietDonHangDtos);


        return donHangDto;
    }

    private void taoChiTietDonHangTuGioHangTamThoi(List<GioHangTamThoiReqDto> gioHangTamThoiReqDto, DonHangEntity donHangEntity) {
        List<ChiTietDonHangEntity> chiTietDonHangEntities = new ArrayList<>();
        BigDecimal tongTien = new BigDecimal(0);
        Integer tongSanPham = 0;

        for (int i = 0; i < gioHangTamThoiReqDto.size(); i++) {
            SanPhamBienThe sanPhamBienThe = sanPhamBienTheRepository.findById(gioHangTamThoiReqDto.get(i).getSanPhamBienThe()).orElseThrow(() -> new ObjectNotFoundException(8));
            SanPham sanPham = sanPhamBienThe.getSanPham();
            BigDecimal tongTienSp = sanPham.getGiaMoi().multiply(BigDecimal.valueOf(gioHangTamThoiReqDto.get(i).getSoLuong().doubleValue()));
            tongTien = tongTien.add(tongTienSp);
            tongSanPham += gioHangTamThoiReqDto.get(i).getSoLuong();
            // tao thong tin
            ChiTietDonHangEntity chiTietDonHang = new ChiTietDonHangEntity();
            chiTietDonHang.setDonHang(donHangEntity);
            chiTietDonHang.setSoLuong(gioHangTamThoiReqDto.get(i).getSoLuong());
            chiTietDonHang.setGiaTien(sanPham.getGiaMoi());
            chiTietDonHang.setPhanLoaiSpId(sanPhamBienThe.getId());
            chiTietDonHang.setSanPhamId(sanPham.getId());
            chiTietDonHangEntities.add(chiTietDonHang);
        }

        donHangEntity.setTongSp(tongSanPham);
        donHangEntity.setTongTienSP(tongTien);
        donHangEntity.setTongGiaTien(tongTien);
        donHangEntity.setChiTietDonHangs(chiTietDonHangEntities);
    }

    private void taoChiTietDonHangTuGioHangChiTiet(List<Long> gioHangItems, DonHangEntity donHangEntity) {
        List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietRepository.findAllById(gioHangItems);
        List<ChiTietDonHangEntity> chiTietDonHangEntities = new ArrayList<>();
        BigDecimal tongTien = new BigDecimal(0);
        Integer tongSanPham = 0;

        for (int i = 0; i < gioHangChiTiets.size(); i++) {
            SanPhamBienThe sanPhamBienThe = sanPhamBienTheRepository.findById(gioHangChiTiets.get(i).getSanPhamBienThe()).orElseThrow(() -> new ObjectNotFoundException(8));
            SanPham sanPham = sanPhamBienThe.getSanPham();
            BigDecimal tongTienSp = sanPham.getGiaMoi().multiply(BigDecimal.valueOf(gioHangChiTiets.get(i).getSoLuong().doubleValue()));
            tongTien.add(tongTienSp);


            tongSanPham += gioHangChiTiets.get(i).getSoLuong();

            // tao thong tin
            ChiTietDonHangEntity chiTietDonHang = new ChiTietDonHangEntity();
            chiTietDonHang.setDonHang(donHangEntity);
            chiTietDonHang.setSoLuong(gioHangChiTiets.get(i).getSoLuong());
            chiTietDonHang.setGiaTien(sanPham.getGiaMoi());
            chiTietDonHang.setPhanLoaiSpId(sanPhamBienThe.getId());
            chiTietDonHang.setSanPhamId(sanPham.getId());

            chiTietDonHangEntities.add(chiTietDonHang);
        }

        donHangEntity.setTongSp(tongSanPham);
        donHangEntity.setTongGiaTien(tongTien);
        donHangEntity.setChiTietDonHangs(chiTietDonHangEntities);
    }

    public String taoUrlVnpay(DonHangDto dto) throws UnsupportedEncodingException {
        // thanh toan vnpay
        String vnp_OrderInfo = "Thanh toan don hang";//Thông tin mô tả nội dung thanh toán;
        String vnp_TxnRef = dto.getMaDonHang(); //Mã tham chiếu của giao dịch tại hệ thống của merchant.
        String bank_code = ""; // edit later

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "ATM";
        String vnp_IpAddr = "0:0:0:0:0:0:0:1";
        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;
        BigDecimal amount = dto.getTongGiaCuoiCung().multiply(BigDecimal.valueOf(100)); //gia tien don hang (gán tạm)

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount.toBigInteger()));
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

        return paymentUrl;
    }

    public String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public Long getDonHang(Long id) {
        return null;
    }


    @Transactional
    public String xuLyThanhToanVnpay(VnpayRedirectReqDto reqDto) throws IOException {
        DonHangEntity donHangEntity = donHangRepository.findByMaDonHang(reqDto.getVnp_TxnRef());

        StringBuilder resParameter = new StringBuilder("redirect:")
                .append(commonConfig.getVnpayRedirectURl())
                .append("?status=");
        if (!reqDto.getVnp_TransactionStatus().equals("00") || donHangEntity == null) // failed order
            resParameter.append("FAILED");
        else { // success
            donHangEntity.setTrangThai(ETrangThaiDonHang.VNPAY_PAID);
            donHangEntity.setNgayXoa(null);
            // need save vnpay info
            donHangRepository.saveAndFlush(donHangEntity);
            resParameter.append("SUCCESS&id=").append(donHangEntity.getId());
        }
        return resParameter.toString();
    }
}
