package com.shoescms.domain.shoes.service.impl;

import com.shoescms.domain.shoes.dto.SanPhamDto;
import com.shoescms.domain.shoes.entitis.SanPham;
import com.shoescms.domain.shoes.models.SanPhamModel;
import com.shoescms.domain.shoes.repository.ISanPhamRepository;
import com.shoescms.domain.shoes.service.ISanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ISanPhamServerImpl implements ISanPhamService {
   @Autowired
    @Lazy
    private ISanPhamRepository sanPhamRepository;

   @Override
   public Page<SanPham> filterEntities(Pageable pageable, Specification<SanPham> specification){

       return this.sanPhamRepository.findAll(specification,pageable);
   }



   @Override
   public SanPhamDto add(SanPhamModel model){
       SanPham entity = SanPham.builder()
               .anhBia(model.getAnhBia())
               .maSP(model.getMaSP())
               .moTa(model.getMoTa())
               .dmGiay(model.getDmGiay())
               .giaCu(model.getGiaCu())
               .giaMoi(model.getGiaMoi())
               .gioiTinh(model.getGioiTinh())
               .slug(model.getSlug())
               .thuongHieu(model.getThuongHieu())
               .tieuDe(model.getTieuDe())
               .nguoiTao(model.getNguoiTao())
               .ngayTao(model.getNgayTao())
               .ngayXoa(model.getNgayXoa())
               .build();
       this.sanPhamRepository.saveAndFlush(entity);
       return SanPhamDto.toDto(entity);
   }
   @Override
   public SanPhamDto update(SanPhamModel model){
       SanPham entity = this.getById(model.getId());
       entity.setAnhBia(model.getAnhBia());
       entity.setGiaMoi(model.getGiaMoi());
       entity.setNgayCapNhat(model.getNgayCapNhat());
       entity.setDmGiay(model.getDmGiay());
       entity.setGioiTinh(model.getGioiTinh());
       entity.setMaSP(model.getMaSP());
       entity.setGiaCu(model.getGiaCu());
       entity.setThuongHieu(model.getThuongHieu());
       entity.setTieuDe(model.getTieuDe());
       entity.setNguoiCapNhat(model.getNguoiCapNhat());
       this.sanPhamRepository.saveAndFlush(entity);
       return SanPhamDto.toDto(entity);
   }

   @Override
   public boolean deleteById(Long id){
       try {
           SanPham entity  = this.getById(id);
           this.sanPhamRepository.saveAndFlush(entity);
           return true;
       }catch (Exception e){
           return false;
       }
   }

   public SanPham getById(Long id){
       return this.sanPhamRepository.findById(id).orElseThrow(() -> new RuntimeException("22"));
   }

}
