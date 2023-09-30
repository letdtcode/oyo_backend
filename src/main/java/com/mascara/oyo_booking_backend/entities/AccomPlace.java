package com.mascara.oyo_booking_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.UUID;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="accom_place")
public class AccomPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "accom_name", columnDefinition = "NVARCHAR(255) NOT NULL")
    private String accomName;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "grade_rate")
    private Float gradeRate;

    @Column(name = "num_review")
    private Double numReview;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_accom_user"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private User user;

    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;

    @ManyToOne
    @JoinColumn(
            name = "accom_cate_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_accom_accom_cate"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private AccommodationCategories accommodationCategories;

    @Column(name = "accom_cate_id", columnDefinition = "BINARY(16)")
    private UUID accomCateId;

    @ManyToOne
    @JoinColumn(
            name = "province_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_accom_province"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private Province province;

    @Column(name = "province_id", columnDefinition = "BINARY(16)")
    private UUID provinceId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accomPlace")
    @Fetch(FetchMode.SUBSELECT)
    private List<ImageAccom> imageAccoms;
}
