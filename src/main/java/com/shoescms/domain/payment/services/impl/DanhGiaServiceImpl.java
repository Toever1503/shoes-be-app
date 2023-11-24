package com.shoescms.domain.payment.services.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoescms.domain.payment.dtos.DanhGiaDTO;
import com.shoescms.domain.payment.dtos.DanhGiaReqDTO;
import com.shoescms.domain.payment.entities.DanhGia;
import com.shoescms.domain.payment.entities.DonHangEntity;
import com.shoescms.domain.payment.repositories.IDanhGiaRepository;
import com.shoescms.domain.payment.repositories.IDonHangRepository;
import com.shoescms.domain.payment.services.IDanhGiaService;
import com.shoescms.domain.product.entitis.SanPhamEntity;
import com.shoescms.domain.product.repository.ISanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shoescms.domain.payment.entities.QChiTietDonHangEntity.chiTietDonHangEntity;
import static com.shoescms.domain.payment.entities.QDanhGia.danhGia;

@Service
public class DanhGiaServiceImpl implements IDanhGiaService {

    @Autowired
    private IDanhGiaRepository repo;

    @Autowired
    private ISanPhamRepository sanPhamRepository;

    @Autowired
    private IDonHangRepository donHangRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public DanhGiaDTO create(DanhGiaReqDTO danhGia) {
        // Thêm mới đánh giá và cập nhật sản phẩm
        DanhGia entity = DanhGia.builder()
                .donHangChiTietId(danhGia.getDonHangChiTietId())
                .binhLuan(danhGia.getBinhLuan())
                .ngayTao(danhGia.getNgayTao())
                .nguoiTaoId(danhGia.getNguoiTaoId())
                .soSao(danhGia.getSoSao())
                .build();
        repo.save(entity);

        // Cập nhật điểm trung bình đánh giá cho sản phẩm
        SanPhamEntity sp = repo.findSanPhamDanhGia(danhGia.getDonHangChiTietId());
        DonHangEntity dh = repo.findDonHangDanhGia(danhGia.getDonHangChiTietId());

        System.out.println("sp = " + sp);
        System.out.println("dh = " + dh);
        if (sp != null) {
            System.out.println("sanPham = " + sp);
            Integer soNguoiDanhGia = layDanhGiaChoSp(sp.getId()).size();
            Double soSao = repo.findRatingBySanPham(sp.getId());
            sp.setTbDanhGia(soSao.floatValue()); // Đảm bảo không gặp vấn đề với giá trị null
            sp.setSoDanhGia(soNguoiDanhGia);
            dh.setCheckRate(1);
            sanPhamRepository.save(sp);
            donHangRepository.save(dh);
            System.out.println("sanPham sau khi update = " + sp);
        } else {
            // Xử lý trường hợp không tìm thấy sản phẩm
            // Có thể làm gì đó tùy thuộc vào yêu cầu của bạn
        }

        return DanhGiaDTO.builder()
                .id(entity.getId())
                .binhLuan(entity.getBinhLuan())
                .donHangChiTietId(entity.getDonHangChiTietId())
                .ngayTao(entity.getNgayTao())
                .nguoiTaoId(entity.getNguoiTaoId())
                .soSao(entity.getSoSao())
                .build();
    }


    @Override
    public List<DanhGia> findByIds(List<Long> ids) {
        return repo.findByIds(ids);
    }

    @Override
    public List<DanhGia> layDanhGiaChoSp(Long id) {
        return jpaQueryFactory.selectFrom(danhGia)
                .join(chiTietDonHangEntity)
                .on(chiTietDonHangEntity.id.eq(danhGia.donHangChiTietId))
                .where(chiTietDonHangEntity.spId.eq(id))
                .fetch();
    }

    @Override
    public Double findRatingBySanPham(Long idSanPham) {
        return repo.findRatingBySanPham(idSanPham);
    }
}
