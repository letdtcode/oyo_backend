package com.mascara.oyo_booking_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "province")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "province_name", unique = true)
    private String provinceName;

    @Column(name = "thumbnail_link")
    private String thumbnail;

    @Column(name = "province_code", unique = true)
    private String provinceCode;

    @Column(name = "division_type",columnDefinition = "varchar (255)")
    private String divisionType;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "province")
    @Fetch(FetchMode.SUBSELECT)
    private Set<District> districtSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "province")
    @Fetch(FetchMode.SUBSELECT)
    private Set<AccomPlace> accomPlaces;
}
