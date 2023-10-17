package com.mascara.oyo_booking_backend.entities;

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
@Table(name = "facility_room")
public class FacilityRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "facility_name", columnDefinition = "NVARCHAR(255)")
    private String facilityName;

    @ManyToMany(mappedBy = "facilityRoomSet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Room> roomSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "facil_room_cate_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_facil_room_facil_room_cate"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private FacilityRoomCategories facilityRoomCategories;

    @Column(name = "facil_room_cate_id")
    private Long facilRoomCateId;
}
