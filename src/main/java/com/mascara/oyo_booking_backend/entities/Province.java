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
@Table(name = "province")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "province_name")
    private String provinceName;

    @Column(name = "thumbnail_link")
    private String thumbnailLink;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "province")
    @Fetch(FetchMode.SUBSELECT)
    private Set<AccomPlace> accomPlaces;
}
