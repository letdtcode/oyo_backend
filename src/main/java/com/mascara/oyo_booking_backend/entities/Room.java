package com.mascara.oyo_booking_backend.entities;

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
import java.util.Set;
import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "acreage")
    private Float acreage ;

    @Column(name = "num_people")
    private Integer numPeople;

    @Column(name = "num_bath_room")
    private Integer numBathRoom;

    @Column(name = "num_bed")
    private Integer numBed;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "allow_child")
    private Integer allowChild;

    @Column(name = "num_child")
    private Integer numChild;

    @Column(name = "price_per_night")
    private BigDecimal pricePerNight;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    @Fetch(FetchMode.SUBSELECT)
    private Set<ImageRoom> imageRoomSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    @Fetch(FetchMode.SUBSELECT)
    private Set<DetailBedOfRoom> detailBedOfRoomSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Review> reviewSet;

    @ManyToOne
    @JoinColumn(
            name = "accom_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_room_accom"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private AccomPlace accomPlace;

    @Column(name = "accom_id", columnDefinition = "BINARY(16)")
    private UUID accomId;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "room_facility_room",
            joinColumns = {@JoinColumn(name = "room_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "facility_room_id", referencedColumnName = "id")}
    )
    private Set<FacilityRoom> facilityRoomSet;

    @ManyToOne
    @JoinColumn(
            name = "room_cate_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_room_room_cate"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private RoomCategories roomCategories;

    @Column(name = "room_cate_id", columnDefinition = "BINARY(16)")
    private UUID roomCateId;
}
