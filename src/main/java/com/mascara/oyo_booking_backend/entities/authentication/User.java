package com.mascara.oyo_booking_backend.entities.authentication;

import com.mascara.oyo_booking_backend.entities.accommodation.AccomPlace;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import com.mascara.oyo_booking_backend.entities.booking.BookingList;
import com.mascara.oyo_booking_backend.entities.review.ReviewList;
import com.mascara.oyo_booking_backend.entities.wish.WishList;
import com.mascara.oyo_booking_backend.enums.AuthProviderEnum;
import com.mascara.oyo_booking_backend.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "mail")
    private String mail;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProviderEnum provider;

    @Column(name = "provider_id")
    private String providerId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    private Set<RefreshToken> refreshTokenSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    private Set<AccomPlace> accomPlace;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private WishList wishList;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BookingList bookingList;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ReviewList reviewList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roleSet;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MailConfirmToken mailConfirmToken;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;
}
