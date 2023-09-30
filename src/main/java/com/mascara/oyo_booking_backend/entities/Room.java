package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
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
}
