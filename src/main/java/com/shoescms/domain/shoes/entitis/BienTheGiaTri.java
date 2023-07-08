package com.shoescms.domain.shoes.entitis;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "tbl_bien_the_gia_tri")
public class BienTheGiaTri {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "bien_the_id", nullable = false, columnDefinition = "BIGINT COMMENT 'bien the gia tri'")
    private BienThe bienThe;

    @Column(name = "gia_tri")
    private String giaTri;
}
