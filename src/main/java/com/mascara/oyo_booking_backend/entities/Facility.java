package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.entities.base.Audit;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "facility")
public class Facility extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "facility_name",unique = true)
    private String facilityName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "facility_accom",
            joinColumns = {@JoinColumn(name = "facility_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "accom_id", referencedColumnName = "id")}
    )
    private Set<AccomPlace> accomPlaceSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "faci_cate_code",
            referencedColumnName = "faci_cate_code",
            foreignKey = @ForeignKey(name = "fk_association_facil_facil_cate"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private FacilityCategories facilityCategories;

    @Column(name = "faci_cate_code")
    private String facilityCateCode;
}
