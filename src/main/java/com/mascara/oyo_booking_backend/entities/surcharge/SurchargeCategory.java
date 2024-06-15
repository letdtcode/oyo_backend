package com.mascara.oyo_booking_backend.entities.surcharge;

import com.mascara.oyo_booking_backend.entities.accommodation.SurchargeOfAccom;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/12/2023
 * Time      : 2:14 SA
 * Filename  : Surcharge
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "surcharge_category")
public class SurchargeCategory extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "surcharge_cate_name", unique = true)
    private String surchargeCateName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surchargeCategory")
    @Fetch(FetchMode.SUBSELECT)
    private Set<SurchargeOfAccom> surchargeOfAccomSet;

    @Column(name = "surcharge_code", unique = true)
    private String surchargeCode;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;
}
