package com.mascara.oyo_booking_backend.external_modules.mail.model_mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/12/2023
 * Time      : 8:11 CH
 * Filename  : ActiveUserInfo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActiveUserInfo {
    String linkActiveUser;
}
