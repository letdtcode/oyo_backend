package com.mascara.oyo_booking_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="facility_accom")
public class FacilityAccom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "facility_name")
    private String facilityName;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "facility_accom_accom",
            joinColumns = {@JoinColumn(name = "facility_accom_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "accom_id", referencedColumnName = "id")}
    )
    private Set<AccomPlace> accomPlaceSet;

    @ManyToOne
    @JoinColumn(
            name = "facility_cate_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_facil_accom_facil_acc_cate"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private FacilityAccomCategories facilityAccomCategories;

    @Column(name = "facility_cate_id", columnDefinition = "BINARY(16)")
    private UUID facilityCateId;
}
