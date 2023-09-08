package com.shoescms.domain.cart.service.impl;


import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.domain.cart.dto.GioHangChiTietDto;
import com.shoescms.domain.cart.entity.GioHangChiTiet;
import com.shoescms.domain.cart.model.GioHangChiTietModel;
import com.shoescms.domain.cart.repository.GioHangChiTietRepository;
import com.shoescms.domain.cart.service.GioHangChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GioHangChiTietServiceImpl implements GioHangChiTietService {

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;


    @Override
    public GioHangChiTietDto add(GioHangChiTietModel gioHangChiTiet) {
        // Kiểm tra xem có sản phẩm biến thể đã tồn tại trong giỏ hàng chưa
        GioHangChiTiet existingEntity = this.gioHangChiTietRepository.findByGioHangAndSanPhamAndSanPhamBienThe(
                gioHangChiTiet.getGioHang(),gioHangChiTiet.getSanPham(), gioHangChiTiet.getSanPhamBienThe());

        if (existingEntity != null) {
            // Nếu sản phẩm biến thể đã tồn tại, thì tăng số lượng lên
            existingEntity.setSoLuong(existingEntity.getSoLuong() + gioHangChiTiet.getSoLuong());
            this.gioHangChiTietRepository.saveAndFlush(existingEntity);
            return GioHangChiTietDto.toDto(existingEntity);
        } else {
            // Nếu sản phẩm biến thể chưa tồn tại, thì tạo mới
            GioHangChiTiet entity = GioHangChiTiet.builder()
                    .gioHang(gioHangChiTiet.getGioHang())
                    .sanPham(gioHangChiTiet.getSanPham())
                    .sanPhamBienThe(gioHangChiTiet.getSanPhamBienThe())
                    .soLuong(gioHangChiTiet.getSoLuong())
                    .build();
            this.gioHangChiTietRepository.saveAndFlush(entity);
            return GioHangChiTietDto.toDto(entity);
        }
    }

    public GioHangChiTiet getById(Long id){
        return this.gioHangChiTietRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Không tìm thấy giỏ hàng chi tiết với id: " + id));
    }
    @Override
    public boolean remove(Long id) {
        try {
            GioHangChiTiet entity  = this.getById(id);
            // chua hieu sao khoong phari laf delete
            this.gioHangChiTietRepository.delete(entity);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
