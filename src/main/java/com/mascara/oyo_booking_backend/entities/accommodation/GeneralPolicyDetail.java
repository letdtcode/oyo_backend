package com.mascara.oyo_booking_backend.entities.accommodation;

import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 19/03/2024
 * Time      : 5:40 CH
 * Filename  : GeneralPolicyDetail
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "general_policy_detail")
public class GeneralPolicyDetail extends BasePesistence {

    @Id
    private Long Id;

    @Column(name = "allow_event")
    private Boolean allowEvent;

    @Column(name = "allow_pet")
    private Boolean allowPet;

    @Column(name = "allow_smoking")
    private Boolean allowSmoking;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private AccomPlace accomPlace;
}
