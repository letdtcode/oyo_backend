package com.mascara.oyo_booking_backend.external_modules.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 03/12/2023
 * Time      : 4:06 SA
 * Filename  : UserBasicInfo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordInfo {
    private String fullName;
    private String newPassword;
}
