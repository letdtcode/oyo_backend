package com.mascara.oyo_booking_backend.entities.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascara.oyo_booking_backend.entities.address.District;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 11:45 CH
 * Filename  : Ward
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ward")
public class Ward extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ward_code", unique = true)
    private String wardCode;

    @Column(name = "ward_name")
    private String wardName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "district_code",
            referencedColumnName = "district_code",
            foreignKey = @ForeignKey(name = "fk_association_ward_district"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private District district;

    @Column(name = "district_code")
    private String districtCode;
}
