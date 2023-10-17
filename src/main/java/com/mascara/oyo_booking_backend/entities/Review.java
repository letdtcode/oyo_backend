package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.enums.ReviewStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "rate_star")
    private Float rateStar;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReviewStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "room_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_review_room"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private Room room;

    @Column(name = "room_id")
    private Long roomId;

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
}
