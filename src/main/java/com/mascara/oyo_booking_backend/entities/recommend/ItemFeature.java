package com.mascara.oyo_booking_backend.entities.recommend;

import com.mascara.oyo_booking_backend.entities.base.BasePesistence;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 30/06/2024
 * Time      : 6:40 CH
 * Filename  : ItemFeature
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "item_feature")
public class ItemFeature extends BasePesistence {
    @Id
    private Long id;

    @Column(name = "accom_category_name")
    private String accomCategoryName;

    @Column(name = "province_name")
    private String provinceName;

    @Column(name = "price_per_night")
    private Double pricePerNight;
}
