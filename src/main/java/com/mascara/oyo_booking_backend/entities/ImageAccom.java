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
@Table(name="image_accom")
public class ImageAccom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "img_accom_link")
    private String imgAccomLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "accom_place_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_image_accom_accom"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private AccomPlace accomPlace;

    @Column(name = "accom_place_id", columnDefinition = "BINARY(16)")
    private UUID accomPlaceId;
}
