package com.mascara.oyo_booking_backend.utils;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 4:27 CH
 * Filename  : AppContants
 */
public class AppContants {
    public static final String USER_NOT_FOUND_WITH_REFRESH_TOKEN = "Not found User with token: ";
    public static final String SEND_MAIL_SUCCESS = "Send mail success";
    public static final String ACTIVE_USER_SUCCESS = "Active User thành công";
    public static final String TOKEN_ACTIVE_MAIL_INVALID = "Token không hợp lệ hoặc đã hết hạn";
    public static final String ACTIVE_USER_TOKEN_EXPIRED = "Token hết hạn";
    public static final String PASSWORD_INCORRECT = "Password is incorrect";
    public static final String CHANGE_PASSWORD_SUCCESS = "Change password success";
    public static final String NEW_PASSWORD_NOT_MATCH_PATTERN = "New password not match pattern";
    public static final String FILE_IS_NULL = "File is null";

    public static final String BOOKING_SUCESSFUL = "Booking successful";

    public static final String FIELD_EXIST_WHEN_ADD_ENTITY(String entity, String field) {
        return "Can not add " + entity + " because " + field + " is exist";
    }

    public static final String DELETE_SUCCESS_MESSAGE(String entity) {
        return "Delete " + entity + " success";
    }

    public static final String ADD_SUCCESS_MESSAGE(String entity) {
        return "Add " + entity + " success";
    }

    public static final String UPDATE_SUCCESS_MESSAGE(String entity) {
        return "Update " + entity + " success";
    }

    public static final String NOT_FOUND_MESSAGE(String entity) {
        return entity + " not found";
    }

}
