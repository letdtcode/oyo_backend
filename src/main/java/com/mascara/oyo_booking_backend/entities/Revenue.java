package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;


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
@DynamicInsert
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
    private Double commPay;

    @Column(name = "total_revenue", nullable = false)
    private Double totalRevenue;

    @Column(name = "total_bill", nullable = false)
    private Double totalBill;

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
