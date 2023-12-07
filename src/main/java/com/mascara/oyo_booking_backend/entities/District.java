package com.mascara.oyo_booking_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 11:44 CH
 * Filename  : District
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "district")
@DynamicInsert
@JsonIgnoreProperties(value = {"province"})
public class District extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "district_code", unique = true)
    private String districtCode;

    @Column(name = "district_name")
    private String districtName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "province_code",
            referencedColumnName = "province_code",
            foreignKey = @ForeignKey(name = "fk_association_district_province"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private Province province;

    @Column(name = "province_code")
    private String provinceCode;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "district")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Ward> wardSet;
}
