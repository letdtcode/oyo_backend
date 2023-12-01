package com.mascara.oyo_booking_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import com.mascara.oyo_booking_backend.enums.AccomStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accom_place")
public class AccomPlace extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "accom_name", columnDefinition = "NVARCHAR(255) NOT NULL", unique = true)
    private String accomName;

    @Column(name = "description")
    private String description;

    @Column(name = "address_detail")
    private String addressDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_accom_user"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private User user;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "accom_cate_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_accom_accom_cate"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private AccommodationCategories accommodationCategories;

    @Column(name = "accom_cate_id")
    private Long accomCateId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "province_code",
            referencedColumnName = "province_code",
            foreignKey = @ForeignKey(name = "fk_association_accom_province"),
            nullable = false,
            insertable = false,
            updatable = false
    )
    private Province province;

    @Column(name = "province_code")
    private String provinceCode;

    @Column(name = "district_code")
    private String districtCode;

    @Column(name = "ward_code")
    private String wardCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accomPlace")
    @Fetch(FetchMode.SUBSELECT)
    private Set<ImageAccom> imageAccoms;

    @Column(name = "acreage")
    private Float acreage;

    @Column(name = "num_people", nullable = false)
    private Integer numPeople;

    @Column(name = "num_bathroom")
    private Integer numBathRoom;

    @Column(name = "num_bed_room", nullable = false)
    private Integer numBedRoom;

    @Column(name = "num_kitchen", nullable = false)
    private Integer numKitchen;

    @Column(name = "num_view", nullable = false, columnDefinition = "bigint default 0")
    private Long numView;

    @Column(name = "grade_rate")
    private Float gradeRate;

    @Column(name = "num_review", nullable = false, columnDefinition = "bigint default 0")
    private Long numReview;

    @Column(name = "num_booking", nullable = false, columnDefinition = "bigint default 0")
    private Long numBooking;

    @Column(name = "price_per_night", nullable = false)
    private Double pricePerNight;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccomStatusEnum status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accomPlace")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Review> reviewSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accomPlace")
    @Fetch(FetchMode.SUBSELECT)
    private Set<WishItem> wishItemSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accomPlace")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Booking> bookingSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accomPlace")
    @Fetch(FetchMode.SUBSELECT)
    private Set<SurchargeOfAccom> surchargeOfAccomSet;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "facility_accom",
            joinColumns = {@JoinColumn(name = "facility_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "accom_id", referencedColumnName = "id")}
    )
    private Set<Facility> facilitySet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accomPlace")
    @Fetch(FetchMode.SUBSELECT)
    private Set<BedRoom> bedRoomSet;

    @Column(name = "slugs", columnDefinition = "varchar (255)", unique = true)
    private String slugs;
}
