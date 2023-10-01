package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.enums.BookingStatusEnum;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="accommodation_categories")
public class AccommodationCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "accom_cate_name", columnDefinition = "NOT NULL")
    private String accomCateName;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accommodationCategories")
    @Fetch(FetchMode.SUBSELECT)
    private Set<AccomPlace> accomPlaces;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;
}
