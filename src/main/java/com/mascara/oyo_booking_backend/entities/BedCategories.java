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
@Table(name = "bed_categories")
public class BedCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "bed_cate_name", columnDefinition = "NVARCHAR(255) NOT NULL")
    private String bedCateName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bedCategories")
    @Fetch(FetchMode.SUBSELECT)
    private Set<DetailBedOfRoom> detailBedOfRooms;
}
