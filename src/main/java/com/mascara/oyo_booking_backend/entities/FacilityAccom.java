package com.mascara.oyo_booking_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "facility_accom")
public class FacilityAccom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "facility_name")
    private String facilityName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "facility_accom_accom",
            joinColumns = {@JoinColumn(name = "facility_accom_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "accom_id", referencedColumnName = "id")}
    )
    private Set<AccomPlace> accomPlaceSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "facility_cate_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_facil_accom_facil_acc_cate"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private FacilityAccomCategories facilityAccomCategories;

    @Column(name = "facility_cate_id")
    private Long facilityCateId;
}
