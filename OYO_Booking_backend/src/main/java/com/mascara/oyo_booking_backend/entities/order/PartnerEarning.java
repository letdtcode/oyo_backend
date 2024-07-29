package com.mascara.oyo_booking_backend.entities.order;

import com.mascara.oyo_booking_backend.entities.authentication.User;
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
 * Time      : 8:34 CH
 * Filename  : ParterEarning
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "partner_earning")
public class PartnerEarning extends BasePesistence {

    @Id
    private Long id;

//    @Column(name = "partner_id")
//    private Long partnerId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "partner_id", insertable = false, updatable = false)
    private User partner;

    @Column(name = "partner_id")
    private Long partnerId;

    @Column(name = "earning_amount")
    private Double earningAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Payment payment;
}
