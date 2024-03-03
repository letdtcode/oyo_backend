package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 19/10/2023
 * Time      : 2:51 SA
 * Filename  : MailConfirmToken
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "mail_confirm_token")
public class MailConfirmToken extends BasePesistence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "date_expired", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateExpired;

    @Column(name = "verify_token")
    private String verifyToken;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    public MailConfirmToken(String token, User user, Integer EXPIRATION_TIME_MINUTE) {
        super();
        this.verifyToken = token;
        this.user = user;
        this.dateExpired = this.getTokenExpirationTime(EXPIRATION_TIME_MINUTE);
    }

    public LocalDateTime getTokenExpirationTime(Integer EXPIRATION_TIME_MINUTE) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME_MINUTE);
        TimeZone tz = calendar.getTimeZone();
        ZoneId zoneId = tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zoneId);
    }
}
