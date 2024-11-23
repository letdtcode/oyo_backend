package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.services.accom_category.AccomCategoryService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 15/11/2024
 * Time      : 11:20 CH
 * Filename  : CmsAccomCategoryControllerTest
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(CmsAccomCategoryController.class)
public class CmsAccomCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccomCategoryService accomCategoryService;
}
