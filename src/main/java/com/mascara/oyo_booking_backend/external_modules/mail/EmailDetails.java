package com.mascara.oyo_booking_backend.external_modules.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 18/10/2023
 * Time      : 8:42 CH
 * Filename  : EmailDetails
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetails<T> {
    private String recipient;
    private T msgBody;
    private String subject;
    private String attachment;
}
