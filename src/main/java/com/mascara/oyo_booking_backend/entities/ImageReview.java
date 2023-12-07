package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/11/2023
 * Time      : 10:08 CH
 * Filename  : ImageReview
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "image_review")
public class ImageReview extends BasePesistence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "review_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_image_review_review"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private Review review;

    @Column(name = "review_id")
    private Long reviewId;
}
