package com.shoescms.domain.product.service.impl;

import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.common.model.FileEntity;
import com.shoescms.common.model.repositories.FileRepository;
import com.shoescms.domain.product.repository.DanhMucRepository;
import com.shoescms.domain.product.repository.ISanPhamRepository;
import com.shoescms.domain.product.repository.ThuogHieuRepository;
import com.shoescms.domain.product.service.ISanPhamService;
import com.shoescms.domain.product.dto.SanPhamDto;
import com.shoescms.domain.product.entitis.SanPham;
import com.shoescms.domain.product.models.SanPhamModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ISanPhamServerImpl implements ISanPhamService {
   @Autowired
    @Lazy
    private ISanPhamRepository sanPhamRepository;
   @Autowired
   private ThuogHieuRepository thuogHieuRepository;
   @Autowired
   private DanhMucRepository danhMucRepository;

   @Autowired
   private FileRepository fileRepository;
   @Override
   public Page<SanPhamDto> filterEntities(Pageable pageable, Specification<SanPham> specification){
            Page<SanPham> sanPhamPage = sanPhamRepository.findAll(specification,pageable);
            return sanPhamPage.map(SanPhamDto::toDto);
   }

    @Override
    public Page<SanPham> getAll(Pageable pageable) {
       return sanPhamRepository.findAll(pageable);
    }

    @Override
    @Transactional
   public SanPhamDto add(SanPhamModel model){
       checkProduct(model);
       SanPham entity = SanPham.builder()
                       .anhBia(model.getAnhBia())
                       .maSP(model.getMaSP())
                       .moTa(model.getMoTa())
                       .dmGiay(danhMucRepository.findById(model.getDmGiay().getId()).get())
                       .giaCu(model.getGiaCu())
                       .giaMoi(model.getGiaMoi())
                       .gioiTinh(model.getGioiTinh())
                       .slug(model.getSlug())
                       .thuongHieu(thuogHieuRepository.findById(model.getThuongHieu().getId()).get())
                       .tieuDe(model.getTieuDe())
                       .nguoiCapNhat(model.getNguoiCapNhat())
                       .ngayXoa(model.getNgayXoa())
                       .anhChinh(model.getAnhChinh())
                       .anhPhu(String.join(",", model.getAnhPhu().stream().map(Object::toString).toList()))
                       .build();

       if(model.getId() == null)
           entity.setNguoiTao(model.getNguoiCapNhat());

               this.sanPhamRepository.saveAndFlush(entity);
               return SanPhamDto.toDto(entity);
   }

   private void checkProduct(SanPhamModel model){
       if(model.getId() != null)
       {
           SanPham sp = sanPhamRepository.findById(model.getId()).orElse(null);
           if(sp == null)
           throw new ObjectNotFoundException(111);

           if(model.getId() != null)
           {
               FileEntity anhChinh=  fileRepository.findById(sp.getAnhChinh()).orElse(null);
               if(anhChinh != null)
               {
                   anhChinh.setIsVerified(false);
                   fileRepository.saveAndFlush(anhChinh);
               }
               try{
                   List<FileEntity> files = fileRepository.findAllById(Arrays.stream(sp.getAnhPhu().split(","))
                           .map(Long::valueOf).toList());
                   files.forEach(f -> f.setIsVerified(false));
                   fileRepository.saveAllAndFlush(files);
               }
               catch (Exception e){

               }
           }
       }
       if(model.getThuongHieu().getId() == null || thuogHieuRepository.findById(model.getThuongHieu().getId()).isEmpty())
           throw new ObjectNotFoundException(1);
       if(model.getDmGiay().getId() == null || danhMucRepository.findById(model.getDmGiay().getId()).isEmpty())
           throw new ObjectNotFoundException(2);
       if(model.getAnhChinh() == null || fileRepository.findById(model.getAnhChinh()).isEmpty())
           throw new ObjectNotFoundException(3);
       if(model.getAnhPhu() == null)
           throw new ObjectNotFoundException(4);
       else if(model.getAnhPhu().size() > 5)
           throw new ObjectNotFoundException(5);
       else if (fileRepository.findAllById(model.getAnhPhu()).size() != model.getAnhPhu().size())
           throw new ObjectNotFoundException(4);
   }

   @Override
   public SanPhamDto update(SanPhamModel model){
       checkProduct(model);
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
       entity.setAnhChinh(model.getAnhChinh());
       entity.setAnhPhu(String.join(",", model.getAnhPhu().stream().map(Object::toString).toList()));
       this.sanPhamRepository.saveAndFlush(entity);
       return SanPhamDto.toDto(entity);
   }

   @Override
   public boolean deleteById(Long id){
       try {
           SanPham entity  = this.getById(id);
           this.sanPhamRepository.delete(entity);
           return true;
       }catch (Exception e){
           return false;
       }
   }

   public SanPham getById(Long id){
       return this.sanPhamRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(0));
   }

   public List<SanPham> getAll(){
       return sanPhamRepository.findAll();
   }

}