package com.shoescms.domain.product.service.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.common.model.FileEntity;
import com.shoescms.common.model.repositories.FileRepository;
import com.shoescms.common.utils.ASCIIConverter;
import com.shoescms.domain.product.dto.*;
import com.shoescms.domain.product.entitis.SanPhamEntity;
import com.shoescms.domain.product.enums.ELoaiBienThe;
import com.shoescms.domain.product.repository.IDanhMucRepository;
import com.shoescms.domain.product.repository.ISanPhamRepository;
import com.shoescms.domain.product.repository.ISanPhamBienTheRepository;
import com.shoescms.domain.product.repository.IThuogHieuRepository;
import com.shoescms.domain.product.service.ISanPhamService;
import com.shoescms.domain.product.models.SanPhamModel;
import com.shoescms.domain.product.service.SanPhamBienTheService;
import com.shoescms.domain.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.shoescms.domain.product.entitis.QBienTheGiaTri.bienTheGiaTri;
import static com.shoescms.domain.product.entitis.QSanPhamBienTheEntity.sanPhamBienTheEntity;

@Service
public class ISanPhamServerImpl implements ISanPhamService {
    @Autowired
    @Lazy
    private ISanPhamRepository sanPhamRepository;
    @Autowired
    private IThuogHieuRepository thuogHieuRepository;
    @Autowired
    private IDanhMucRepository danhMucRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ISanPhamBienTheRepository sanPhamBienTheRepository;

    @Autowired
    private SanPhamBienTheService sanPhamBienTheService;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private VoucherService voucherService;

    @Override
    public Page<SanPhamDto> filterEntities(Pageable pageable, Specification<SanPhamEntity> specification) {
        Page<SanPhamEntity> sanPhamPage = sanPhamRepository.findAll(specification, pageable);
        return sanPhamPage.map(item -> SanPhamDto.toDto(item).setAnhChinh(fileRepository.findById(item.getAnhChinh()).get()));
    }

