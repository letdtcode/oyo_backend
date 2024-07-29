package com.mascara.oyo_booking_backend.external_modules.mail.model_mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/12/2023
 * Time      : 8:33 CH
 * Filename  : BookingSuccessInfo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingSuccessInfo {
    private String billId;
    private String homeName;
    private String ownerName;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Double baseCost;
    private Double surchargeCost;
    private Double totalCost;
    private Double moneyPay;
    private String createdDate;
    private String fullNameCustomer;
}
