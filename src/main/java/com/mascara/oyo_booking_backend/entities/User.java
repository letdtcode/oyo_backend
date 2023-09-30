package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

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

    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "mail")
    private String mail;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    @OneToMany(mappedBy = "userOwn")
    private List<AccomPlace> accomPlace;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private WishList wishList;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private BookingList bookingList;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CommisionList commisionList;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roleSet;
}
