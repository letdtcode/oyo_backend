package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.enums.BookingStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "code", columnDefinition = "NVARCHAR(255) NOT NULL")
    private String code;

    @Column(name = "date_check_in")
    private LocalDateTime checkIn;

    @Column(name = "date_check_out")
    private LocalDateTime checkOut;

    @Column(name = "name_customer")
    private String nameCustomer;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "origin_pay")
    private String originPay;

    @Column(name = "surcharge")
    private String surcharge;

    @Column(name = "total_pay")
    private String totalPay;

    @Column(name = "payments")
    private String payments;

    @Column(name = "num_of_room")
    private String numOfRoom;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatusEnum bookingStatusEnum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "accom_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_accom_room"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private AccomPlace accomPlace;

    @Column(name = "accom_id")
    private Long accomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "booking_list_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_booking_booking_list"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private BookingList bookingList;

    @Column(name = "booking_list_id")
    private Long bookListId;
}
