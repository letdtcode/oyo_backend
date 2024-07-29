package com.mascara.oyo_booking_backend.entities.facility;

import com.mascara.oyo_booking_backend.entities.accommodation.AccomPlace;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import com.mascara.oyo_booking_backend.common.enums.CommonStatusEnum;
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
@Table(name = "facility")
public class Facility extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "facility_name", unique = true)
    private String facilityName;

    @Column(name = "faci_code")
    private String facilityCode;

    @ManyToMany(mappedBy = "facilitySet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;
}
