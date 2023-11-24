package com.mascara.oyo_booking_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascara.oyo_booking_backend.entities.base.Audit;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "province")
public class Province extends Audit<String> {
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

//    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "province")
    @Fetch(FetchMode.SUBSELECT)
    private Set<District> districtSet;


    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "province")
    @Fetch(FetchMode.SUBSELECT)
    private Set<AccomPlace> accomPlaces;
}
