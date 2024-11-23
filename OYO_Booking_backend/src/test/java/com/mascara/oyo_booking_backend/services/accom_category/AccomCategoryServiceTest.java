package com.mascara.oyo_booking_backend.services.accom_category;

import com.mascara.oyo_booking_backend.common.mapper.accommodation.AccommodationCategoriesMapper;
import com.mascara.oyo_booking_backend.repositories.AccommodationCategoriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 16/11/2024
 * Time      : 12:51 CH
 * Filename  : AccomCategoryServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class AccomCategoryServiceTest {

    @Mock
    private AccommodationCategoriesRepository accommodationCategoriesRepository;

    @Mock
    private AccommodationCategoriesMapper accommodationCategoriesMapper;

    private AccomCategoryService accomCategoryService;

    @BeforeEach
    void setUp() {
        accomCategoryService = new AccomCategoryServiceImpl(accommodationCategoriesRepository,
                accommodationCategoriesMapper);
    }
}
