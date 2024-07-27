package com.mascara.oyo_booking_backend.entities.booking;

import com.mascara.oyo_booking_backend.entities.review.Review;
import com.mascara.oyo_booking_backend.entities.accommodation.AccomPlace;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import com.mascara.oyo_booking_backend.common.enums.booking.BookingStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_code", columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
    private String bookingCode;

    @Column(name = "date_check_in", nullable = false)
    private LocalDate checkIn;

    @Column(name = "date_check_out", nullable = false)
    private LocalDate checkOut;

    @Column(name = "name_customer", nullable = false)
    private String nameCustomer;

    @Column(name = "phone_number_customer", nullable = false)
    private String phoneNumberCustomer;

    @Column(name = "num_adult", nullable = false)
    private Integer numAdult;

    @Column(name = "num_child", nullable = false)
    private Integer numChild;

    @Column(name = "num_born_child", nullable = false)
    private Integer numBornChild;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "accom_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_accom_booking"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private AccomPlace accomPlace;

    @Column(name = "accom_id", nullable = false)
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

    @Column(name = "booking_list_id", nullable = false)
    private Long bookListId;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Review review;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatusEnum status;
}
