package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 30/09/2023
 * Time      : 10:34 CH
 * Filename  : Commision
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "revenue")
public class Revenue extends BasePesistence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_code",
            referencedColumnName = "booking_code",
            foreignKey = @ForeignKey(name = "fk_association_revenue_booking"),
            nullable = false,
            insertable = false,
            updatable = false)
    private Booking booking;

    @Column(name = "booking_code", columnDefinition = "VARCHAR(255)", nullable = false)
    private String bookingCode;

    @Column(name = "comm_pay", nullable = false)
    private BigDecimal commPay;

    @Column(name = "total_revenue", nullable = false)
    private BigDecimal totalRevenue;

    @Column(name = "total_bill", nullable = false)
    private BigDecimal totalBill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "revenue_list_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_revenue_revenue_list"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private RevenueList revenueList;

    @Column(name = "revenue_list_id")
    private Long revenueListId;
}
