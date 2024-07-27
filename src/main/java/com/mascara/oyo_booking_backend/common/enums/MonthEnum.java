package com.mascara.oyo_booking_backend.common.enums;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 28/06/2024
 * Time      : 6:20 CH
 * Filename  : MonthEnum
 */
public enum MonthEnum {
    JANUARY(1, "January"),
    FEBRUARY(2, "February"),
    MARCH(3, "March"),
    APRIL(4, "April"),
    MAY(5, "May"),
    JUNE(6, "June"),
    JULY(7, "July"),
    AUGUST(8, "August"),
    SEPTEMBER(9, "September"),
    OCTOBER(10, "October"),
    NOVEMBER(11, "November"),
    DECEMBER(12, "December");


    private final Integer monthValue;
    private final String monthName;

    MonthEnum(Integer monthValue, String monthName) {
        this.monthValue = monthValue;
        this.monthName = monthName;
    }

    public Integer getMonthValue() {
        return monthValue;
    }

    public String getMonthName() {
        return monthName;
    }
}
