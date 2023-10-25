package com.mascara.oyo_booking_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "accom_id")
    private AccomPlace accomPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "cart_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_cart_item_cart"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private Cart cart;

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "date_check_in")
    private LocalDateTime checkIn;

    @Column(name = "date_check_out")
    private LocalDateTime checkOut;

    @Column(name = "total_price_item")
    private BigDecimal totalPriceItem;
}
