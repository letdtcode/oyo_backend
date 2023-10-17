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
@Table(name = "facility_room_categories")
public class FacilityRoomCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "faci_romm_cate_name", columnDefinition = "NVARCHAR(255) NOT NULL")
    private String faciRoomCateName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facilityRoomCategories")
    @Fetch(FetchMode.SUBSELECT)
    private Set<FacilityRoom> facilityRoomSet;
}
