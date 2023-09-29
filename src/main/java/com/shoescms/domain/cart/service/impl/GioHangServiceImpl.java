package com.shoescms.domain.cart.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.common.model.repositories.FileRepository;
import com.shoescms.domain.cart.dto.GioHangChiTietDto;
import com.shoescms.domain.cart.dto.GioHangResDto;
import com.shoescms.domain.cart.entity.GioHang;
import com.shoescms.domain.cart.entity.GioHangChiTiet;
import com.shoescms.domain.cart.entity.GioHangChiTiet_;
import com.shoescms.domain.cart.model.GioHangChiTietModel;
import com.shoescms.domain.cart.repository.GioHangChiTietRepository;
import com.shoescms.domain.cart.repository.GioHangRepository;
import com.shoescms.domain.cart.service.GioHangService;
import com.shoescms.domain.product.entitis.SanPhamBienThe;
import com.shoescms.domain.product.repository.IBienTheGiaTriRepository;
import com.shoescms.domain.product.repository.ISanPhamBienTheRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.shoescms.domain.cart.entity.QGioHang.gioHang;
import static com.shoescms.domain.cart.entity.QGioHangChiTiet.gioHangChiTiet;

@Service
public class GioHangServiceImpl implements GioHangService {

    private final GioHangRepository gioHangRepository;
    private final GioHangChiTietRepository gioHangChiTietRepository;
    private final ISanPhamBienTheRepository sanPhamBienTheRepository;

    private final IBienTheGiaTriRepository bienTheGiaTriRepository;
    private final FileRepository fileRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public GioHangServiceImpl(GioHangRepository gioHangRepository, GioHangChiTietRepository gioHangChiTietRepository, ISanPhamBienTheRepository sanPhamBienTheRepository, IBienTheGiaTriRepository bienTheGiaTriRepository, FileRepository fileRepository, JPAQueryFactory jpaQueryFactory) {
        this.gioHangRepository = gioHangRepository;
        this.gioHangChiTietRepository = gioHangChiTietRepository;
        this.sanPhamBienTheRepository = sanPhamBienTheRepository;
        this.bienTheGiaTriRepository = bienTheGiaTriRepository;
        this.fileRepository = fileRepository;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public GioHangResDto findById(Long id) {
        GioHang entity = this.gioHangRepository.findById(id).orElse(null);
        if(entity == null){
            return null;
        }
        return GioHangResDto.class.cast(entity);
    }

    @Override
    public GioHangResDto findByUserEntity(Long userEntity) {
        GioHangResDto gioHangResDto = GioHangResDto.
                builder()
                .sanPhamHienCo(new ArrayList<>())
                .sanPhamHetHang(new ArrayList<>())
                .build();
        GioHang gioHang = gioHangRepository.findByUserEntity(userEntity);
        if(gioHang == null)
            return gioHangResDto;

        gioHangResDto.setId(gioHang.getId());
         gioHangChiTietRepository.findAllByGioHang(gioHang.getId(), Sort.by(Sort.Order.desc(GioHangChiTiet_.NGAY_CAP_NHAT)))
                 .stream()
                 .forEach(item ->
                 {
                     GioHangChiTietDto gioHangChiTietDto = GioHangChiTietDto.toDto(item);
                     SanPhamBienThe sanPhamBienThe = sanPhamBienTheRepository.findById(item.getSanPhamBienThe()).orElse(null);
                     gioHangChiTietDto.setSanPhamBienThe(sanPhamBienThe);
                     gioHangChiTietDto.getSanPhamBienThe().setAnh(fileRepository.findById(sanPhamBienThe.getAnh()).orElse(null));
                     gioHangChiTietDto.getSanPhamBienThe().setGiaTriObj1(bienTheGiaTriRepository.findById(sanPhamBienThe.getBienTheGiaTri1() == null ? 0 : sanPhamBienThe.getBienTheGiaTri1()).orElse(null));
                     gioHangChiTietDto.getSanPhamBienThe().setGiaTriObj2(bienTheGiaTriRepository.findById(sanPhamBienThe.getBienTheGiaTri2() == null ? 0 : sanPhamBienThe.getBienTheGiaTri2()).orElse(null));

                     if(gioHangChiTietDto.getSanPhamBienThe().getNgayXoa() != null && gioHangChiTietDto.getSanPhamBienThe().getSoLuong() != 0)
                         gioHangResDto.getSanPhamHienCo().add(gioHangChiTietDto);
                     else
                        gioHangResDto.getSanPhamHetHang().add(gioHangChiTietDto);
                 });
         return gioHangResDto;
    }

    @Override
    public GioHangResDto add(GioHang gioHang) {
        return GioHangResDto.builder()
                .id(gioHangRepository.saveAndFlush(gioHang).getId())
                .build();
    }

    @Override
    public void remove(Long itemId, Long userId) {
        GioHangChiTiet entity  = jpaQueryFactory.selectFrom(gioHangChiTiet)
                .join(gioHang)
                .on(gioHang.id.eq(gioHangChiTiet.gioHang))
                .where(gioHangChiTiet.id.eq(itemId), gioHang.userEntity.eq(userId))
                .fetchOne();
        if(entity != null)
        this.gioHangChiTietRepository.delete(entity);
    }

    @Override
    public SanPhamBienThe getBienTheBySanPhamId(Long sanPhamBienThe) {
        return this.sanPhamBienTheRepository.findById(sanPhamBienThe).orElseThrow(() -> new ObjectNotFoundException(50));

    }

    @Override
    public GioHangChiTietDto addItem(GioHangChiTietModel reqDto) {
        // Kiểm tra xem có sản phẩm biến thể đã tồn tại trong giỏ hàng chưa
        GioHangChiTiet existingEntity = this.gioHangChiTietRepository.findByGioHangAndSanPhamBienThe(
                reqDto.getGioHang(),reqDto.getSanPhamBienThe());

        if (existingEntity != null) {
            // Nếu sản phẩm biến thể đã tồn tại, thì tăng số lượng lên
            existingEntity.setSoLuong(existingEntity.getSoLuong() + reqDto.getSoLuong());
            this.gioHangChiTietRepository.saveAndFlush(existingEntity);
            return GioHangChiTietDto.toDto(existingEntity);
        } else {
            // Nếu sản phẩm biến thể chưa tồn tại, thì tạo mới
            GioHangChiTiet entity = GioHangChiTiet.builder()
                    .gioHang(reqDto.getGioHang())
                    .sanPhamBienThe(reqDto.getSanPhamBienThe())
                    .soLuong(reqDto.getSoLuong())
                    .build();
            this.gioHangChiTietRepository.saveAndFlush(entity);
            return GioHangChiTietDto.toDto(entity);
        }
    }

}
