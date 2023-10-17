package com.mascara.oyo_booking_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "detail_bed_of_room")
public class DetailBedOfRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "bed_cate_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_detail_bed_bed_categories"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private BedCategories bedCategories;

    @Column(name = "bed_cate_id")
    private Long bedCateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "room_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_detail_bed_room"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private Room room;

    @Column(name = "room_id")
    private Long roomId;
}
