package com.mascara.oyo_booking_backend.entities;

import com.mascara.oyo_booking_backend.enums.ReviewStatusEnum;
import com.mascara.oyo_booking_backend.enums.RoleEnum;
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
@Table(name="review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

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
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private Room room;

    @Column(name = "room_id", columnDefinition = "BINARY(16)")
    private UUID roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "review_list_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_review_review_list"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private ReviewList reviewList;

    @Column(name = "review_list_id", columnDefinition = "BINARY(16)")
    private UUID reviewListId;
}
