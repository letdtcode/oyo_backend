package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "accommodation_categories")
public class AccommodationCategories extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "accom_cate_name", columnDefinition = "VARCHAR(255) NOT NULL", unique = true)
    private String accomCateName;

    @Column(name = "description")
    private String description;

    @Column(name = "icon", unique = true, nullable = false)
    private String icon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accommodationCategories")
    @Fetch(FetchMode.SUBSELECT)
    private Set<AccomPlace> accomPlaces;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;
}
