package com.mascara.oyo_booking_backend.entities.accommodation;

import com.mascara.oyo_booking_backend.entities.type_bed.TypeBed;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 03/11/2023
 * Time      : 10:50 CH
 * Filename  : BedRoom
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bed_room")
public class BedRoom extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "accom_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_bed_room_accom_place"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private AccomPlace accomPlace;

    @Column(name = "accom_id")
    private Long accomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "type_bed_code",
            referencedColumnName = "type_bed_code",
            foreignKey = @ForeignKey(name = "fk_association_bed_room_type_bed"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private TypeBed typeBed;

    @Column(name = "type_bed_code")
    private String typeBedCode;
}
