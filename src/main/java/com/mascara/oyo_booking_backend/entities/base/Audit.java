package com.mascara.oyo_booking_backend.entities.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.Builder;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Audit<U extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by",  length = 50, updatable = false)
    @JsonIgnore
    private U createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    @Builder.Default
    private LocalDateTime createdDate = getLocalDateTimeGMT7();

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    @JsonIgnore
    private U lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    @Builder.Default
    private LocalDateTime lastModifiedDate = getLocalDateTimeGMT7();


    public static LocalDateTime getLocalDateTimeGMT7() {
        DateTimeZone timeZone = DateTimeZone.forID("Asia/Ho_Chi_Minh");
        DateTime now = DateTime.now(timeZone);

        return LocalDateTime.of(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), now.getHourOfDay(), now.getMinuteOfHour(), now.getSecondOfMinute());
    }
}
