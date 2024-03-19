package com.mascara.oyo_booking_backend.entities.accommodation;

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
 * Date      : 19/03/2024
 * Time      : 7:07 CH
 * Filename  : PaymentInfoDetail
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payment_info_detail")
public class PaymentInfoDetail extends BasePesistence {
    @Id
    private Long Id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_name_host")
    private String accountNameHost;

    @Column(name = "swift_code")
    private String swiftCode;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private AccomPlace accomPlace;
}
