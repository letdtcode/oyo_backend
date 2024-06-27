package com.mascara.oyo_booking_backend.dtos.statistic.admin.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/12/2023
 * Time      : 3:10 SA
 * Filename  : AdminStatisticTransactionResponse
 */
@Data
@Builder
public class AdminStatisticTransactionResponse {
    private Long transactionId;
    private String customerName;
    private String ownerName;
    private Double totalCost;
    private Double adminCost;
    private String homeName;
    private LocalDateTime createdDate;
}
