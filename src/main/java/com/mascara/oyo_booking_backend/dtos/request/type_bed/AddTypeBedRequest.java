package com.mascara.oyo_booking_backend.dtos.request.type_bed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/11/2023
 * Time      : 5:45 CH
 * Filename  : AddTypeBedRequest
 */
@Data
@AllArgsConstructor
public class AddTypeBedRequest {
    @NotNull
    @NotBlank
    private String typeBedName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "(?i)Enable|Disable", message = "Status must be 'Enable' or 'Disable'")
    private String status;
}
