package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 30/09/2023
 * Time      : 10:23 CH
 * Filename  : BookList
 */

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "booking_list")
public class BookingList extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookingList")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Booking> bookingList;
}