    @Override
    public Page<SanPhamEntity> getAll(Pageable pageable) {
        return sanPhamRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public SanPhamDto add(SanPhamModel model) {
        checkProduct(model);
        SanPhamEntity entity = SanPhamEntity.builder()
                .maSP(model.getMaSP())
                .moTa(model.getMoTa())
                .dmGiay(danhMucRepository.findById(model.getDmGiayEntity().getId()).get())
                .giaCu(model.getGiaCu())
                .giaMoi(model.getGiaMoi())
                .gioiTinh(model.getGioiTinh())
                .slug(ASCIIConverter.utf8ToAscii(model.getTieuDe()))
                .thuongHieu(thuogHieuRepository.findById(model.getThuongHieuEntity().getId()).get())
                .tieuDe(model.getTieuDe())
                .nguoiCapNhat(model.getNguoiCapNhat())
                .ngayXoa(model.getNgayXoa())
                .anhChinh(model.getAnhChinh())
                .anhPhu(String.join(",", model.getAnhPhu().stream().map(Object::toString).toList()))
                .hienThiWeb(model.getHienThiWeb())
                .chatLieu(model.getChatLieu())
                .trongLuong(model.getTrongLuong())
                .congNghe(model.getCongNghe())
                .tinhNang(model.getTinhNang())
                .noiSanXuat(model.getNoiSanXuat())
                .build();

        if (model.getId() == null)
            entity.setNguoiTao(model.getNguoiCapNhat());

        this.sanPhamRepository.saveAndFlush(entity);
        return SanPhamDto.toDto(entity);
    }

    private void checkProduct(SanPhamModel model) {
        if (model.getId() != null) {
            SanPhamEntity sp = sanPhamRepository.findById(model.getId()).orElse(null);
            if (sp == null)
                throw new ObjectNotFoundException(111);

            if (model.getId() != null) {
                FileEntity anhChinh = fileRepository.findById(sp.getAnhChinh()).orElse(null);
                if (anhChinh != null) {
                    anhChinh.setIsVerified(false);
                    fileRepository.saveAndFlush(anhChinh);
                }
                try {
                    List<FileEntity> files = fileRepository.findAllById(Arrays.stream(sp.getAnhPhu().split(","))
                            .map(Long::valueOf).toList());
                    files.forEach(f -> f.setIsVerified(false));
                    fileRepository.saveAllAndFlush(files);
                } catch (Exception e) {

                }
            }
        }
        if (model.getThuongHieuEntity().getId() == null || thuogHieuRepository.findById(model.getThuongHieuEntity().getId()).isEmpty())
            throw new ObjectNotFoundException(1);
        if (model.getDmGiayEntity().getId() == null || danhMucRepository.findById(model.getDmGiayEntity().getId()).isEmpty())
            throw new ObjectNotFoundException(2);
        if (model.getAnhChinh() == null || fileRepository.findById(model.getAnhChinh()).isEmpty())
            throw new ObjectNotFoundException(3);
        if (model.getAnhPhu() == null)
            throw new ObjectNotFoundException(4);
        else if (model.getAnhPhu().size() > 5)
            throw new ObjectNotFoundException(5);
        else if (fileRepository.findAllById(model.getAnhPhu()).size() != model.getAnhPhu().size())
            throw new ObjectNotFoundException(4);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            SanPhamEntity entity = this.getById(id);
            this.sanPhamRepository.delete(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public void thayDoiPhanLoai(Long id, ELoaiBienThe type) {
        SanPhamEntity sanPhamEntity = sanPhamRepository.findById(id).orElse(null);
        if (sanPhamEntity.getLoaiBienThe() == null) {
            sanPhamEntity.setLoaiBienThe(type);
            sanPhamRepository.saveAndFlush(sanPhamEntity);
        } else if (!sanPhamEntity.getLoaiBienThe().equals(type)) {
            sanPhamBienTheRepository
                    .saveAllAndFlush(sanPhamBienTheRepository
                            .findAllAllBySanPhamIdAndNgayXoaIsNull(sanPhamEntity.getId())
                            .stream()
                            .peek(sp -> {
                                FileEntity file = fileRepository.findById(sp.getAnh()).orElse(null);
                                if (file != null) {
                                    file.setIsVerified(false);
                                    fileRepository.saveAndFlush(file);
                                }
                                sp.delete();
                            })
                            .toList());
            sanPhamEntity.setLoaiBienThe(type);
            sanPhamRepository.saveAndFlush(sanPhamEntity);
        }

    }

    @Override
    public ELoaiBienThe getPhanLoai(Long id) {
        return getById(id).getLoaiBienThe();
    }

    @Override
    public SanPhamDto findBydId(Long id) {
        return SanPhamDto
                .toDto(getById(id));
    }

    @Override
    public WebChiTietSanPhamDto chiTietSanPhamResDto(Long id) {
        SanPhamEntity sanPhamEntity = getById(id);
        if (sanPhamEntity.getNgayXoa() != null)
            throw new ObjectNotFoundException(1);

        WebChiTietSanPhamDto dto = WebChiTietSanPhamDto.toDto(sanPhamEntity);
        dto.setAnhChinh(fileRepository.findById(sanPhamEntity.getAnhChinh()).get());
        dto.setAnhPhu(fileRepository.findAllById(Arrays.stream(sanPhamEntity.getAnhPhu().split(",")).map(Long::valueOf).toList()));
        dto.setBienTheDTOS(sanPhamBienTheService.findAllPhanLoaiTheoSanPham(id));

        if (sanPhamEntity.getLoaiBienThe().equals(ELoaiBienThe.COLOR))
            setListBienThe1ChoSP(dto, id, false);
         else if (sanPhamEntity.getLoaiBienThe().equals(ELoaiBienThe.SIZE))
            setListBienThe2ChoSP(dto, id, false);
        else {
            setListBienThe1ChoSP(dto, id, true);
            setListBienThe2ChoSP(dto, id, true);
        }

        dto.setSoLuongKho(dto.getBienTheDTOS().stream().map(SanPhamBienTheDTO::getSoLuong).reduce(0, Integer::sum));
        dto.setVouchers(voucherService.findAvailableVoucherByDanhMuc(dto.getDmGiay().getId()));
        return dto;
    }

    @Override
    public Page<WebChiTietSanPhamDto> locSPChoWeb(SanPhamFilterReqDto reqDto, Pageable pageable) {
        // task: need filter
        Page<SanPhamEntity> pageSp = sanPhamRepository.findAll(pageable);
        return pageSp.map(sp -> chiTietSanPhamResDto(sp.getId()));
    }

    @Override
    public List<SanPhamBienTheDTO> kiemTraSoLuongSpBienThe(List<Long> ids) {
        return sanPhamBienTheRepository.findAllById(ids)
                .stream()
                .map(item -> SanPhamBienTheDTO
                        .builder()
                        .id(item.getId())
                        .soLuong(item.getSoLuong())
                        .build())
                .toList();
    }

    private void setListBienThe1ChoSP(WebChiTietSanPhamDto dto, Long spId, boolean is2BienThe) {
        dto.setGiaTri1List(dto.getBienTheDTOS()
                .stream()
                .filter(item -> item.getBienThe1().equals(1L))
                .map(item -> {
                    BienTheGiaTriDTO giaTriDTO = BienTheGiaTriDTO
                            .builder()
                            .id(item.getGiaTriObj1().getId())
                            .giaTri(item.getGiaTriObj1().getGiaTri())
                            .build();

                    if (is2BienThe)
                    {
                      List<BienTheGiaTriDTO> ls =   jpaQueryFactory
                              .selectDistinct(Projections.constructor(BienTheGiaTriDTO.class,
                                        bienTheGiaTri.id,
                                        bienTheGiaTri.giaTri))
                              .from(bienTheGiaTri)
                                .where(bienTheGiaTri.id.in(jpaQueryFactory.select(sanPhamBienTheEntity.bienTheGiaTri2)
                                        .from(sanPhamBienTheEntity)
                                        .where(sanPhamBienTheEntity.ngayXoa.isNull(),
                                                sanPhamBienTheEntity.sanPham.id.eq(spId),
                                                sanPhamBienTheEntity.bienTheGiaTri1.eq(giaTriDTO.getId())
                                        )
                                        .fetch()))
                              .fetch();
                      giaTriDTO.setBienThe2(ls);
                    }
                    return giaTriDTO;
                })
                .distinct()
                .collect(Collectors.toList()));
    }

    private void setListBienThe2ChoSP(WebChiTietSanPhamDto dto, Long spId, boolean is2BienThe) {
        dto.setGiaTri2List(dto.getBienTheDTOS()
                .stream()
                .filter(item -> item.getBienThe2().equals(2L))
                .map(item -> {
                    BienTheGiaTriDTO giaTriDTO = BienTheGiaTriDTO
                            .builder()
                            .id(item.getGiaTriObj2().getId())
                            .giaTri(item.getGiaTriObj2().getGiaTri())
                            .build();
                    if (is2BienThe) {
                        giaTriDTO.setBienThe2(jpaQueryFactory.selectDistinct(
                                        Projections.constructor(BienTheGiaTriDTO.class,
                                                bienTheGiaTri.id,
                                                bienTheGiaTri.giaTri
                                        )
                                )
                                .from(bienTheGiaTri)
                                .where(bienTheGiaTri.id.in(jpaQueryFactory.select(sanPhamBienTheEntity.bienTheGiaTri1)
                                        .from(sanPhamBienTheEntity)
                                        .where(sanPhamBienTheEntity.ngayXoa.isNull(),
                                                sanPhamBienTheEntity.sanPham.id.eq(spId),
                                                sanPhamBienTheEntity.bienTheGiaTri2.eq(giaTriDTO.getId())
                                        )
                                        .fetch()))
                                .fetch());
                    }
                    return giaTriDTO;
                })
                .distinct()
                .collect(Collectors.toList()));
    }

    public SanPhamEntity getById(Long id) {
        return this.sanPhamRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(0));
    }

    public List<SanPhamEntity> getAll() {
        return sanPhamRepository.findAll();
    }

}
