package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.common.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.entities.accommodation.AccommodationCategories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 16/11/2024
 * Time      : 6:04 CH
 * Filename  : AccommodationCategoryRepositoryTest
 */

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccommodationCategoryRepositoryTest {

    @Autowired
    private AccommodationCategoriesRepository accommodationCategoriesRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findByIdTest() {
        AccommodationCategories accommodationCategories = AccommodationCategories.builder()
                .id(1L)
                .accomCateName("Category 1")
                .status(CommonStatusEnum.ENABLE).build();

        accommodationCategories = testEntityManager.persistAndFlush(accommodationCategories);

        Optional<AccommodationCategories> accomCateFind = this.accommodationCategoriesRepository.findById(1L);
        assertThat(accomCateFind.get().getAccomCateName()).isEqualTo(accommodationCategories.getAccomCateName());
    }
}
