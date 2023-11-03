package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.entities.base.Audit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 03/11/2023
 * Time      : 10:50 CH
 * Filename  : TypeBed
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "type_bed")
public class TypeBed extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "type_bed_name", columnDefinition = "NVARCHAR(255) NOT NULL")
    private String typeBedName;

    @Column(name = "type_bed_code", columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
    private String typeBedCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeBed")
    @Fetch(FetchMode.SUBSELECT)
    private Set<BedRoom> bedRoomSet;
}
