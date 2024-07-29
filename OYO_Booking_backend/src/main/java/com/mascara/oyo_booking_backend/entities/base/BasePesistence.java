package com.mascara.oyo_booking_backend.entities.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/11/2023
 * Time      : 3:36 CH
 * Filename  : Persistence
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@MappedSuperclass
public abstract class BasePesistence extends Audit<String> {
    @JsonIgnore
    @Column(name = "deleted", nullable = false, columnDefinition = "boolean")
    @Builder.Default
    private boolean deleted = false;
}
