package com.mascara.oyo_booking_backend.utils;

import java.time.LocalDate;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/11/2023
 * Time      : 6:03 CH
 * Filename  : GenerateCodeUtils
 */
public class Utilities {
    private static Utilities instance = null;

    public static Utilities getInstance() {
        if (instance == null)
            instance = new Utilities();
        return instance;
    }

    public String generateCode(String alias, Integer count) {
        int newCount = count + 1;
        String formattedCount = String.format("%03d", newCount);
        return alias + "_" + formattedCount;
    }

    public int roundNearestMultipleOf10(float number) {
        return (int) Math.round(number / 10.0) * 10;
    }

    public boolean isWithinRange(LocalDate testDate, LocalDate startDate, LocalDate endDate) {
        // exclusive startDate and endDate
        //return testDate.isBefore(endDate) && testDate.isAfter(startDate);
        // inclusive startDate and endDate
        return (testDate.isEqual(startDate) || testDate.isEqual(endDate))
                || (testDate.isBefore(endDate) && testDate.isAfter(startDate));

    }
}
