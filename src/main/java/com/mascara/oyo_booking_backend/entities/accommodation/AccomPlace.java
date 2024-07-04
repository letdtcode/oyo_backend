package com.mascara.oyo_booking_backend.entities.accommodation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascara.oyo_booking_backend.entities.address.Province;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import com.mascara.oyo_booking_backend.entities.booking.Booking;
import com.mascara.oyo_booking_backend.entities.facility.Facility;
import com.mascara.oyo_booking_backend.entities.wish.WishItem;
import com.mascara.oyo_booking_backend.enums.AccomStatusEnum;
import com.mascara.oyo_booking_backend.enums.CancellationPolicyEnum;
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
@Table(name = "accom_place")
public class AccomPlace extends BasePesistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accom_name", columnDefinition = "NVARCHAR(255)")
    private String accomName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "district_code")
    private String districtCode;

    @Column(name = "ward_code")
    private String wardCode;

    @Column(name = "cld_video_id", columnDefinition = "VARCHAR(255)")
    private String cldVideoId;

    @Column(name = "acreage")
    private Float acreage;

    @Column(name = "num_people")
    private Integer numPeople;

    @Column(name = "num_bathroom")
    private Integer numBathRoom;

    @Column(name = "num_bed_room")
    private Integer numBedRoom;

    @Column(name = "num_kitchen")
    private Integer numKitchen;

    @Column(name = "num_view", columnDefinition = "bigint")
    @Builder.Default
    private Long numView = 0L;

    @Column(name = "grade_rate", columnDefinition = "float")
    @Builder.Default
    private Float gradeRate = 5f;

    @Column(name = "num_review", columnDefinition = "bigint")
    @Builder.Default
    private Long numReview = 0L;

    @Column(name = "num_booking", columnDefinition = "bigint")
    @Builder.Default
    private Long numBooking = 0L;

    @Column(name = "price_per_night")
    private Double pricePerNight;

    @Column(name = "discount")
    @Builder.Default
    private Double discount = 0D;

    @Column(name = "guide", columnDefinition = "NVARCHAR(255)")
    private String guide;

    @Column(name = "slugs", columnDefinition = "varchar (255)", unique = true)
    private String slugs;

    @Column(name = "check_in_from")
    private String checkInFrom;

    @Column(name = "check_out_to")
    private String checkOutTo;

    @Column(name = "cancellation_policy")
    @Enumerated(EnumType.STRING)
    private CancellationPolicyEnum cancellationPolicy;

    @Column(name = "cancellation_fee")
    private Integer cancellationFeeRate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accomPlace")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("createdDate ASC ")
    private Set<ImageAccom> imageAccoms;

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
            insertable = false,
            updatable = false
    )
    private Province province;

    @Column(name = "province_code")
    private String provinceCode;

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
            joinColumns = {@JoinColumn(name = "accom_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "facility_id", referencedColumnName = "id")}
    )
    private Set<Facility> facilitySet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accomPlace")
    @Fetch(FetchMode.SUBSELECT)
    private Set<BedRoom> bedRoomSet;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccomStatusEnum status;
}
