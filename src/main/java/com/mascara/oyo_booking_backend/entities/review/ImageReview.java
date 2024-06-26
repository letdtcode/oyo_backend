package com.mascara.oyo_booking_backend.entities.review;

import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import com.mascara.oyo_booking_backend.entities.review.Review;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
@Table(name = "image_review")
public class ImageReview extends BasePesistence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
