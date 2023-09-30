package com.mascara.oyo_booking_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

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
@Table(name="commision")
public class Commision {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "comm_pay")
    private BigDecimal commPay;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_code")
    private Booking bookingCode;

    @ManyToOne
    @JoinColumn(
            name = "comm_list_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_comm_comm_list"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private CommisionList commisionList;

    @Column(name = "comm_list_id", columnDefinition = "BINARY(16)")
    private UUID commListId;
}
