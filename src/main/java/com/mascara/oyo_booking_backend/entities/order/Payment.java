package com.mascara.oyo_booking_backend.entities.order;

import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import com.mascara.oyo_booking_backend.entities.booking.Booking;
import com.mascara.oyo_booking_backend.enums.PaymentMethodEnum;
import com.mascara.oyo_booking_backend.enums.PaymentPolicyEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 29/02/2024
 * Time      : 8:33 CH
 * Filename  : Payment
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment extends BasePesistence {
    @Id
    private Long id;

    @Column(name = "origin_pay", nullable = false)
    private Double originPay;

    @Column(name = "surcharge_pay", nullable = false)
    private Double surchargePay;

    @Column(name = "total_bill", nullable = false)
    private Double totalBill;

    @Column(name = "total_transfer", nullable = false)
    private Double totalTransfer;

    @Column(name = "payment_policy", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentPolicyEnum paymentPolicy;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

    @Column(name = "cancellation_fee")
    private Double cancellationFee;

    @Column(name = "cancel_reason", columnDefinition = "TEXT")
    private String cancelReason;

    @Column(name = "cancel_period", columnDefinition = "TIMESTAMP")
    private LocalDateTime cancelPeriod;

//    @Column(name = "paypal_order_status")
//    private String paypalOrderStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Booking booking;
}
