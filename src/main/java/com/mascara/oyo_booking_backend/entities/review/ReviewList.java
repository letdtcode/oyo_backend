package com.mascara.oyo_booking_backend.entities.review;

import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import com.mascara.oyo_booking_backend.entities.review.Review;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "review_list")
public class ReviewList extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reviewList")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Review> reviewSet;
}
