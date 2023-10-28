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
@Table(name = "facility_categories")
public class FacilityCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "faci_cate_name")
    private String faciCateName;

    @Column(name = "faci_cate_code",unique = true)
    private String faciCateCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facilityCategories")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Facility> facilitySet;
}
