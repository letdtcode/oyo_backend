package com.mascara.oyo_booking_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "room_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_cart_item_room"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private Room room;

    @Column(name = "room_id")
    private Long roomId;

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

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price_item")
    private BigDecimal totalPriceItem;
}
