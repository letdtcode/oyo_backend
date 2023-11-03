package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.entities.base.Audit;
import com.mascara.oyo_booking_backend.enums.BookingStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "booking_code", columnDefinition = "VARCHAR(255)", nullable = false)
    private String bookingCode;

    @Column(name = "date_check_in", nullable = false)
    private LocalDate checkIn;

    @Column(name = "date_check_out", nullable = false)
    private LocalDate checkOut;

    @Column(name = "mail_customer", nullable = false)
    private String mailCustomer;

    @Column(name = "origin_pay")
    private BigDecimal originPay;

    @Column(name = "surcharge")
    private BigDecimal surcharge;

    @Column(name = "total_bill")
    private BigDecimal totalBill;

    @Column(name = "total_transfer")
    private BigDecimal totalTransfer;

    @Column(name = "payment_method")
    private Integer paymentMethod;

    @Column(name = "payment_by")
    private Integer paymentBy;

    @Column(name = "num_adult")
    private Integer numAdult;

    @Column(name = "num_child")
    private Integer numChild;

    @Column(name = "num_born_child")
    private Integer numBornChild;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatusEnum bookingStatusEnum;

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

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Revenue revenue;
}
