package com.mascara.oyo_booking_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 30/09/2023
 * Time      : 10:35 CH
 * Filename  : CommisionList
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="commision_list")
public class CommisionList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "discount")
    private Float discount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "commisionList")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Commision> commisionSet;
}
