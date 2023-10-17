package com.mascara.oyo_booking_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 30/09/2023
 * Time      : 10:34 CH
 * Filename  : Commision
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "commision")
public class Commision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "comm_pay")
    private BigDecimal commPay;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_code")
    private Booking bookingCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "comm_list_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_comm_comm_list"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private CommisionList commisionList;

    @Column(name = "comm_list_id")
    private Long commListId;
}
