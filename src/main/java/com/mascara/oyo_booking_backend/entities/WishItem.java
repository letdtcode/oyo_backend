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
@Table(name="wish_item")
public class WishItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "accom_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_wish_item_accom"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private AccomPlace accomPlace;

    @Column(name = "accom_id", columnDefinition = "BINARY(16)")
    private UUID accomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "wish_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_wish_item_wish_list"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private WishList wishList;

    @Column(name = "wish_id", columnDefinition = "BINARY(16)")
    private UUID wishId;
}
