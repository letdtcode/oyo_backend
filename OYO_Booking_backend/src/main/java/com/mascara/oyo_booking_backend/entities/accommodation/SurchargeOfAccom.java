package com.mascara.oyo_booking_backend.entities.accommodation;

import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import com.mascara.oyo_booking_backend.entities.surcharge.SurchargeCategory;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/12/2023
 * Time      : 2:28 SA
 * Filename  : SurchargeOfAccom
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "surcharge_of_accom")
public class SurchargeOfAccom extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cost")
    private Double cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "accom_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_surcharge_accom"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private AccomPlace accomPlace;

    @Column(name = "accom_id")
    private Long accomPlaceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "surcharge_cate_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_surchargeofaccom_surchargecate"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private SurchargeCategory surchargeCategory;

    @Column(name = "surcharge_cate_id")
    private Long surchargeCateId;
}
