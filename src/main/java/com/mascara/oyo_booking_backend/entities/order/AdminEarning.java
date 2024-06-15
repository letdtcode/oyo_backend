package com.mascara.oyo_booking_backend.entities.order;

import com.mascara.oyo_booking_backend.entities.order.Payment;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 29/02/2024
 * Time      : 8:36 CH
 * Filename  : OYOEarning
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "admin_earning")
public class AdminEarning extends BasePesistence {
    @Id
    private Long id;

    @Column(name = "earning_amount")
    private Double earningAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Payment payment;
}
