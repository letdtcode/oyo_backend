package com.mascara.oyo_booking_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "province")
public class Province extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "province_name", unique = true)
    private String provinceName;

    @Column(name = "thumbnail_link")
    private String thumbnail;

    @Column(name = "province_code", unique = true)
    private String provinceCode;

    @Column(name = "division_type", columnDefinition = "varchar (255)")
    private String divisionType;

    @Column(name = "slugs", columnDefinition = "varchar (255)", unique = true)
    private String slugs;

    @Column(name = "num_booking", nullable = false, columnDefinition = "bigint default 0")
        private Long numBooking;

    //    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "province")
    @Fetch(FetchMode.SUBSELECT)
    private Set<District> districtSet;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "province")
    @Fetch(FetchMode.SUBSELECT)
    private Set<AccomPlace> accomPlaces;
}
