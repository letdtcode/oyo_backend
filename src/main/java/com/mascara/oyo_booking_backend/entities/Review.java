package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "review")
public class Review extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "title")
    private String title;

    @Column(name = "content",columnDefinition = "TEXT")
    private String content;

    @Column(name = "rate_star")
    private Float rateStar;

    @Column(name = "have_img", nullable = false)
    private Boolean haveImage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "review")
    @Fetch(FetchMode.SUBSELECT)
    private Set<ImageReview> imageReviewSet;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "review_list_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_review_review_list"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private ReviewList reviewList;

    @Column(name = "review_list_id")
    private Long reviewListId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;
}
