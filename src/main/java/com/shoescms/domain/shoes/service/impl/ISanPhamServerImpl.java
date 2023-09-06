package com.shoescms.domain.shoes.service.impl;

import com.shoescms.domain.shoes.dto.SanPhamDto;
import com.shoescms.domain.shoes.entitis.SanPham;
import com.shoescms.domain.shoes.models.SanPhamModel;
import com.shoescms.domain.shoes.repository.DanhMucRepository;
import com.shoescms.domain.shoes.repository.ISanPhamRepository;
import com.shoescms.domain.shoes.repository.ThuogHieuRepository;
import com.shoescms.domain.shoes.service.ISanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ISanPhamServerImpl implements ISanPhamService {
   @Autowired
    @Lazy
    private ISanPhamRepository sanPhamRepository;
   @Autowired
   private ThuogHieuRepository thuogHieuRepository;
   @Autowired
   private DanhMucRepository danhMucRepository;
   @Override
   public Page<SanPham> filterEntities(Pageable pageable, Specification<SanPham> specification){

       return this.sanPhamRepository.findAll(specification,pageable);
   }

    @Override
    public Page<SanPham> getAll(Pageable pageable) {
       return sanPhamRepository.findAll(pageable);
    }

    // Tìm Kiem theo thuong Hieu
    @Override
    public  Page<SanPham> findByThuongHieu(Long id , Pageable pageable){
       return  sanPhamRepository.findByThuongHieuId(id, pageable);
    }

    @Override
    public Page<SanPham> findByDmGiay(Long id, Pageable pageable) {
        return  sanPhamRepository.findByDmGiayId(id,pageable);
    }


    @Override
    public Page<SanPham> findByThuongHieuIdAndDmGiayId(Long idThuongHieu, Long idDanhMucGiay, Pageable pageable) {
        return  sanPhamRepository.findByThuongHieuIdAndDmGiayId(idThuongHieu,idDanhMucGiay,pageable);
    }

    @Override
   public SanPhamDto add(SanPhamModel model){
       if(model.getThuongHieu().getId()!=null || model.getDmGiay().getId()!=null) {
           if (thuogHieuRepository.findById(model.getThuongHieu().getId()).orElse(null) != null
                ||danhMucRepository.findById(model.getDmGiay().getId()).orElse(null)!=null) {
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
                       .ngayTao(LocalDateTime.now())
                       .nguoiCapNhat(model.getNguoiCapNhat())
                       .ngayCapNhat(model.getNgayCapNhat())
                       .ngayXoa(model.getNgayXoa())
                       .build();
               this.sanPhamRepository.saveAndFlush(entity);

               return SanPhamDto.toDto(entity);
           }
       }
       return null;
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
           // chua hieu sao khoong phari laf delete
           this.sanPhamRepository.delete(entity);
           return true;
       }catch (Exception e){
           return false;
       }
   }

   public SanPham getById(Long id){
       return this.sanPhamRepository.findById(id).orElseThrow(() -> new RuntimeException("22"));
   }

   public List<SanPham> getAll(){
       return sanPhamRepository.findAll();
   }
}
