package com.mascara.oyo_booking_backend.config.init_data.models;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 11/04/2024
 * Time      : 3:37 CH
 * Filename  : PaymentInfoModel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfoModel {
    @NotNull
    private Long bankId;

    @NotNull
    private String accountNumber;

    @NotNull
    private String accountNameHost;

    @NotNull
    private String swiftCode;
}
