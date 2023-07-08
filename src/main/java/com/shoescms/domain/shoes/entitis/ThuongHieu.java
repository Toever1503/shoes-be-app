package com.shoescms.domain.shoes.entitis;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_thuong_hieu")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class ThuongHieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ten_thuong_hieu")
    private String tenThuongHieu;

    @Column(name = "slug")
    private String slug;
}
