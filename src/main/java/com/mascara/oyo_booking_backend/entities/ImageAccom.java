package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.entities.base.Audit;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "image_accom")
public class ImageAccom extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "img_accom_link")
    private String imgAccomLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "accom_place_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_image_accom_accom"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private AccomPlace accomPlace;

    @Column(name = "accom_place_id")
    private Long accomPlaceId;
}
