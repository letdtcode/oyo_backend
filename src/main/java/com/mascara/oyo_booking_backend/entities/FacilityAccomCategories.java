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
@Table(name = "facility_accom_categories")
public class FacilityAccomCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "faci_accom_cate_name")
    private String faciAccomCateName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facilityAccomCategories")
    @Fetch(FetchMode.SUBSELECT)
    private Set<FacilityAccom> facilityAccomSet;
}
