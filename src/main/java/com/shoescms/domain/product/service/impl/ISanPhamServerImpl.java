package com.shoescms.domain.product.service.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.common.model.FileEntity;
import com.shoescms.common.model.repositories.FileRepository;
import com.shoescms.common.utils.ASCIIConverter;
import com.shoescms.domain.product.dto.*;
import com.shoescms.domain.product.enums.ELoaiBienThe;
import com.shoescms.domain.product.repository.DanhMucRepository;
import com.shoescms.domain.product.repository.ISanPhamRepository;
import com.shoescms.domain.product.repository.SanPhamBienTheRepository;
import com.shoescms.domain.product.repository.ThuogHieuRepository;
import com.shoescms.domain.product.service.ISanPhamService;
import com.shoescms.domain.product.entitis.SanPham;
import com.shoescms.domain.product.models.SanPhamModel;
import com.shoescms.domain.product.service.SanPhamBienTheService;
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
import static com.shoescms.domain.product.entitis.QSanPhamBienThe.sanPhamBienThe;

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

    @Autowired
    private SanPhamBienTheRepository sanPhamBienTheRepository;

    @Autowired
    private SanPhamBienTheService sanPhamBienTheService;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<SanPhamDto> filterEntities(Pageable pageable, Specification<SanPham> specification) {
        Page<SanPham> sanPhamPage = sanPhamRepository.findAll(specification, pageable);
        return sanPhamPage.map(item -> SanPhamDto.toDto(item).setAnhChinh(fileRepository.findById(item.getAnhChinh()).get()));
    }

    @Override
    public Page<SanPham> getAll(Pageable pageable) {
        return sanPhamRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public SanPhamDto add(SanPhamModel model) {
        checkProduct(model);
        SanPham entity = SanPham.builder()
                .maSP(model.getMaSP())
                .moTa(model.getMoTa())
                .dmGiay(danhMucRepository.findById(model.getDmGiay().getId()).get())
                .giaCu(model.getGiaCu())
                .giaMoi(model.getGiaMoi())
                .gioiTinh(model.getGioiTinh())
                .slug(ASCIIConverter.utf8ToAscii(model.getTieuDe()))
                .thuongHieu(thuogHieuRepository.findById(model.getThuongHieu().getId()).get())
                .tieuDe(model.getTieuDe())
                .nguoiCapNhat(model.getNguoiCapNhat())
                .ngayXoa(model.getNgayXoa())
                .anhChinh(model.getAnhChinh())
                .anhPhu(String.join(",", model.getAnhPhu().stream().map(Object::toString).toList()))
                .hienThiWeb(model.getHienThiWeb())
                .build();

        if (model.getId() == null)
            entity.setNguoiTao(model.getNguoiCapNhat());

        this.sanPhamRepository.saveAndFlush(entity);
        return SanPhamDto.toDto(entity);
    }

    private void checkProduct(SanPhamModel model) {
        if (model.getId() != null) {
            SanPham sp = sanPhamRepository.findById(model.getId()).orElse(null);
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
        if (model.getThuongHieu().getId() == null || thuogHieuRepository.findById(model.getThuongHieu().getId()).isEmpty())
            throw new ObjectNotFoundException(1);
        if (model.getDmGiay().getId() == null || danhMucRepository.findById(model.getDmGiay().getId()).isEmpty())
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
            SanPham entity = this.getById(id);
            this.sanPhamRepository.delete(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public void thayDoiPhanLoai(Long id, ELoaiBienThe type) {
        SanPham sanPham = sanPhamRepository.findById(id).orElse(null);
        if (sanPham.getLoaiBienThe() == null) {
            sanPham.setLoaiBienThe(type);
            sanPhamRepository.saveAndFlush(sanPham);
        } else if (!sanPham.getLoaiBienThe().equals(type)) {
            sanPhamBienTheRepository
                    .saveAllAndFlush(sanPhamBienTheRepository
                            .findAllAllBySanPhamIdAndNgayXoaIsNull(sanPham.getId())
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
            sanPham.setLoaiBienThe(type);
            sanPhamRepository.saveAndFlush(sanPham);
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
        SanPham sanPham = getById(id);
        if (sanPham.getNgayXoa() != null)
            throw new ObjectNotFoundException(1);

        WebChiTietSanPhamDto dto = WebChiTietSanPhamDto.toDto(sanPham);
        dto.setAnhChinh(fileRepository.findById(sanPham.getAnhChinh()).get());
        dto.setAnhPhu(fileRepository.findAllById(Arrays.stream(sanPham.getAnhPhu().split(",")).map(Long::valueOf).toList()));
        dto.setBienTheDTOS(sanPhamBienTheService.findAllPhanLoaiTheoSanPham(id));

        if (sanPham.getLoaiBienThe().equals(ELoaiBienThe.COLOR))
            setListBienThe1ChoSP(dto, id, false);
         else if (sanPham.getLoaiBienThe().equals(ELoaiBienThe.SIZE))
            setListBienThe2ChoSP(dto, id, false);
        else {
            setListBienThe1ChoSP(dto, id, true);
            setListBienThe2ChoSP(dto, id, true);
        }
        return dto;
    }

    @Override
    public Page<WebChiTietSanPhamDto> locSPChoWeb(SanPhamFilterReqDto reqDto, Pageable pageable) {
        // task: need filter
        Page<SanPham> pageSp = sanPhamRepository.findAll(pageable);
        return pageSp.map(sp -> chiTietSanPhamResDto(sp.getId()));
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
                                .where(bienTheGiaTri.id.in(jpaQueryFactory.select(sanPhamBienThe.bienTheGiaTri2)
                                        .from(sanPhamBienThe)
                                        .where(sanPhamBienThe.ngayXoa.isNull(),
                                                sanPhamBienThe.sanPham.id.eq(spId),
                                                sanPhamBienThe.bienTheGiaTri1.eq(giaTriDTO.getId())
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
                                .where(bienTheGiaTri.id.in(jpaQueryFactory.select(sanPhamBienThe.bienTheGiaTri1)
                                        .from(sanPhamBienThe)
                                        .where(sanPhamBienThe.ngayXoa.isNull(),
                                                sanPhamBienThe.sanPham.id.eq(spId),
                                                sanPhamBienThe.bienTheGiaTri2.eq(giaTriDTO.getId())
                                        )
                                        .fetch()))
                                .fetch());
                    }
                    return giaTriDTO;
                })
                .distinct()
                .collect(Collectors.toList()));
    }

    public SanPham getById(Long id) {
        return this.sanPhamRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(0));
    }

    public List<SanPham> getAll() {
        return sanPhamRepository.findAll();
    }

}
