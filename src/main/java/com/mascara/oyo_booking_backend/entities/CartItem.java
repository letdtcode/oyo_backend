package com.mascara.oyo_booking_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(
            name = "room_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_cart_item_room"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private Room room;

    @Column(name = "room_id", columnDefinition = "BINARY(16)")
    private UUID roomId;

    @ManyToOne
    @JoinColumn(
            name = "cart_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_cart_item_cart"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private Cart cart;

    @Column(name = "cart_id", columnDefinition = "BINARY(16)")
    private UUID cartId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price_item")
    private BigDecimal totalPriceItem;
}
