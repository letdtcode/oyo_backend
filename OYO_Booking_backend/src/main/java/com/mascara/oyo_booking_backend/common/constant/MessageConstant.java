package com.mascara.oyo_booking_backend.common.constant;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 4:27 CH
 * Filename  : MessageConstant
 */
public class MessageConstant {
    public static final String SEND_MAIL_SUCCESS = "Send mail success";
    public static final String ACTIVE_USER_SUCCESS = "Active User thành công";
    public static final String TOKEN_ACTIVE_MAIL_INVALID = "Token không hợp lệ hoặc đã hết hạn";
    public static final String ACTIVE_USER_TOKEN_EXPIRED = "Token hết hạn";
    public static final String PASSWORD_INCORRECT = "Password is incorrect";
    public static final String CHANGE_PASSWORD_SUCCESS = "Change password success";
    public static final String FILE_IS_NULL = "File is null";
    public static final String NOT_PERMIT = "Not permit do this";
    public static final String RESET_PASSWORD_SUCESS = "Reset password sucess";
    public static final Object REVIEW_IS_NOT_AVAILABLE = "Review is not available";
    public static final Object CHANGE_STATUS_BOOKING_SUCCESS = "Change status booking success";
    public static final String ADD_WISH_ITEM_SUCCESS = "Add wish item success";
    public static final String REMOVE_WISH_ITEM_SUCCESS = "Remove wish item success";

    public static final String BOOKING_NOT_AVAILABLE_TIME(Long accomId, String dateCheckIn, String dateCheckOut) {
        return "Booking not available for accom " + accomId + "from " + dateCheckIn + " to " + dateCheckOut;
    }

    public static final String BOOKING_NOT_AVAILABLE_PEOPLE = "Num people over range of accom place";

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
