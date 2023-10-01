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

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",columnDefinition = "BINARY(16)")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart")
    @Fetch(FetchMode.SUBSELECT)
    private Set<CartItem> cartItemSet;
}
